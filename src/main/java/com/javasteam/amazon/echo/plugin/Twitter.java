/**
 * 
 */
package com.javasteam.amazon.echo.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.AmazonLoginException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoResponseItem;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.http.Form;
import com.javasteam.http.FormFieldMap;
import com.javasteam.http.HttpGetHelper;
import com.javasteam.http.User;
import com.javasteam.http.UserImpl;

/**
 * @author damon
 *
 */
public class Twitter {
  private final static Log          log                = LogFactory.getLog( Twitter.class.getName() );
  
  private static String[] hashKeys = { "hash tag ", "hashtag " };
  
  public static String LOGIN_PAGE             = "https://twitter.com/login";
  public static String SESSION_PAGE           = "https://twitter.com/sessions";
  public static String CREATE_TWIT_PAGE       = "https://twitter.com/i/tweet/create";
  public static String TWIT_I_JOT_PAGE        = "https://twitter.com/i/jot";
  public static String AUTHENTICITY_TOKEN_KEY = "authenticity_token";
  public static String PAGE_CONTEXT_KEY       = "page_context";
  public static String TAGGED_USERS_KEY       = "tagged_users";
  public static String PLACE_ID_KEY           = "place_id";
  public static String STATUS_KEY             = "status";
  public static String USER_FIELD             = "session[username_or_email]";
  public static String PASSWORD_FIELD         = "session[password]";
  
  public static String TWIT_USER_KEY          = "twit.user";
  public static String TWIT_PASSWORD_KEY      = "twit.password";
  
  private User   user              = null;
  private String authenticityToken = null;
  
  /**
	 * 
	 */
  public Twitter() {  
  }
  
  public boolean sendTwit( final EchoBase base, final String authenticityToken, final String twitText, final User user ) {
    Preconditions.checkNotNull( user );
    Preconditions.checkNotNull( twitText );
    
    boolean            retval  = false;
    Form               form    = new Form( CREATE_TWIT_PAGE );
    Map<String,String> fields  = new HashMap<String,String>();

    form.setFields( fields );
    
    fields.put( STATUS_KEY,             twitText );
    fields.put( PLACE_ID_KEY,           "" );
    fields.put( TAGGED_USERS_KEY,       "" );
    fields.put( PAGE_CONTEXT_KEY,       "profile" );
    fields.put( AUTHENTICITY_TOKEN_KEY, authenticityToken );
    
    try {
      log.info( "Sending twit: " + twitText );
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
  
  
  public synchronized String twitterLogin( final EchoBase base, final User user ) throws ClientProtocolException, IOException {
    Preconditions.checkNotNull( user );
    Preconditions.checkNotNull( base );
    
    Form     form            = FormFieldMap.getHtmlFormFieldsByAction( Twitter.LOGIN_PAGE
                                                                     , Twitter.SESSION_PAGE
                                                                     , user );
    Document document        = FormFieldMap.getDocumentByUrl( Twitter.LOGIN_PAGE, user );
    Elements elements        = document.getElementsByAttributeValue( HTML.Attribute.NAME.toString(), AUTHENTICITY_TOKEN_KEY );
    String authenticityToken = elements.get(0).attr( HTML.Attribute.VALUE.toString() );
    
    
    log.debug( AUTHENTICITY_TOKEN_KEY + ": " + authenticityToken );
    
    if( !form.getAction().isEmpty() ) {
      form.getFields().put( USER_FIELD,             user.getUsername() );
      form.getFields().put( PASSWORD_FIELD,         user.getPassword() );
      form.getFields().put( AUTHENTICITY_TOKEN_KEY, authenticityToken );
     
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
  
  private synchronized User getUser( final EchoUserSession echoUserSession ) {
    Preconditions.checkNotNull( echoUserSession );
    
    if( user == null ) {
      String username = echoUserSession.getConfigurator().get( TWIT_USER_KEY );
      String password = echoUserSession.getConfigurator().get( TWIT_PASSWORD_KEY );
      
      if( username != null && password != null && username.length() > 0 && password.length() > 0 ) {
        user = new UserImpl( username, password );
      }
    }
    
    return user;
  }
  
  public String popluateHashtags( final String text, final String[] hashKeys ) {
    String lowercaseText = text.toLowerCase();
    String retval        = text;
    
    for( String key : hashKeys ) {
      int startPoint = lowercaseText.indexOf( key.toLowerCase() );
      
      if( startPoint >= 0 ) {
        retval = retval.substring( 0, startPoint ) + "#" + retval.substring( startPoint + key.length() );
        lowercaseText = retval.toLowerCase();
      }  
    }
    
    return retval;
  }
  
  

  public String popluateHashtags( final String text ) {
    return popluateHashtags( text, Twitter.hashKeys );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerInterface
   * #handleTodoItem(com.javasteam.amazon.echo.EchoTodoItem,
   * com.javasteam.amazon.echo.EchoUserSession, java.lang.String)
   */
  public boolean sendTwit( final EchoResponseItem responseItem, final EchoUserSession echoUserSession, final String[] commands ) throws ClientProtocolException, IOException {
    Preconditions.checkNotNull( responseItem,    "Can't process a null responseItem item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );
    
    boolean retval = false;
    User    user   = getUser( echoUserSession );
    
    if( user != null ) {
      if( authenticityToken == null ) {
        authenticityToken =  twitterLogin( echoUserSession.getEchoBase(), user );
        // twitter... /i/jot sets up a cookie we seem to need
        HttpGetHelper getHelper = new HttpGetHelper( TWIT_I_JOT_PAGE );
        getHelper.httpGet( EchoBase.getHttpClientPool(), user );
      }
      
      if( authenticityToken != null ) {
        String hashTaggedRemainder = popluateHashtags( responseItem.getRemainder() );
        sendTwit( echoUserSession.getEchoBase(), authenticityToken, hashTaggedRemainder, user );
        retval = true;
      }
      else {
        log.error( "Can't send to twitter.  Not logged in." );
      }
    }

    return retval;
  }

}
