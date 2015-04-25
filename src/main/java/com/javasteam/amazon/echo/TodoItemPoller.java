package com.javasteam.amazon.echo;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.object.EchoTodoItemRetrieved;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;

/**
 * @author ddamon
 *
 */
public class TodoItemPoller extends PollerBase {
  private final static Log          log = LogFactory.getLog( TodoItemPoller.class.getName() );
  
  private Vector<EchoCommandHandler> todoListeners     = new Vector<EchoCommandHandler>();
 
  /**
   * @param echoUserSession
   */
  public TodoItemPoller( final Configurator configurator, final EchoUserSession echoUserSession ) {
    super( configurator, echoUserSession );
  }
    
  private void configure() {
    super.baseConfigure();

    int i = 0;
    boolean halt = false;
    do {
      String listenerString = getConfigurator().get( "todoListener." + ++i );
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

    getEchoUserSession().startTodoItemPoller();
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
        deleteTodoItemIfExpired( parseTimeToLiveString( getConfigurator().get( "cancelledMinutesToLive" ))
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

  protected void doProcess() throws AmazonAPIAccessException {
    List<EchoTodoItemRetrieved> todos = getEchoUserSession().getEchoBase().getTodoItems( getItemRetrievalCount(), getEchoUser() );
    
    log.info( "getting todos for user: " + getEchoUser().getUsername() );
    
    if( todos != null ) {
      for( EchoTodoItemRetrieved todoItem : todos ) {
        notifyTodoRetrievedListeners( todoItem );
      }
      log.info( "finished todos for user: " + getEchoUser().getUsername() );
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
    
    super.run();
  }
}
