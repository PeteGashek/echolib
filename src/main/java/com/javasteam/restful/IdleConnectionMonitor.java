package com.javasteam.restful;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


public class IdleConnectionMonitor extends Thread {
  private final static Log          log    = LogFactory.getLog( IdleConnectionMonitor.class.getName() );
  
  private final PoolingHttpClientConnectionManager connectionManager;
  
  private boolean stopped               = false;
  private long    idleTimeoutInSeconds  = 60;
  private long    pollIntervalInSeconds = 10;
  private Object  setterSync            = new Object();

  public IdleConnectionMonitor( PoolingHttpClientConnectionManager connectionManager ) {
    super();
    this.connectionManager = connectionManager;
  }
  
  public long getPollIntervalInSeconds() {
    long retval;
    
    synchronized( setterSync ) {
      retval = pollIntervalInSeconds;
    }
    
    return retval;
  }
  
  public long setPollIntervalInSeconds( long pollIntervalInSeconds ) {
   long retval;
    
    synchronized( setterSync ) {
      this.pollIntervalInSeconds = pollIntervalInSeconds;
      retval = getPollIntervalInSeconds();
    }
    
    return retval;
  }
  
  public long getIdleTimeoutInSeconds() {
    long retval;
    
    synchronized( setterSync ) {
      retval = idleTimeoutInSeconds;
    }
    
    return retval;
  }
  
  public long setIdleTimeoutInSeconds( long idleTimeoutInSeconds ) {
   long retval;
    
    synchronized( setterSync ) {
      this.idleTimeoutInSeconds = idleTimeoutInSeconds;
      retval = getIdleTimeoutInSeconds();
    }
    
    return retval;
  }
  
  @Override
  public void run() {
    while( !stopped ) {
      connectionManager.closeExpiredConnections();
      connectionManager.closeIdleConnections( idleTimeoutInSeconds, TimeUnit.SECONDS );
        
      log.debug( "Stats: " + connectionManager.getTotalStats() );

      try {
        Thread.sleep( this.pollIntervalInSeconds * 1000 );
      }
      catch( InterruptedException e ) {}
    }
  }

  public void shutdown()  {
    log.trace( "Shutting down client pool" );
    synchronized( setterSync ) {
      stopped = true;
    } 
  }

}
