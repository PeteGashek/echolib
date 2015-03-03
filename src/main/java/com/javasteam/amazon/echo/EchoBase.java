package com.javasteam.amazon.echo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasteam.amazon.echo.http.EchoHttpGet;
import com.javasteam.amazon.echo.http.EchoHttpPost;
import com.javasteam.amazon.echo.http.EchoHttpPut;
import com.javasteam.http.Form;
import com.javasteam.http.FormFieldMap;
import com.javasteam.http.User;
import com.javasteam.restful.HttpClientPool;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author ddamon
 *
 */
public class EchoBase {
  private final static Log          log                = LogFactory.getLog( EchoBase.class.getName() );
  private final static ObjectMapper mapper             = new ObjectMapper();

  static {
    mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
  }

  private static HttpClientPool     httpClientPool     = null;

  public final static String        DEFAULT_ECHO_URL   = "https://pitangui.amazon.com/";
  public final static String        DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13";

  // instance vars
  private String                    echoURL            = DEFAULT_ECHO_URL;
  private String                    userAgent          = DEFAULT_USER_AGENT;
  private String                    loginFormName      = "ap_signin_form";
  private String                    userFieldName      = "email";
  private String                    passwordFieldName  = "password";

  /**
   * @param httpClientPool
   */
  public static void setHttpClientPool( HttpClientPool httpClientPool ) {
    EchoBase.httpClientPool = httpClientPool;
  }

  /**
   * @param totalPoolConnections
   * @param maxPoolConnectionsPerRoute
   */
  public static void setHttpClientPool( int totalPoolConnections, int maxPoolConnectionsPerRoute ) {
    setHttpClientPool( new HttpClientPool( totalPoolConnections, maxPoolConnectionsPerRoute ));
  }

  /**
   * @return
   */
  public static HttpClientPool getHttpClientPool() {
    return EchoBase.httpClientPool;
  }

  public EchoBase() {
  }

  /**
   * @param url
   */
  public EchoBase( String url ) {
    this.echoURL = url;
  }

  /**
   * @return
   */
  public String getEchoURL() {
    return echoURL;
  }

  /**
   * @param echoURL
   */
  public void setEchoURL( String echoURL ) {
    this.echoURL = echoURL;
  }

  public String getLoginFormName() {
    return loginFormName;
  }

  public void setLoginFormName( String loginFormName ) {
    this.loginFormName = loginFormName;
  }

  public String getUserFieldName() {
    return userFieldName;
  }

  public void setUserFieldName( String userFieldName ) {
    this.userFieldName = userFieldName;
  }

  public String getPasswordFieldName() {
    return passwordFieldName;
  }

  public void setPasswordFieldName( String passwordFieldName ) {
    this.passwordFieldName = passwordFieldName;
  }

  private EchoHttpGet getEchoHttpGetForActionUrl( String actionUrl ) {
    return new EchoHttpGet( actionUrl );
  }

  private EchoHttpPut getEchoHttpPutForActionUrl( String actionUrl ) {
    return new EchoHttpPut( actionUrl );
  }

  private EchoHttpPost getEchoHttpPostForActionUrl( String actionUrl ) {
    return new EchoHttpPost( actionUrl );
  }

  
  /**
   * @param actionUrl
   * @param user
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  private String amazonEchoGet( String actionUrl, EchoUser user ) throws ClientProtocolException, IOException {
    checkNotNull( user );
    
    String retval = "";

    EchoHttpGet httpGet = getEchoHttpGetForActionUrl( getEchoURL() + actionUrl );

    httpGet.setUserAgentHeader( userAgent );
    httpGet.setUserContext( user );

    HttpResponse response = httpGet.execute( httpClientPool );

    retval = httpGet.parseResponse( response );

    user.logCookies();
    httpGet.logHeaders();
    
    return retval;
  }

  
  /**
   * @param actionUrl
   * @param jsonString
   * @param user
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  private String amazonEchoPut( String actionUrl, String jsonString, EchoUser user ) throws ClientProtocolException, IOException {
    checkNotNull( user );
    
    String retval = "";
    
    if( log.isDebugEnabled() ) {
      log.debug( "put: " + actionUrl );
      log.debug( "   : " + jsonString );
    }

    EchoHttpPut httpPut = getEchoHttpPutForActionUrl( getEchoURL() + actionUrl );

    httpPut.setEchoCsrfHeaderFromUserCookieStore( user );
    httpPut.setUserAgentHeader( userAgent );
    httpPut.setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
    httpPut.setApplicationJsonEntity( jsonString );
    httpPut.setUserContext( user );

    HttpResponse response = httpPut.execute( httpClientPool );

    retval = httpPut.parseResponse( response );
    
    user.logCookies();
    httpPut.logHeaders();
    
    return retval;
  }

  /**
   * @param actionUrl
   * @param jsonString
   * @param user
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  private String amazonEchoPost( String actionUrl, String jsonString, EchoUser user ) throws ClientProtocolException, IOException {
    checkNotNull( user );
    
    String       retval   = "";
    EchoHttpPost httpPost = getEchoHttpPostForActionUrl( actionUrl );
    
    httpPost.setEchoCsrfHeaderFromUserCookieStore( user );
    httpPost.setUserAgentHeader( userAgent );
    httpPost.setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
    httpPost.setUserContext( user );
    httpPost.setApplicationJsonEntity( jsonString );

    if( log.isDebugEnabled() ) {
      log.debug( "put: " + actionUrl );
      log.debug( "   : " + jsonString );
    }

    HttpResponse response = httpPost.execute( httpClientPool );
    
    retval = httpPost.parseResponse( response );
    
    user.logCookies();
    httpPost.logHeaders();
    
    return retval;
  }
  
  public void postForm( Form form, User user ) throws AmazonLoginException, IOException {
    checkNotNull( user );
    
    EchoHttpPost httpPost = this.getEchoHttpPostForActionUrl( form.getAction() );
    
    httpPost.setUserAgentHeader( userAgent );
    //httpPost.setRefererHeader( getEchoURL() );
    httpPost.setUserContext( user );
    httpPost.setEntity( new UrlEncodedFormEntity( form.generateFormData(), StandardCharsets.UTF_8.name() ));
    
    HttpResponse httpResponse = httpPost.execute( httpClientPool );
    
    log.debug( "Form response: " + httpResponse.toString() );
    
    user.logCookies();
    
    // process...
    httpResponse.getEntity();
    HttpEntity entity = httpResponse.getEntity();

    if( entity != null ) {
      EntityUtils.consume( entity );
    }
  }
  

  /**
   * @param action
   * @param formData
   * @param user
   * @throws AmazonLoginException
   * @throws IOException
   */
  private void amazonEchoPostForm( String actionUrl, List<NameValuePair> formData, EchoUser user ) throws AmazonLoginException, IOException {
    checkNotNull( user );
    
    EchoHttpPost httpPost = this.getEchoHttpPostForActionUrl( actionUrl );
    
    httpPost.setUserAgentHeader( userAgent );
    httpPost.setRefererHeader( getEchoURL() );
    httpPost.setUserContext( user );
    httpPost.setEntity( new UrlEncodedFormEntity( formData, StandardCharsets.UTF_8.name() ));
    
    HttpResponse httpResponse = httpPost.execute( httpClientPool );
    
    log.debug( "Form response: " + httpResponse.toString() );
    
    user.logCookies();
    
    // process...
    httpResponse.getEntity();
    HttpEntity entity = httpResponse.getEntity();

    if( entity != null ) {
      EntityUtils.consume( entity );
    }
  }
  
  /**
   * @param user
   * @return
   * @throws AmazonLoginException
   */
  public synchronized boolean echoLogin( EchoUser user ) throws AmazonLoginException {
    checkNotNull( user );
    
    boolean retval = user.isLoggedIn();
    
    log.debug( "Preparing to log in.  Current login status for user is: " + user.isLoggedIn() );
    
    if( !retval ) {
      log.debug( "echo service login" );
      
      Form form = FormFieldMap.getHtmlFormFieldsByFormName( getEchoURL(), loginFormName, user );
      
      try {
        if( !form.getAction().isEmpty() ) {
          form.getFields().put( userFieldName,    user.getUsername() );
          
          if( log.isDebugEnabled() ) {
            log.debug( "FormAction: " + form.getAction() );
            for( NameValuePair pair : form.generateFormData() ) {
              log.debug( "  -- " + pair.getName() + "==" + pair.getValue() );
            }
          }
          
          form.getFields().put( passwordFieldName, user.getPassword() );
          
          amazonEchoPostForm( form.getAction(), form.generateFormData(), user );

          log.info( "Login successful" );

          retval = true;
        }
      }
      catch( IOException e ) {
        log.fatal( "Failure logging in", e );
        throw new AmazonLoginException( "Failure logging in to Amazon", e );
      }
    }

    user.setLoggedIn( retval );
    
    if( log.isDebugEnabled() ) {  
      log.debug( "User login results. Logged in status: " + user.isLoggedIn() );
    }
    
    return retval;
  }


  /**
   * @param size
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> getTodoItems( int size, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    List<EchoTodoItemImpl> retval = new ArrayList<EchoTodoItemImpl>();

    user.logCookies();
    
    try {
      String tasks = amazonEchoGet( "/api/todos?startTime=&endTime=&completed=&type=TASK&size=" + size + "&offset=-1", user );
      
      // Parse JSON
      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
      
      if( tasks != null && tasks.length() > 0 ) {
        TodoResponse items = mapper.readValue( tasks, TodoResponse.class );

        if( items != null && items.getValues() != null && items.getValues().length > 0 ) {
          for( EchoTodoItemImpl todoItem : items.getValues() ) {
            if( log.isDebugEnabled() ) {
              log.debug( "Item recv'd: " + mapper.writeValueAsString( todoItem ));
            }

            if( todoItem != null && todoItem.getItemId() != null && todoItem.getText() != null ) {
              retval.add( todoItem );
            }
          }
        }
      }
      else {
        log.error( "Amazon returned empty task list" );
      }
    }
    catch( JsonMappingException e ) {
      log.fatal( "Failure getting todo list- JSON mapping error", e );
      System.exit( -1 );
    }
    catch( IOException e ) {
      log.fatal( "Failure geting todo list", e );
      throw new AmazonAPIAccessException( "Failure getting todo list", e );
    }

    return retval;
  }

  /**
   * @param item
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  private List<EchoTodoItemImpl> updateTodoItem( EchoTodoItemBase item, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    checkNotNull( item );
    
    List<EchoTodoItemImpl> retval = new ArrayList<EchoTodoItemImpl>();
    StringBuffer           path   = new StringBuffer( "/api/todos" );

    try {
      String itemId = item.getItemId();

      if( log.isDebugEnabled() ) {
        log.debug( "Putting Item: " + mapper.writeValueAsString( item ));
      }

      // have to update the localUpdated time or Amazon will not update the
      // object
      Calendar localUpdate = Calendar.getInstance();
      localUpdate.setTime( new Date() );

      if( itemId != null ) {
        item.setLastLocalUpdateDate( localUpdate );
        path.append( "/" ).append( itemId );
      }

      String tasks = amazonEchoPut( path.toString(), mapper.writeValueAsString( item ), user );

      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
    }
    catch( ClientProtocolException e ) {
      log.fatal( "Failure updating todo item", e );
      log.error( "Path: " + path.toString() );
      
      try {
        log.error( "Item: " + mapper.writeValueAsString( item ));
      }
      catch( JsonProcessingException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      
      throw new AmazonAPIAccessException( "Failure updating todo item", e );
    }
    catch( IOException e ) {
      log.fatal( "Failure updating todo item", e );
      
      throw new AmazonAPIAccessException( "Failure updating todo item", e );
    }

    return retval;
  }

  /**
   * @param item
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  private List<EchoTodoItemImpl> addTodoItem( EchoTodoItemBase item, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    checkNotNull( item );
    
    List<EchoTodoItemImpl> retval = new ArrayList<EchoTodoItemImpl>();
    StringBuffer       path   = new StringBuffer( "/api/todos" );

    try {
      log.info( "Adding todo Item: " + mapper.writeValueAsString( item ));

      // have to update the localUpdated time or Amazon will not update the
      // object
      Calendar localUpdate = Calendar.getInstance();
      localUpdate.setTime( new Date() );

      String tasks = amazonEchoPost( path.toString(), mapper.writeValueAsString( item ), user );

      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
    }
    catch( ClientProtocolException e ) {
      log.fatal( "Failure updating todo item", e );
      log.fatal( "Path: " + path.toString() );
      
      try {
        log.fatal( "Item: " + mapper.writeValueAsString( item ));
      }
      catch( JsonProcessingException e1 ) {
        e1.printStackTrace();
      }
      
      throw new AmazonAPIAccessException( "Failure updating todo item", e );
    }
    catch( IOException e ) {
      log.fatal( "Failure updating todo item", e );
      
      throw new AmazonAPIAccessException( "Failure updating todo item", e );
    }
    return retval;
  }

  /**
   * @param item
   * @param value
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> setTodoItemDeletedStatus( EchoTodoItemImpl item, boolean value, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    checkNotNull( item );
    
    log.info( "Setting todo '" + item.getText() + "' deleted status to " + value );
    
    item.setDeleted( value );
    
    return updateTodoItem( item, user );
  }

  /**
   * @param item
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> deleteTodoItem( EchoTodoItemImpl item, EchoUser user ) throws AmazonAPIAccessException {
    return this.setTodoItemDeletedStatus( item, true, user );
  }

  /**
   * @param item
   * @param value
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> setTodoItemCompleteStatus( EchoTodoItemImpl item, boolean value, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    checkNotNull( item );
    
    log.info( "Setting todo '" + item.getText() + "' complete status to " + value );
    
    item.setComplete( value );
    
    return updateTodoItem( item, user );
  }

  /**
   * @param item
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> completeTodoItem( EchoTodoItemImpl item, EchoUser user ) throws AmazonAPIAccessException {
    return setTodoItemCompleteStatus( item, true, user );
  }

  /**
   * @param text
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItemImpl> addTodoItem( String text, EchoUser user ) throws AmazonAPIAccessException {
    checkNotNull( user );
    checkNotNull( text );
    
    EchoTodoItemBase item        = new EchoTodoItemBase( text );
    
    item.setCreatedDateToNow();
    item.setType( "TASK" );

    return addTodoItem( item, user );
  }
}
