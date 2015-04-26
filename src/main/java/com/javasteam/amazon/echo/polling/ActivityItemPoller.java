package com.javasteam.amazon.echo.polling;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.EchoResponseItem;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.activity.EchoActivityItemImpl;
import com.javasteam.amazon.echo.activity.EchoActivityResponseItem;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;
import com.javasteam.util.Configurator;

/**
 * @author ddamon
 *
 */
public class ActivityItemPoller extends PollerBase {
  private final static Log          log = LogFactory.getLog( ActivityItemPoller.class.getName() );
  private Vector<EchoCommandHandler> activityListeners = new Vector<EchoCommandHandler>();
  
  private String          activityKey        = "alexa simon says ";
  
  /**
   * @param echoUserSession
   */
  public ActivityItemPoller( final Configurator configurator, final EchoUserSession echoUserSession ) {
    super( configurator, echoUserSession );
    configure();
  }
  
  private long getActivityPollingStartTime() {
    long   retval    = 0;
    String startTime = getConfigurator().get( "activityPollingStartTime" );
    
    if( startTime == null || startTime.length() == 0 || startTime.equalsIgnoreCase( "now" )) {
      retval = ( new Date() ).getTime();
    }
    else {
      retval = Long.parseLong( startTime );
    }
    
    return retval;
  }
  
  private void setActivityPollingStartTime( long startTime ) {
    getConfigurator().set( "activityPollingStartTime", (new Long( startTime )).toString() );
  }
  
  public boolean addActivityRetrievedListener( final EchoCommandHandler activityListener ) {
    boolean retval = false;
    
    if( activityListener != null &&  !this.activityListeners.contains( activityListener )) {
      this.activityListeners.add( activityListener );
      retval = true;
    }
    
    return retval;
  }
  
  public void configure() {
    super.baseConfigure();

    int i = 0;
    boolean halt = false;
    do {
      String listenerString = super.getConfigurator().get( "activityListener." + ++i );
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
  
  public void doProcess() throws AmazonAPIAccessException {
    long startTime = this.getActivityPollingStartTime();
    List<EchoActivityItemImpl> activities = getEchoUserSession().getEchoBase().getActivityItems( getItemRetrievalCount()
                                                                                               , getEchoUserSession().getEchoUser()
                                                                                               , startTime
                                                                                               );
    
    log.info( "getting Activities for user: " + getEchoUser().getUsername() );
    
    if( activities != null ) {
      for( EchoActivityItemImpl activity : activities ) {
        log.debug( "Activity from amazon: " + activity.getActivityDescription().getSummary() + " -- created: " + activity.getCreationTimestamp().getTime().getTime() );
        // amazon is not honoring the timestamps for some reason....
        if( activity.getCreationTimestamp().getTime().getTime() > startTime ) {
          notifyActivityRetrievedListeners( activity );
          
          this.setActivityPollingStartTime( activity.getCreationTimestamp().getTime().getTime() + 1 );
        }
      }
      log.info( "finished Activities for user: " + getEchoUser().getUsername() );
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
    super.run();
  }
}
