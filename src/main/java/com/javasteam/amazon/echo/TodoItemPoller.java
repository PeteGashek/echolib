package com.javasteam.amazon.echo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TodoItemPoller extends Thread {
  private final static Log          log = LogFactory.getLog( TodoItemPoller.class.getName() );
  
  private EchoUserSession echoUserSession;
  private int             intervalInSeconds  = 10;
  private int             itemRetrievalCount = 100;
  private boolean         stopped            = false;
  
  public TodoItemPoller() {
    super();
  }

  public TodoItemPoller( EchoUserSession echoUserSession ) {
    this();
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

  public synchronized void shutdown() {
    stopped = true;
    //this.interrupt();
  }
  
  @Override
  public void run() {
    while( !stopped ) {
      if( !echoUserSession.getEchoUser().isLoggedIn() ) {
        try {
          log.debug( "Logging in user: " + echoUserSession.getEchoUser().getUsername() );
          echoUserSession.getEchoBase().echoLogin( echoUserSession.getEchoUser() );
        }
        catch( AmazonLoginException e ) {
          log.error( "Logging in echoUser", e  );
        }
      }
        
      if( echoUserSession.getEchoUser().isLoggedIn() ) {
        try {
          List<EchoTodoItem> todos = echoUserSession.getEchoBase().getTodoItems( itemRetrievalCount, echoUserSession.getEchoUser() );
          log.debug( "getting todos for user: " + echoUserSession.getEchoUser().getUsername() );
          if( todos != null ) {
            for( EchoTodoItem todoItem : todos ) {
              echoUserSession.notifyTodoRetrievedListeners( todoItem );
            } 
          }
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Fetching Todo items", e );
        }
      }
    }
      
    int loop = 0;
    while( !isStopped() && loop++ < intervalInSeconds ) {
      try {
        Thread.sleep( 1000 );
      }
      catch( InterruptedException e ) {}
    }
  }
}
