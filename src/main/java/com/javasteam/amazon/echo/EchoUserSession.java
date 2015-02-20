package com.javasteam.amazon.echo;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.javasteam.amazon.echo.plugin.util.ListenerPropertyParser;
import com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerBuilder;
import com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerInterface;

public class EchoUserSession implements EchoUserInterface {
  private final static Log log = LogFactory.getLog( EchoUserSession.class.getName() );
  
  private Vector<TodoItemRetrievedListenerInterface> todoListeners = new Vector<TodoItemRetrievedListenerInterface>();
  
  private TodoItemPoller    todoItemPoller = null;
  private Object            todoPollerLock = new Object();
  
  private Properties        properties = new Properties();
  private EchoUserInterface echoUser;
  private EchoBase          echoBase;
  
  public EchoUserSession() {
  }
  
  public EchoUserSession( EchoUserInterface echoUser, EchoBase echoBase ) {
    this();
    this.echoUser = echoUser;
    this.echoBase = echoBase;
  }

  public EchoUserInterface getEchoUser() {
    return echoUser;
  }

  public void setEchoUser( EchoUserInterface echoUser ) {
    this.echoUser = echoUser;
  }

  public EchoBase getEchoBase() {
    return echoBase;
  }

  public void setEchoBase( EchoBase echoBase ) {
    this.echoBase = echoBase;
  }
  
  public Properties getProperties() {
    return properties;
  }

  public void setProperties( Properties properties ) {
    this.properties = properties;
  }
  
  public String getProperty( String property ) {
    return properties.getProperty( property );
  }

  public String getUsername() {
    String retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getUsername();
    }
    
    return retval;
  }

  public void setUser( String username ) {
    if( this.echoUser != null ) {
      echoUser.setUser( username );
    }
  }

  public String getPassword() {
    String retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getPassword();
    }
    
    return retval;
  }

  public void setPassword( String password ) {
    if( this.echoUser != null ) {
      echoUser.setPassword( password );
    }
  }

  public BasicCookieStore getCookieStore() {
    BasicCookieStore retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getCookieStore();
    }
    
    return retval;
  }

  public void setCookieStore( BasicCookieStore cookieStore ) {
    if( this.echoUser != null ) {
      echoUser.setCookieStore( cookieStore );
    }
  }

  public HttpClientContext getContext() {
    HttpClientContext retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getContext();
    }
    
    return retval;
  }

  public boolean isLoggedIn() {
    boolean retval = false;
    
    if( this.echoUser != null ) {
      retval = echoUser.isLoggedIn();
    }
    
    return retval;
  }

  public void setLoggedIn( boolean loggedIn ) {
    echoUser.setLoggedIn( loggedIn );
  }

  private boolean loadProperties( String filename ) {
    boolean retval = false;
    
    try {
      properties.load( EchoUserSession.class.getClassLoader().getResourceAsStream( filename ));
      retval = true;
    } 
    catch( Throwable e ) {
      log.fatal( "No Properties file: " + filename );
    }
    
    return retval;
  }
  
  public boolean hasTodoRetrievedListeners() {
    return this.todoListeners != null && !this.todoListeners.isEmpty();
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#addTodoRetrievedListener(com.javasteam.amazon.echo.plugin.TodoItemRetrievedListener)
   */
  public boolean addTodoRetrievedListener( TodoItemRetrievedListenerInterface todoListener ) {
    boolean retval = false;
    
    if( todoListener != null &&  !this.todoListeners.contains( todoListener )) {
      this.todoListeners.add( todoListener );
      retval = true;
    }
    
    return retval;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#removeTodoRetrievedListener(com.javasteam.amazon.echo.plugin.TodoItemRetrievedListener)
   */
  public boolean removeTodoRetrievedListener( TodoItemRetrievedListenerInterface todoListener ) {
    boolean retval = false;
    
    if( todoListener != null &&  this.todoListeners.contains( todoListener )) {
      this.todoListeners.remove( todoListener );
      retval = true;
    }
    
    return retval; 
  }
  
  public void notifyTodoRetrievedListeners( EchoTodoItem todoItem ) {
    if( this.todoListeners != null && !this.todoListeners.isEmpty() ) {
      boolean handled = false;
      
      if( todoItem.isComplete() ) {
        String timeToLiveStr = this.getProperty( "cancelledMinutesToLive" );
        long   timeToLive    = 60 * 60000; // 60 minutes
        
        if( timeToLiveStr != null && timeToLiveStr.length() > 0 ) {
          timeToLive = Long.parseLong( timeToLiveStr ) * 60000;
        }
        
        Date date = new Date();
        if( log.isDebugEnabled() ) {
          log.debug( "cancelled item alive for (ms): " + ( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ));
        }
        if(( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ) > timeToLive ) {
          try {
            log.info( "Time to live has passed.  Deleting todo: " + todoItem.getText() );
            this.echoBase.deleteTodoItem( todoItem, this.getEchoUser() );
          }
          catch( AmazonAPIAccessException e ) {
            log.error( "Failed deleting todo item", e );
          }
        }
      }
      
      for( TodoItemRetrievedListenerInterface listener : this.todoListeners ) {
        
        if( !todoItem.isComplete() && !todoItem.isDeleted() ) {
          if( todoItem.getText().toLowerCase().startsWith( listener.getKey().toLowerCase() )) {
            String remainder = todoItem.getText().substring( listener.getKey().length() );
            log.info( "Handling '" + todoItem.getText() + "' with listener " + listener.getKey() );
            handled = handled | listener.handleTodoItem( todoItem, this, remainder );
          }
        }
      }
      
      if( handled ) {
        try {
          this.getEchoBase().completeTodoItem( todoItem, this.getEchoUser() );
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Failed marking handled todo item as complete", e );
        }
      }
    }
  }
  
  public boolean startTodoItemPoller() {
    boolean retval = false;
    
    synchronized( this.todoPollerLock ) {
      if( this.echoBase != null && this.echoUser != null && this.todoItemPoller == null ) {
        this.todoItemPoller = new TodoItemPoller( this );
        log.debug( "Starting todoItem poller" );
        this.todoItemPoller.start();
        retval = true;
      }
    }

    return retval;
  }
  
  public void setTodoItemPollerIntervalInSeconds( int intervalInSeconds ) {
    this.todoItemPoller.setIntervalInSeconds( intervalInSeconds );
  }
  
  public void setTodoItemPollerItemRetrievalCount( int itemRetrievalCount ) {
    this.todoItemPoller.setItemRetrievalCount( itemRetrievalCount );
  }
  
  public boolean shutdown() {
    boolean retval = false;
    
    synchronized( this.todoPollerLock ) {
      if( this.todoItemPoller != null ) {
        this.todoItemPoller.shutdown();
        this.todoItemPoller = null;
        retval = true;
      }
    }
    
    return retval;
  }
  
  public boolean isTodoPollerStopped() {
    boolean retval = true;
    
    synchronized( this.todoPollerLock ) {
      if( this.todoItemPoller != null ) {
        retval = this.todoItemPoller.isStopped();
      }
    }
    
    return retval;
  }
  
  public static void main( String[] args ) {
    EchoUserSession echoUserSession = new EchoUserSession();
  
    if( echoUserSession.loadProperties( "echo.properties" )) {
      String username            = echoUserSession.getProperty( "username" );
      String password            = echoUserSession.getProperty( "password" );
      String pollingIntervalStr  = echoUserSession.getProperty( "pollingInterval" );
      String pollingItemCountStr = echoUserSession.getProperty( "pollingItemCount" );
      
      int pollingInterval            = 60;
      int pollingItemCount           = 100;
      int totalPoolConnections       = 5;
      int maxPoolConnectionsPerRoute = 2;
      
      if( pollingIntervalStr != null ) {
        int temp = Integer.parseInt( pollingIntervalStr );
        if( temp >= 10 ) {
          pollingInterval = temp;
        }
      }
      
      if( pollingItemCountStr != null ) {
        int temp = Integer.parseInt( pollingItemCountStr );
        if( temp > 0 ) {
          pollingItemCount = temp;
        }
      }
      
      EchoBase.setHttpClientPool( totalPoolConnections, maxPoolConnectionsPerRoute );
      EchoBase.getHttpClientPool().getMonitor().setPollIntervalInSeconds( 60 );
      EchoBase.getHttpClientPool().getMonitor().setIdleTimeoutInSeconds( 600 );
      
      EchoBase echoBase = new EchoBase();
      EchoUser echoUser = new EchoUser( username, password );
    
      int i = 0;
      boolean halt = false;
      do {
        String listenerString = echoUserSession.getProperty( "todoListener." + ++i );
        if( listenerString != null ) {
          TodoItemRetrievedListenerBuilder listenerBuilder = ListenerPropertyParser.getListenerBuilder( listenerString );
          TodoItemRetrievedListenerInterface listener = listenerBuilder.generate();
          echoUserSession.addTodoRetrievedListener( listener );
          log.info(  "Added " + listener.getClass().getName() + " as a listener for key: " + listener.getKey() );
        }
        else {
          halt = true;
        }
      } while( !halt );
      
      echoUserSession.setEchoUser( echoUser );
      echoUserSession.setEchoBase( echoBase );
    
      echoUserSession.startTodoItemPoller();
      echoUserSession.setTodoItemPollerIntervalInSeconds( pollingInterval );
      echoUserSession.setTodoItemPollerItemRetrievalCount( pollingItemCount );
    }
  }
}