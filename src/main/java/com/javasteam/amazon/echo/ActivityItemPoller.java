package com.javasteam.amazon.echo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.object.EchoActivityItemImpl;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;

/**
 * @author ddamon
 *
 */
public class ActivityItemPoller extends Thread {
  private final static Log          log = LogFactory.getLog( ActivityItemPoller.class.getName() );
  private Vector<EchoCommandHandler> activityListeners = new Vector<EchoCommandHandler>();
  
  private String          activityKey        = "alexa simon says ";
  private EchoUserSession echoUserSession;
  private int             intervalInSeconds  = 10;
  private int             itemRetrievalCount = 100;
  private boolean         stopped            = false;
  private Configurator    configurator       = null;
  private long            lastMaxTime        = ( new Date() ).getTime();
  
  public ActivityItemPoller() {
    super();
  }

  /**
   * @param echoUserSession
   */
  public ActivityItemPoller( final Configurator configurator, final EchoUserSession echoUserSession ) {
    this();
    
    Preconditions.checkNotNull( echoUserSession );
    Preconditions.checkNotNull( configurator );
    
    this.configurator    = configurator;
    this.echoUserSession = echoUserSession;
  }
  
  public boolean addActivityRetrievedListener( final EchoCommandHandler activityListener ) {
    boolean retval = false;
    
    if( activityListener != null &&  !this.activityListeners.contains( activityListener )) {
      this.activityListeners.add( activityListener );
      retval = true;
    }
    
    return retval;
  }
  
  private void configure() {
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

    int i = 0;
    boolean halt = false;
    do {
      String listenerString = configurator.get( "activityListener." + ++i );
      if( listenerString != null ) {
        log.info( "Activity: " + listenerString );
        EchoCommandHandlerBuilder listenerBuilder = EchoCommandHandlerDefinitionPropertyParser.getCommandHandlerBuilder( listenerString );
        EchoCommandHandler        listener        = listenerBuilder.generate();
        
        log.info(  "Addeding " + listener.getClass().getName() + " as a listener for key: " + listener.getKey() );
        addActivityRetrievedListener( listener );
      }
      else {
        halt = true;
      }
    } while( !halt );

    this.setIntervalInSeconds( todoPollingInterval );
    this.setItemRetrievalCount( todoPollingItemCount );
    echoUserSession.startActivityItemPoller();
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
  
  public void setActivityKey( String activityKey ) {
    this.activityKey = activityKey;
  }
  
  private boolean sendActivityNotification( final EchoActivityItemImpl activityItem ) {
    boolean handled = false;
    
    for( EchoCommandHandler listener : this.activityListeners ) {
      String activityCommand = activityItem.getActivityDescription().getSummary();

      // we're using simon says commands for now....
      String  lowercase     = activityCommand.toLowerCase();
      boolean matchAny      = listener.getKey().trim().equals( "*" );
      int     simonIndex    = matchAny ? 0 : lowercase.indexOf( activityKey );
      
      if( simonIndex >= 0 ) {
        if( matchAny || lowercase.startsWith( listener.getKey().toLowerCase() )) {
          int              length       = matchAny ? 0 : listener.getKey().length();
          String           remainder    = activityCommand.substring( length );
          EchoResponseItem responseItem = new EchoActivityResponseItem( listener.getKey(), remainder, activityItem );
          
          log.info( "Activity '" + activityItem.getActivityDescription().getSummary() + "' processing with listener " + listener.getKey() );
          handled = handled | listener.handle( responseItem, this.getEchoUserSession() );
        }
      }
    }
    
    return handled;
  }
  
  /**
   * @param todoItem
   */
  public void notifyActivityRetrievedListeners( final EchoActivityItemImpl activityItem ) {
    Preconditions.checkNotNull( activityItem );
    
    log.debug( "calling activitiy listeners...." );
    if( this.activityListeners != null && !this.activityListeners.isEmpty() ) {
      log.debug( "has listeners...." );
      sendActivityNotification( activityItem );
    }
  }
  
  private void handleUsersActivityItems() throws AmazonAPIAccessException, ClientProtocolException, IOException {
    Preconditions.checkNotNull( echoUserSession );
    
    List<EchoActivityItemImpl> activities = echoUserSession.getEchoBase().getActivityItems( itemRetrievalCount
                                                                                          , echoUserSession.getEchoUser()
                                                                                          , this.lastMaxTime
                                                                                          );
    
    log.info( "getting Activities for user: " + echoUserSession.getEchoUser().getUsername() );
    
    if( activities != null ) {
      for( EchoActivityItemImpl activity : activities ) {
        log.debug( "Activity from amazon: " + activity.getActivityDescription().getSummary() + " -- created: " + activity.getCreationTimestamp().getTime().getTime() );
        // amazon is not honoring the timestamps for some reason....
        if( activity.getCreationTimestamp().getTime().getTime() > this.lastMaxTime ) {
          notifyActivityRetrievedListeners( activity );
          this.lastMaxTime = activity.getCreationTimestamp().getTime().getTime() + 1;
        }
      }
      log.info( "finished Activities for user: " + echoUserSession.getEchoUser().getUsername() );
    }
    else {
      log.info( "No Activities to process" );
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
          handleUsersActivityItems();
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Error fetching Todo items", e );
        }
        catch( ClientProtocolException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch( IOException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      
      doSleep( intervalInSeconds );
    }
  }
}
