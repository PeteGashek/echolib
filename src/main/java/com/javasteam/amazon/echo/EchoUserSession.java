package com.javasteam.amazon.echo;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author ddamon
 *
 */
public class EchoUserSession implements EchoUser {
  private final static Log log = LogFactory.getLog( EchoUserSession.class.getName() );
  
  private Vector<EchoCommandHandler> todoListeners = new Vector<EchoCommandHandler>();
  
  private TodoItemPoller    todoItemPoller = null;
  private Object            todoPollerLock = new Object();
  
  private Properties        properties = new Properties();
  private EchoUser          echoUser;
  private EchoBase          echoBase;
  
  public EchoUserSession() {
  }
  
  /**
   * @param echoUser
   * @param echoBase
   */
  public EchoUserSession( final EchoUser echoUser, final EchoBase echoBase ) {
    this();
        
    this.echoUser = echoUser;
    this.echoBase = echoBase;
  }

  /**
   * @return
   */
  public EchoUser getEchoUser() {
    return echoUser;
  }

  /**
   * @param echoUser
   */
  public void setEchoUser( final EchoUser echoUser ) {
    this.echoUser = echoUser;
  }

  /**
   * @return
   */
  public EchoBase getEchoBase() {
    return echoBase;
  }

  /**
   * @param echoBase
   */
  public void setEchoBase( final EchoBase echoBase ) {
    this.echoBase = echoBase;
  }
  
  /**
   * @return
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties
   */
  public void setProperties( final Properties properties ) {
    this.properties = properties;
  }
  
  /**
   * @param property
   * @return
   */
  public String getProperty( final String property ) {
    return properties.getProperty( property );
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#getUsername()
   */
  public String getUsername() {
    String retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getUsername();
    }
    
    return retval;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#setUser(java.lang.String)
   */
  public void setUser( final String username ) {
    if( this.echoUser != null ) {
      echoUser.setUser( username );
    }
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#getPassword()
   */
  public String getPassword() {
    String retval = null;
    
    if( this.echoUser != null ) {
      retval = echoUser.getPassword();
    }
    
    return retval;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#setPassword(java.lang.String)
   */
  public void setPassword( final String password ) {
    if( this.echoUser != null ) {
      echoUser.setPassword( password );
    }
  }

  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#logCookies()
   */
  public void logCookies() {
    echoUser.logCookies();
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#getCookieStore()
   */
  public BasicCookieStore getCookieStore() {
    return echoUser != null ? echoUser.getCookieStore() : null;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#setCookieStore(org.apache.http.impl.client.BasicCookieStore)
   */
  public void setCookieStore( final BasicCookieStore cookieStore ) {
    checkNotNull( echoUser );
    echoUser.setCookieStore( cookieStore );
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#getContext()
   */
  public HttpClientContext getContext() {
    return echoUser != null ? echoUser.getContext() : null;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#isLoggedIn()
   */
  public boolean isLoggedIn() {
    return echoUser != null ? echoUser.isLoggedIn() : false;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUser#setLoggedIn(boolean)
   */
  public void setLoggedIn( final boolean loggedIn ) {
    echoUser.setLoggedIn( loggedIn );
  }

  /**
   * @param filename
   * @return
   */
  private boolean loadProperties( final String filename ) {
    boolean retval = false;
    
    checkNotNull( filename );
    
    try {
      properties.load( EchoUserSession.class.getClassLoader().getResourceAsStream( filename ));
      retval = true;
    } 
    catch( Throwable e ) {
      log.fatal( "No Properties file: " + filename );
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
  
  private boolean todoItemCanBeProcessed( final EchoTodoItemImpl todoItem ) {
    return todoItem != null && !todoItem.isComplete() && !todoItem.isDeleted() && todoItem.getText() != null;
  }
  
  /**
   * @param todoItem
   */
  public void notifyTodoRetrievedListeners( final EchoTodoItemImpl todoItem ) {
    if( this.todoListeners != null && !this.todoListeners.isEmpty() ) {
      boolean handled = false;
      
      if( todoItem.isComplete() ) {
        String timeToLiveStr = this.getProperty( "cancelledMinutesToLive" );
        long   timeToLive    = 60 * 60000; // 60 minutes
        
        if( timeToLiveStr != null && timeToLiveStr.length() > 0 ) {
          timeToLive = Long.parseLong( timeToLiveStr ) * 60000;
          if( timeToLive > 0 ) {
            timeToLive = timeToLive * 60000;
          }
        }
        
        Date date = new Date();
        if( log.isDebugEnabled() ) {
          log.debug( "cancelled item alive for (ms): " + ( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ));
        }

        if( timeToLive > -1 && ( date.getTime() - todoItem.getLastUpdatedDate().getTimeInMillis() ) > timeToLive ) {
          try {
            log.info( "Time to live has passed.  Deleting todo: " + todoItem.getText() );
            this.echoBase.deleteTodoItem( todoItem, this.getEchoUser() );
          }
          catch( AmazonAPIAccessException e ) {
            log.error( "Failed deleting todo item", e );
          }
        }
      }
      
      for( EchoCommandHandler listener : this.todoListeners ) {
        if( todoItemCanBeProcessed( todoItem )) {
          if( todoItem.getText().toLowerCase().startsWith( listener.getKey().toLowerCase() )) {
            String remainder = todoItem.getText().substring( listener.getKey().length() );
            log.info( "Handling '" + todoItem.getText() + "' with listener " + listener.getKey() );
            handled = handled | listener.handle( todoItem, this, remainder );
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
  
  /**
   * @return
   */
  public boolean startTodoItemPoller() {
    boolean retval = false;
    
    synchronized( this.todoPollerLock ) {
      checkNotNull( this.echoBase );
      checkNotNull( this.echoUser );
      
      if( this.todoItemPoller == null ) {
        this.todoItemPoller = new TodoItemPoller( this );
        log.debug( "Starting todoItem poller" );
        this.todoItemPoller.start();
        retval = true;
      }
    }

    return retval;
  }
  
  /**
   * @param intervalInSeconds
   */
  public void setTodoItemPollerIntervalInSeconds( final int intervalInSeconds ) {
    this.todoItemPoller.setIntervalInSeconds( intervalInSeconds );
  }
  
  /**
   * @param itemRetrievalCount
   */
  public void setTodoItemPollerItemRetrievalCount( final int itemRetrievalCount ) {
    this.todoItemPoller.setItemRetrievalCount( itemRetrievalCount );
  }
  
  /**
   * @return
   */
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
  
  /**
   * @return
   */
  public boolean isTodoPollerStopped() {
    boolean retval = true;
    
    synchronized( this.todoPollerLock ) {
      if( this.todoItemPoller != null ) {
        retval = this.todoItemPoller.isStopped();
      }
    }
    
    return retval;
  }

  public static void configureBaseFromProperties( final EchoBase echoBase, final EchoUserSession echoUserSession ) {
    String loginForm         = echoUserSession.getProperty( "loginForm" );
    String userNameField     = echoUserSession.getProperty( "userNameField" );
    String userPasswordField = echoUserSession.getProperty( "userPasswordField" );
    
    if( loginForm != null && loginForm.trim().length() > 0 ) {
      echoBase.setLoginFormName( loginForm.trim() );  
    }

    if( userNameField != null && userNameField.trim().length() > 0 ) {
      echoBase.setUserFieldName( userNameField.trim() );  
    }

    if( userPasswordField != null && userPasswordField.trim().length() > 0 ) {
      echoBase.setPasswordFieldName( userPasswordField.trim() );  
    }
  }
  
  /**
   * @param args
   */
  public static void main( final String[] args ) {
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
      
      EchoBase     echoBase = new EchoBase();
      EchoUserImpl echoUser = new EchoUserImpl( username, password );
    
      configureBaseFromProperties( echoBase, echoUserSession );

      int i = 0;
      boolean halt = false;
      do {
        String listenerString = echoUserSession.getProperty( "todoListener." + ++i );
        if( listenerString != null ) {
          EchoCommandHandlerBuilder listenerBuilder = EchoCommandHandlerDefinitionPropertyParser.getCommandHandlerBuilder( listenerString );
          EchoCommandHandler        listener        = listenerBuilder.generate();
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
