package com.javasteam.amazon.echo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.activity.EchoActivityItemImpl;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.polling.ActivityItemPoller;
import com.javasteam.amazon.echo.polling.TodoItemPoller;
import com.javasteam.util.Configurator;
import com.javasteam.util.PropertyFileConfigurator;

/**
 * @author ddamon
 *
 */
public class EchoUserSession implements EchoUser {
  private final static Log log = LogFactory.getLog( EchoUserSession.class.getName() );
  
  //private Vector<EchoCommandHandler> todoListeners     = new Vector<EchoCommandHandler>();
  //private Vector<EchoCommandHandler> activityListeners = new Vector<EchoCommandHandler>();
  
  private TodoItemPoller     todoItemPoller      = null;
  private ActivityItemPoller activityItemPoller  = null;
  private Object             todoPollerLock      = new Object();
  private Object             activityPollerLock  = new Object();
  private Configurator       configurator;
  private EchoUser           echoUser;
  private EchoBase           echoBase;
  
  public EchoUserSession() { 
  }
  
  public EchoUserSession( Configurator configurator ) {
    this.setConfigurator( configurator );
    refreshConfiguration();
  }
  
  public void refreshConfiguration() {
    String loginForm         = configurator.get( "loginForm" );
    String userNameField     = configurator.get( "userNameField" );
    String userPasswordField = configurator.get( "userPasswordField" );
    
    echoBase = new EchoBase();
    echoUser = new EchoUserImpl( configurator.get( "username" )
                               , configurator.get( "password" )
                               );
    
    
    if( StringUtils.isNotBlank( loginForm )) {
      echoBase.setLoginFormName( loginForm.trim() );  
    }

    if( StringUtils.isNotBlank( userNameField )) {
      echoBase.setUserFieldName( userNameField.trim() );  
    }

    if( StringUtils.isNotBlank( userPasswordField )) {
      echoBase.setPasswordFieldName( userPasswordField.trim() );  
    } 
    
    int totalPoolConnections       = 5;
    int maxPoolConnectionsPerRoute = 2;
      
    EchoBase.setHttpClientPool( totalPoolConnections, maxPoolConnectionsPerRoute );
    EchoBase.getHttpClientPool().getMonitor().setPollIntervalInSeconds( 60 );
    EchoBase.getHttpClientPool().getMonitor().setIdleTimeoutInSeconds( 600 );
    
    
  }
  
  public void startPollers() {
    synchronized( this.todoPollerLock ) {
      if( this.todoItemPoller != null ) {
        this.todoItemPoller.shutdown();
      }
    
      this.todoItemPoller = new TodoItemPoller( configurator, this );
      this.startTodoItemPoller();
    }

    synchronized( this.activityPollerLock ) {
      if( this.activityItemPoller != null ) {
        this.activityItemPoller.shutdown();
      }
    
      this.activityItemPoller = new ActivityItemPoller( configurator, this );
      this.startActivityItemPoller();
    }
  }
  
  /**
   * @param echoUser
   * @param echoBase
   */
  /*
  public EchoUserSession( final EchoUser echoUser, final EchoBase echoBase ) {
    this();
    
    Preconditions.checkNotNull( echoUser );
    Preconditions.checkNotNull( echoBase );
    
    this.echoUser = echoUser;
    this.echoBase = echoBase;
  }
  */

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
    
    Preconditions.checkNotNull( echoUser );
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
    Preconditions.checkNotNull( echoBase );
    this.echoBase = echoBase;
  }
  
  /**
   * @return
   */
  public Configurator getConfigurator() {
    return configurator;
  }

  /**
   * @param properties
   */
  public void setConfigurator( final Configurator configurator ) {
    Preconditions.checkNotNull( configurator );
    
    this.configurator = configurator;
  }
  
  /**
   * @param property
   * @return
   */
  public String getConfigSetting( final String key ) {
    Preconditions.checkNotNull( configurator );
    
    return configurator.get( key );
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
    Preconditions.checkNotNull( username );
    Preconditions.checkNotNull( this.echoUser );
    
    echoUser.setUser( username );
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
    Preconditions.checkNotNull( echoUser );
    
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
    Preconditions.checkNotNull( echoUser );
    
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
  

  public boolean addTodoRetrievedListener( final EchoCommandHandler todoListener ) {
    return this.todoItemPoller.addTodoRetrievedListener( todoListener );
  }
  
  public boolean removeTodoRetrievedListener( final EchoCommandHandler todoListener ) {
    return this.todoItemPoller.removeTodoRetrievedListener( todoListener );
  }
  
  private synchronized void verifyTodoItemPoller() {
    if( this.todoItemPoller == null || this.todoItemPoller.isStopped() ) {
      this.todoItemPoller = new TodoItemPoller( configurator, this );
    }
  }
  
  /**
   * @return
   */
  public boolean startTodoItemPoller() {
    boolean retval = false;
    
    synchronized( this.todoPollerLock ) {
      Preconditions.checkNotNull( this.echoBase );
      Preconditions.checkNotNull( this.echoUser );
      
      verifyTodoItemPoller();
      
      if( !this.todoItemPoller.isAlive() ) {
        log.debug( "Starting Todo Item poller" );
        this.todoItemPoller.start();
        retval = true;
      }
    }

    return retval;
  }
  

  private synchronized void verifyActivityItemPoller() {
    if( this.activityItemPoller == null || this.activityItemPoller.isStopped() ) {
      this.activityItemPoller = new ActivityItemPoller( configurator, this );
    }
  }
  
  public void notifyActivityRetrievedListeners( final EchoActivityItemImpl activityItem ) {
    verifyActivityItemPoller();
    activityItemPoller.notifyActivityRetrievedListeners( activityItem );  
  }

 
  
  /**
   * @return
   */
  public boolean startActivityItemPoller() {
    boolean retval = false;
    
    synchronized( this.activityPollerLock ) {
      Preconditions.checkNotNull( this.echoBase );
      Preconditions.checkNotNull( this.echoUser );
      
      verifyActivityItemPoller();
      
      if( !this.activityItemPoller.isAlive() ) {
        log.debug( "Starting Activity Item poller" );
        this.activityItemPoller.start();
        retval = true;
      }
    }

    return retval;
  }
  
  /**
   * @return
   */
  public boolean shutdown() {
    boolean retval = false;
    
    synchronized( this.todoPollerLock ) {
      if( this.todoItemPoller != null && this.todoItemPoller.isAlive() ) {
        this.todoItemPoller.shutdown();
        this.todoItemPoller = null;
        retval = true;
      }
    }
    
    synchronized( this.activityPollerLock ) {
      if( this.activityItemPoller != null && this.activityItemPoller.isAlive() ) {
        this.activityItemPoller.shutdown();
        this.activityItemPoller = null;
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

  /**
   * @return
   */
  public boolean isActivityPollerStopped() {
    boolean retval = true;
    
    synchronized( this.activityPollerLock ) {
      if( this.activityItemPoller != null ) {
        retval = this.activityItemPoller.isStopped();
      }
    }
    
    return retval;
  }
  
  /**
   * @param args
   */
  public static void main( final String[] args ) {
    String filename = "echo.properties";
    if( args.length == 1 ) {
      filename = args[ 0 ];
    }
    Configurator    configurator    = new PropertyFileConfigurator( filename );
    EchoUserSession echoUserSession = new EchoUserSession( configurator );
    echoUserSession.startPollers();
  }
}
