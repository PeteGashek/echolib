/**
 * 
 */
package com.javasteam.amazon.echo.plugin;

import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerImpl;

/**
 * @author damon
 *
 */
public class TwitterPluginBase extends TodoItemRetrievedListenerImpl {
  //The factory instance is re-useable and thread safe.
  private static Twitter twitter;
  
  static {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled( true ).setUser( "tkismarr" ).setPassword( "greniia" );
    TwitterFactory tf = new TwitterFactory( cb.build() );
    twitter = tf.getInstance();
  }
  
  public static Twitter getTwitter() {
    return twitter;
  }

  /**
	 * 
	 */
  public TwitterPluginBase() {
    
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerInterface
   * #handleTodoItem(com.javasteam.amazon.echo.EchoTodoItem,
   * com.javasteam.amazon.echo.EchoUserSession, java.lang.String)
   */
  public boolean handleTodoItem( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder ) {
    ResponseList<Status> statuses;
    
    try {
      statuses = getTwitter().getHomeTimeline();
      System.out.println( "Showing friends timeline." );
      
      for( int i=0; i < statuses.size() ; ++i ) {
        Status status = (Status) statuses.get( i );
        System.out.println( status.getUser().getName() + ":" + status.getText() ); 
      }
    }
    catch( TwitterException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    

    return false;
  }

}
