package com.javasteam.amazon.echo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasteam.restful.HttpClientPool;


/**
 * @author ddamon
 *
 */
public class EchoBase {
  // static final vars
  private final static Log          log = LogFactory.getLog( EchoBase.class.getName() );
  private final static ObjectMapper mapper = new ObjectMapper();
  
  static {
    mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
  }
  
  private static HttpClientPool      httpClientPool = null;
  //private static BasicCookieStore    cookieStore    = new BasicCookieStore();
  
  public final static String DEFAULT_ECHO_URL   = "https://pitangui.amazon.com/";
  public final static String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13";
  
  // instance vars
  private String     echoURL    = DEFAULT_ECHO_URL;
  private String     userAgent  = DEFAULT_USER_AGENT;
  
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
  public static HttpClientPool getHttpClientPool( ) {
    return EchoBase.httpClientPool;
  }
  
  public EchoBase() {
  }
 
  /**
   * @param url
   */
  public EchoBase( String url ) {
    this.echoURL  = url;
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

  /**
   * @param actionUrl
   * @param user
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  private String amazonEchoGet( String actionUrl, EchoUser user ) throws ClientProtocolException, IOException {
    String retval = "";
    
    HttpGet httpGet = new HttpGet( getEchoURL() + actionUrl );
      
    httpGet.setHeader( HttpHeaders.USER_AGENT
                     , userAgent
                     );

    HttpResponse response = httpClientPool.getHttpClient().execute( httpGet, user.getContext() );
    StatusLine   status   = response.getStatusLine();
    int          code     = status.getStatusCode();
    
    if( log.isDebugEnabled() ) {
      List<Cookie> cookies = user.getCookieStore().getCookies();
      for( Cookie cookie: cookies ) {
        log.debug( cookie.getName() + "==" + cookie.getValue() );
      }

      for( Header header : httpGet.getAllHeaders() ) {
        log.debug( "Header: " + header.toString() );
      }
    }

    if( code == HttpStatus.SC_OK ) {
      response.getEntity();
      retval = new BasicResponseHandler().handleResponse( response );
      if( log.isDebugEnabled() ) {
        log.debug( "amazonEchoGet returns: " + retval );
      }
    }

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
    String  retval  = "";
    HttpPut httpPut = new HttpPut( getEchoURL() + actionUrl );
    
    List<Cookie> cookies = user.getCookieStore().getCookies();
    for( Cookie cookie: cookies ) {
      if( cookie.getName().equalsIgnoreCase( "csrf" )) {
        httpPut.setHeader( "csrf"
                         , cookie.getValue()
                         );
      }
    }
      
    httpPut.setHeader( HttpHeaders.USER_AGENT
                     , userAgent
                     );

    httpPut.setHeader( "Accept"
                     , "application/json, text/javascript, */*; q=0.01"
                     );
   
    StringEntity input;
    try {
      input = new StringEntity( jsonString, StandardCharsets.UTF_8.name() );
      input.setContentType( "application/json" );
      
      httpPut.setEntity( input );
      
      if( log.isDebugEnabled() ) {
        log.debug( "put: " + actionUrl );
        log.debug( "   : " + jsonString );
      }
      
      HttpResponse response = httpClientPool.getHttpClient().execute( httpPut, user.getContext() );
      StatusLine   status   = response.getStatusLine();
      int          code     = status.getStatusCode();

      if( code == 200 ) {
        response.getEntity();
        retval = new BasicResponseHandler().handleResponse( response );
        log.debug( "amazonEchoPut returns: " + retval );
      }
      else {
        retval = new BasicResponseHandler().handleResponse( response );
        log.error( "amazonEchoPut returns Error(" + code + "): " + retval );
      }
    } 
    catch( UnsupportedEncodingException e ) {
      log.error( "Error putting to ECHO API", e );
    }
    
    
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
    String  retval  = "";
    HttpPost httpPost = new HttpPost( getEchoURL() + actionUrl );
    
    List<Cookie> cookies = user.getCookieStore().getCookies();
    for( Cookie cookie: cookies ) {
      if( cookie.getName().equalsIgnoreCase( "csrf" )) {
        httpPost.setHeader( "csrf"
                          , cookie.getValue()
                          );
      }
    }
      
    httpPost.setHeader( HttpHeaders.USER_AGENT
                      , userAgent
                      );
 
    httpPost.setHeader( "Accept"
                      , "application/json, text/javascript, */*; q=0.01"
                      );
   
    StringEntity input;
    try {
      input = new StringEntity( jsonString, StandardCharsets.UTF_8.name() );
      input.setContentType( "application/json" );
      
      httpPost.setEntity( input );
      
      if( log.isDebugEnabled() ) {
        log.debug( "put: " + actionUrl );
        log.debug( "   : " + jsonString );
      }
      
      HttpResponse response = httpClientPool.getHttpClient().execute( httpPost, user.getContext() );
      StatusLine   status   = response.getStatusLine();
      int          code     = status.getStatusCode();

      if( code == 200 ) {
        response.getEntity();
        retval = new BasicResponseHandler().handleResponse( response );
        log.debug( "amazonEchoPut returns: " + retval );
      }
      else {
        retval = new BasicResponseHandler().handleResponse( response );
        log.error( "amazonEchoPut returns Error(" + code + "): " + retval );
      }
    } 
    catch( UnsupportedEncodingException e ) {
      log.error( "Error putting to ECHO API", e );
    }
    
    
    return retval;
  }
  
  
  /**
   * @param action
   * @param formData
   * @param user
   * @throws AmazonLoginException
   * @throws IOException
   */
  private void amazonEchoPost( String action, List <NameValuePair> formData, EchoUser user ) throws AmazonLoginException, IOException {
    HttpPost httpPost = new HttpPost( action );
    httpPost.setHeader( HttpHeaders.USER_AGENT
                      , userAgent
                      );

    httpPost.setHeader( HttpHeaders.REFERER
                      , getEchoURL()
                      );
    
    httpPost.setEntity( new UrlEncodedFormEntity( formData, StandardCharsets.UTF_8.name() ));

    HttpClientContext context = user.getContext();
    HttpResponse httpResponse = httpClientPool.getHttpClient().execute( httpPost, context );

    if( log.isDebugEnabled() ) {
      // get Cookies
      List<Cookie> cookies = user.getCookieStore().getCookies();
      for( Cookie cookie: cookies ) {
        log.debug( cookie.getName() + "==" + cookie.getValue() );
      }
    }
    
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
  public synchronized boolean echoLogin( EchoUser user) throws AmazonLoginException {
    boolean retval = user.isLoggedIn();
    log.info( "user is logged in...." + user.isLoggedIn() );
    if( !retval ) {
      log.debug(  "echo service login" );
    
      try {
        String   output = amazonEchoGet( "", user );
        Document doc    = Jsoup.parse( output );
        Elements forms  = doc.select( "form" );
        String   action = forms.attr( "action" );

        if( !action.isEmpty() ) {
          Elements             hidden   = doc.select( "input[type=hidden]" );
          List <NameValuePair> formData = new ArrayList<NameValuePair>();
        
          formData.add( new BasicNameValuePair( "email",    user.getUsername() ));
          formData.add( new BasicNameValuePair( "password", user.getPassword() ));
          formData.add( new BasicNameValuePair( "create",   "0" ));

          for( Element element : hidden ) {
            formData.add( new BasicNameValuePair( element.attr( "name" )
                                                , element.attr( "value" )));
          }
        
          amazonEchoPost( action, formData, user );

          if( log.isDebugEnabled() ) {
            log.debug( "Login successful" );
            log.info( "user got logged in...." + user.isLoggedIn() );
          }
          
          retval = true;
        }
      }
      catch( ClientProtocolException e ) {
        log.fatal( "Failure logging in", e );
        throw new AmazonLoginException( "Failure logging in to Amazon", e );
      }
      catch( IOException e ) {
        log.fatal( "Failure logging in", e );
        throw new AmazonLoginException( "Failure logging in to Amazon", e );
      }
    }

    user.setLoggedIn( retval );
    return retval;
  }
  

  /**
   * @param size
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItem> getTodoItems( int size, EchoUser user ) throws AmazonAPIAccessException {
    List<EchoTodoItem> retval = new ArrayList<EchoTodoItem>();
    
    try {
      String tasks = amazonEchoGet( "/api/todos?startTime=&endTime=&completed=&type=TASK&size=" + size + "&offset=-1", user );
      // Parse JSON
      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
      if( tasks != null && tasks.length() > 0 ) {
        TodoResponse items = mapper.readValue( tasks, TodoResponse.class );

        if( items != null && items.getValues() != null && items.getValues().length > 0 ) {
          for( EchoTodoItem todoItem : items.getValues() ) {
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
        log.error( "Amazon returned empty task list"  );
      }
    }
    catch( ClientProtocolException e ) {
      log.fatal( "Failure geting todo list", e );
      throw new AmazonAPIAccessException( "Failure getting todo list", e );
    }
    catch( JsonMappingException e ) {
      log.fatal( "Failure getting todo list- JSON mapping error", e );
      System.exit( -1 );
    }
    catch( IOException e ) {
      
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
  private List<EchoTodoItem> updateTodoItem( EchoTodoItemImpl item, EchoUser user ) throws AmazonAPIAccessException {
    List<EchoTodoItem> retval = new ArrayList<EchoTodoItem>();
    StringBuffer       path   = new StringBuffer( "/api/todos" );
    
    try {
      String itemId = item.getItemId();
      
      if( log.isDebugEnabled() ) {
        log.debug( "Putting Item: " + mapper.writeValueAsString( item ));
        //log.debug( item.toString() );
      }
      
      // have to update the localUpdated time or Amazon will not update the object
      Calendar localUpdate = Calendar.getInstance();
      localUpdate.setTime( new Date() );
      
      if( itemId != null ) {
        item.setLastLocalUpdateDate( localUpdate );
        path.append("/").append( itemId );
      }
      
      String tasks = amazonEchoPut( path.toString()
                                  , mapper.writeValueAsString( item )
                                  , user
                                  );
      
      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
    }
    catch( ClientProtocolException e ) {
      log.fatal( "Failure updating todo item", e );
      log.error(  "Path: " + path.toString() );
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
  private List<EchoTodoItem> addTodoItem( EchoTodoItemImpl item, EchoUser user ) throws AmazonAPIAccessException {
    List<EchoTodoItem> retval = new ArrayList<EchoTodoItem>();
    StringBuffer       path   = new StringBuffer( "/api/todos" );
    
    try {
      log.info( "Adding todo Item: " + mapper.writeValueAsString( item ));
      
      // have to update the localUpdated time or Amazon will not update the object
      Calendar localUpdate = Calendar.getInstance();
      localUpdate.setTime( new Date() );
      
      String tasks = amazonEchoPost( path.toString()
                                   , mapper.writeValueAsString( item )
                                   , user
                                   );
      
      if( log.isDebugEnabled() ) {
        log.debug( "tasks: " + tasks );
      }
    }
    catch( ClientProtocolException e ) {
      log.fatal( "Failure updating todo item", e );
      log.fatal(  "Path: " + path.toString() );
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
  public List<EchoTodoItem> setTodoItemDeletedStatus( EchoTodoItem item, boolean value, EchoUser user ) throws AmazonAPIAccessException {
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
  public List<EchoTodoItem> deleteTodoItem( EchoTodoItem item, EchoUser user ) throws AmazonAPIAccessException {
    return this.setTodoItemDeletedStatus( item, true, user );
  }

  /**
   * @param item
   * @param value
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItem> setTodoItemCompleteStatus( EchoTodoItem item, boolean value, EchoUser user ) throws AmazonAPIAccessException {
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
  public List<EchoTodoItem> completeTodoItem( EchoTodoItem item, EchoUser user ) throws AmazonAPIAccessException {
    return setTodoItemCompleteStatus( item, true, user );
  }

  /**
   * @param text
   * @param user
   * @return
   * @throws AmazonAPIAccessException
   */
  public List<EchoTodoItem> addTodoItem( String text, EchoUser user ) throws AmazonAPIAccessException {
    EchoTodoItemImpl item        = new EchoTodoItemImpl( text );
    Calendar         localUpdate = Calendar.getInstance();
    
    localUpdate.setTime( new Date() );
    item.setCreatedDate( localUpdate );
    item.setType( "TASK" );
    
    return addTodoItem( item, user );
  }
  
}
