package com.javasteam.amazon.echo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;

/**
 * @author ddamon
 *
 */
public class TodoItemPoller extends Thread {
  private final static Log          log = LogFactory.getLog( TodoItemPoller.class.getName() );
  
  private EchoUserSession echoUserSession;
  private int             intervalInSeconds  = 10;
  private int             itemRetrievalCount = 100;
  private boolean         stopped            = false;
  
  public TodoItemPoller() {
    super();
  }

  /**
   * @param echoUserSession
   */
  public TodoItemPoller( final EchoUserSession echoUserSession ) {
    this();
    
    Preconditions.checkNotNull( echoUserSession );
    
    this.echoUserSession = echoUserSession;
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
  
  private void handleUsersTodoItems() throws AmazonAPIAccessException {
    Preconditions.checkNotNull( echoUserSession );
    //System.out.print( "." );
    
    List<EchoTodoItemImpl> todos = echoUserSession.getEchoBase().getTodoItems( itemRetrievalCount, echoUserSession.getEchoUser() );
    
    log.debug( "getting todos for user: " + echoUserSession.getEchoUser().getUsername() );
    
    if( todos != null ) {
      for( EchoTodoItemImpl todoItem : todos ) {
        echoUserSession.notifyTodoRetrievedListeners( todoItem );
      } 
    }
    else {
      log.info( "No todos to process" );
    }
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
  
  /* (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
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
