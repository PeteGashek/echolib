package com.javasteam.amazon.echo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.object.EchoTodoItemRetrieved;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;

/**
 * @author ddamon
 *
 */
public class TodoItemPoller extends Thread {
  private final static Log          log = LogFactory.getLog( TodoItemPoller.class.getName() );
  
  public static final TodoItemPoller EMPTY_INSTANCE = new TodoItemPoller();
  
  private Vector<EchoCommandHandler> todoListeners     = new Vector<EchoCommandHandler>();
  
  private EchoUserSession echoUserSession;
  private int             intervalInSeconds  = 10;
  private int             itemRetrievalCount = 100;
  private boolean         stopped            = false;
  private Configurator    configurator       = null;
  
  private TodoItemPoller() {
    super();
  }

  /**
   * @param echoUserSession
   */
  public TodoItemPoller( final Configurator configurator, final EchoUserSession echoUserSession ) {
    this();
    
    Preconditions.checkNotNull( echoUserSession );
    Preconditions.checkNotNull( configurator );
    
    this.configurator    = configurator;
    this.echoUserSession = echoUserSession; 
  }
  
  private void configure() {
    String todoPollingIntervalStr  = configurator.get( "todoPollingInterval" );
    String todoPollingItemCountStr = configurator.get( "todoPollingItemCount" );
    int    todoPollingInterval     = 60;
    int    todoPollingItemCount    = 100;
    
    if( todoPollingIntervalStr != null ) {
      int temp = Integer.parseInt( todoPollingIntervalStr );
      if( temp >= 10 ) {
        todoPollingInterval = temp;
      }
    }
      
    if( todoPollingItemCountStr != null ) {
      int temp = Integer.parseInt( todoPollingItemCountStr );
      if( temp > 0 ) {
        todoPollingItemCount = temp;
      }
    }

    int i = 0;
    boolean halt = false;
    do {
      String listenerString = configurator.get( "todoListener." + ++i );
      if( listenerString != null ) {
        EchoCommandHandlerBuilder listenerBuilder = EchoCommandHandlerDefinitionPropertyParser.getCommandHandlerBuilder( listenerString );
        EchoCommandHandler        listener        = listenerBuilder.generate();
        addTodoRetrievedListener( listener );
        log.info(  "Added " + listener.getClass().getName() + " as a listener for key: " + listener.getKey() );
      }
      else {
        halt = true;
      }
    } while( !halt );

    this.setIntervalInSeconds( todoPollingInterval );
    this.setItemRetrievalCount( todoPollingItemCount );
    echoUserSession.startTodoItemPoller();
  }
  
  
  /**
   * @return
   */
  public EchoUserSession getEchoUserSession() {
    return echoUserSession;
  }

  /**
   * @param echoUserSession
   */
  public void setEchoUserSession( final EchoUserSession echoUserSession ) {
    Preconditions.checkNotNull( echoUserSession );
    
    this.echoUserSession = echoUserSession;
  }

  /**
   * @return
   */
  public int getIntervalInSeconds() {
    return intervalInSeconds;
  }

  /**
   * @param intervalInSeconds
   */
  public void setIntervalInSeconds( final int intervalInSeconds ) {
    this.intervalInSeconds = intervalInSeconds;
  }

  /**
   * @return
   */
  public int getItemRetrievalCount() {
    return itemRetrievalCount;
  }

  /**
   * @param itemRetrievalCount
   */
  public void setItemRetrievalCount( final int itemRetrievalCount ) {
    this.itemRetrievalCount = itemRetrievalCount;
  }

  /**
   * @return
   */
  public boolean isStopped() {
    return stopped;
  }

  /**
   * @param stopped
   */
  public void setStopped( final boolean stopped ) {
    this.stopped = stopped;
  }

  /**
   * 
   */
  public synchronized void shutdown() {
    stopped = true;
    //this.interrupt();
  }
  

  private void doSleep( final long intervalInSeconds ) {
    int loop = 0;

    while( !isStopped() && loop < intervalInSeconds ) {
      try {
        Thread.sleep( 1000 );
      }
      catch( InterruptedException e ) {}
      ++loop;
    }
  }
  
  private void loginUserIfNecessary() {
    Preconditions.checkNotNull( echoUserSession );
    
    if( !echoUserSession.getEchoUser().isLoggedIn() ) {
      try {
        log.info( "Logging in user: " + echoUserSession.getEchoUser().getUsername() );
        echoUserSession.getEchoBase().echoLogin( echoUserSession.getEchoUser() );
      }
      catch( AmazonLoginException e ) {
        log.error( "Logging in echoUser", e  );
      }
    }
  }
  
  private boolean todoItemCanBeProcessed( final EchoTodoItemRetrieved todoItem ) {
    return todoItem != null && !todoItem.isComplete() && !todoItem.isDeleted() && todoItem.getText() != null;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#removeTodoRetrievedListener(com.javasteam.amazon.echo.plugin.TodoItemRetrievedListener)
   */
  public boolean removeTodoRetrievedListener( final EchoCommandHandler todoListener ) {
    boolean retval = false;
    
    if( todoListener != null &&  this.todoListeners.contains( todoListener )) {
      this.todoListeners.remove( todoListener );
      retval = true;
    }
    
    return retval; 
  }
  
  /**
   * @return
   */
  public boolean hasTodoRetrievedListeners() {
    return this.todoListeners != null && !this.todoListeners.isEmpty();
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#addTodoRetrievedListener(com.javasteam.amazon.echo.plugin.TodoItemRetrievedListener)
   */
  public boolean addTodoRetrievedListener( final EchoCommandHandler todoListener ) {
    boolean retval = false;
    
    if( todoListener != null &&  !this.todoListeners.contains( todoListener )) {
      this.todoListeners.add( todoListener );
      retval = true;
    }
    
    return retval;
  }
  
  
  private boolean sendTodoNotifications( final EchoTodoItemRetrieved todoItem ) {
    boolean handled = false;
    
    for( EchoCommandHandler listener : this.todoListeners ) {
      if( todoItemCanBeProcessed( todoItem )) {
        if( todoItem.getText().toLowerCase().startsWith( listener.getKey().toLowerCase() )) {
          String           remainder    = todoItem.getText().substring( listener.getKey().length() );
          EchoResponseItem responseItem = new EchoTodoResponseItem( listener.getKey()
                                                                  , remainder
                                                                  , todoItem
                                                                  );
          
          
          log.info( "Handling '" + todoItem.getText() + "' with listener " + listener.getKey() );
          handled = handled | listener.handle( responseItem, this.getEchoUserSession() );
        }
      }
    }
    
    return handled;
  }
  
  private long parseTimeToLiveString( String timeToLiveStr ) {
    long   timeToLive    = 60 * 60000; // 60 minutes
    
    if( StringUtils.isNotBlank( timeToLiveStr ) ) {
      timeToLive = Long.parseLong( timeToLiveStr ) * 60000;
    }
    
    return timeToLive;
  }
  
  public void setTodoItemPollerIntervalInSeconds( final int intervalInSeconds ) {
    this.intervalInSeconds = intervalInSeconds;
  }

  private void deleteTodoItemIfExpired( final long timeToLive, final EchoTodoItemRetrieved todoItem ) {
    Preconditions.checkNotNull( todoItem );
    
    Date date = new Date();
    
    if( log.isDebugEnabled() ) {
      log.debug( "checking TTL for item alive for (ms): " + ( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ) + " > " + timeToLive );
    }

    if( timeToLive > -1 && ( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ) > timeToLive ) {
      try {
        log.info( "Time to live has passed.  Deleting todo: " + todoItem.getText() );
        this.getEchoUserSession().getEchoBase().deleteTodoItem( todoItem, this.getEchoUserSession().getEchoUser() );
      }
      catch( AmazonAPIAccessException e ) {
        log.error( "Failed deleting todo item", e );
      }
    }
  }
  
  
  /**
   * @param todoItem
   */
  public void notifyTodoRetrievedListeners( final EchoTodoItemRetrieved todoItem ) {
    Preconditions.checkNotNull( todoItem );
    
    if( this.todoListeners != null && !this.todoListeners.isEmpty() ) {
      if( todoItem.isComplete() ) {
        deleteTodoItemIfExpired( parseTimeToLiveString( configurator.get( "cancelledMinutesToLive" ))
                               , todoItem );
      }
      
      if( sendTodoNotifications( todoItem )) {
        try {
          this.getEchoUserSession().getEchoBase().completeTodoItem( todoItem, this.getEchoUserSession().getEchoUser() );
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Failed marking handled todo item as complete", e );
        }
      }
    }
  }

  private void handleUsersTodoItems() throws AmazonAPIAccessException {
    Preconditions.checkNotNull( echoUserSession );
    
    List<EchoTodoItemRetrieved> todos = echoUserSession.getEchoBase().getTodoItems( itemRetrievalCount, echoUserSession.getEchoUser() );
    
    log.info( "getting todos for user: " + echoUserSession.getEchoUser().getUsername() );
    
    if( todos != null ) {
      for( EchoTodoItemRetrieved todoItem : todos ) {
        echoUserSession.notifyTodoRetrievedListeners( todoItem );
      }
      log.info( "finished todos for user: " + echoUserSession.getEchoUser().getUsername() );
    }
    else {
      log.info( "No todos to process" );
    }
  }
  
  /* (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    this.configure();
    
    while( !isStopped() ) {
      Preconditions.checkNotNull( echoUserSession );
      
      loginUserIfNecessary();
        
      if( echoUserSession.getEchoUser().isLoggedIn() ) {
        try {
          handleUsersTodoItems();
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Error fetching Todo items", e );
        }
      }
      
      doSleep( intervalInSeconds );
    }
  }
}
