/**
 * 
 */
package com.javasteam.amazon.echo.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.javasteam.amazon.echo.AmazonLoginException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoTodoItemImpl;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.http.Form;
import com.javasteam.http.FormFieldMap;
import com.javasteam.http.User;
import com.javasteam.http.UserImpl;

/**
 * @author damon
 *
 */
public class Twitter {
  private final static Log          log                = LogFactory.getLog( Twitter.class.getName() );
  
  private User   user              = null;
  private String authenticityToken = null;
  
  /**
	 * 
	 */
  public Twitter() {  
  }
  
  public boolean sendTwit( EchoBase base, String authenticityToken, String twitText, User user ) {
    boolean            retval  = false;
    Form               form    = new Form( "https://twitter.com/i/tweet/create" );
    Map<String,String> fields  = new HashMap<String,String>();

    form.setFields( fields );
    
    fields.put( "status",             twitText );
    fields.put( "place_id",           "" );
    fields.put( "tagged_users",       "" );
    fields.put( "page_context",       "profile" );
    fields.put( "authenticity_token", authenticityToken );
    
    try {
      base.postForm( form, user );
      retval = true;
    }
    catch( AmazonLoginException e ) {
      log.error( "Can't send to twitter.", e );
    }
    catch( IOException e ) {
      log.error( "Can't send to twitter.", e );
    }
    
    return retval;
  }
  
  public synchronized String twitterLogin( EchoBase base, User user ) throws ClientProtocolException, IOException {
    Form form = FormFieldMap.getHtmlFormFieldsByAction( "https://twitter.com/login", "https://twitter.com/sessions", user );
    
    Document doc     = FormFieldMap.getDocumentByUrl( "https://twitter.com/login", user );
    Elements elements = doc.getElementsByAttributeValue( "name", "authenticity_token" );
    
    String authenticityToken = elements.get(0).attr( "value" );
    
    log.debug( "authenticityToken: " + authenticityToken );
    
    if( !form.getAction().isEmpty() ) {
      form.getFields().put( "session[username_or_email]",    user.getUsername() );
      form.getFields().put( "session[password]",             user.getPassword() );
      form.getFields().put( "authenticity_token", authenticityToken );
     
      try {
        base.postForm( form, user );
        log.info( "Twitter login successful" );
      }
      catch( IOException e ) {
        log.error( "Twitter login Failed!", e );
      }
      catch( AmazonLoginException e ) {
        log.error( "Twitter login Failed!", e );
      }

      
    }
    
    return authenticityToken;
  }
  
  private synchronized User getUser( EchoUserSession echoUserSession ) {
    if( user == null ) {
      String username = echoUserSession.getProperty( "twit.user" );
      String password = echoUserSession.getProperty( "twit.password" );
      
      if( username != null && password != null && username.length() > 0 && password.length() > 0 ) {
        user = new UserImpl( username, password );
      }
    }
    
    return user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerInterface
   * #handleTodoItem(com.javasteam.amazon.echo.EchoTodoItem,
   * com.javasteam.amazon.echo.EchoUserSession, java.lang.String)
   */
  public boolean sendTwit( EchoTodoItemImpl todoItem, EchoUserSession echoUserSession, String remainder, String[] commands ) throws ClientProtocolException, IOException {
    boolean retval = false;
    User    user   = getUser( echoUserSession );
    
    if( user != null ) {
      if( authenticityToken == null ) {
        authenticityToken =  twitterLogin( echoUserSession.getEchoBase(), user );
        echoUserSession.getEchoBase().httpGet( "https://twitter.com/i/jot", user );
      }
      
      if( authenticityToken != null ) {
        sendTwit( echoUserSession.getEchoBase(), authenticityToken, remainder, user );
        retval = true;
      }
      else {
        log.error( "Can't send to twitter.  Not logged in." );
      }
    }

    return retval;
  }

}
