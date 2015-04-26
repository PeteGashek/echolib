package com.javasteam.amazon.echo.polling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.AmazonLoginException;
import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.util.Configurator;

public abstract class PollerBase extends Thread {
  private final static Log          log = LogFactory.getLog( PollerBase.class.getName() );
  
  private EchoUserSession echoUserSession;
  private int             intervalInSeconds  = 10;
  private int             itemRetrievalCount = 100;
  private boolean         stopped            = false;
  private Configurator    configurator       = null;
  
  protected PollerBase( final Configurator configurator, final EchoUserSession echoUserSession ) {
    Preconditions.checkNotNull( echoUserSession );
    Preconditions.checkNotNull( configurator );
    
    this.configurator    = configurator;
    this.echoUserSession = echoUserSession;
  }

  public EchoUserSession getEchoUserSession() {
    return echoUserSession;
  }

  public void setEchoUserSession( EchoUserSession echoUserSession ) {
    this.echoUserSession = echoUserSession;
  }

  public int getIntervalInSeconds() {
    return intervalInSeconds;
  }

  public void setIntervalInSeconds( int intervalInSeconds ) {
    this.intervalInSeconds = intervalInSeconds;
  }

  public int getItemRetrievalCount() {
    return itemRetrievalCount;
  }

  public void setItemRetrievalCount( int itemRetrievalCount ) {
    this.itemRetrievalCount = itemRetrievalCount;
  }

  public boolean isStopped() {
    return stopped;
  }

  public void setStopped( boolean stopped ) {
    this.stopped = stopped;
  }

  public Configurator getConfigurator() {
    return configurator;
  }

  public void setConfigurator( Configurator configurator ) {
    this.configurator = configurator;
  }
  
  protected EchoUser getEchoUser() {
    return this.echoUserSession.getEchoUser();
  }

  protected void baseConfigure() {
    String todoPollingIntervalStr  = configurator.get( "activityPollingInterval" );
    String todoPollingItemCountStr = configurator.get( "activityPollingItemCount" );
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
    
    this.setIntervalInSeconds( todoPollingInterval );
    this.setItemRetrievalCount( todoPollingItemCount );
  }
  
  protected void loginUserIfNecessary() {
    Preconditions.checkNotNull( echoUserSession );
    
    if( !echoUserSession.getEchoUser().isLoggedIn() ) {
      try {
        log.info( "Logging in user: " + getEchoUser().getUsername() );
        echoUserSession.getEchoBase().echoLogin( getEchoUser() );
      }
      catch( AmazonLoginException e ) {
        log.error( "Logging in echoUser", e  );
      }
    }
  }
  
  
  protected void doSleep( final long intervalInSeconds ) {
    int loop = 0;

    while( !isStopped() && loop < intervalInSeconds ) {
      try {
        Thread.sleep( 1000 );
      }
      catch( InterruptedException e ) {}
      ++loop;
    }
  }
  
  protected void doSleep() {
    doSleep( this.intervalInSeconds );
  }
  
  /**
   * 
   */
  public synchronized void shutdown() {
    stopped = true;
    //this.interrupt();
  }
  
  public abstract void doProcess() throws AmazonAPIAccessException;
  
  /* (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    while( !isStopped() ) {
      loginUserIfNecessary();
        
      if( getEchoUser().isLoggedIn() ) {
        try {
          doProcess();
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Error fetching Todo items", e );
        }
      }
      
      doSleep();
    }
  }

}
