package com.javasteam.amazon.echo.http;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import com.javasteam.http.HttpPostHelper;
import com.javasteam.http.User;
import com.javasteam.restful.HttpClientPool;
import com.google.common.base.Preconditions;

public class EchoHttpPost extends HttpPostHelper {
  private final static Log          log = LogFactory.getLog( EchoHttpPost.class.getName() );
  
  private HttpClientContext context;
  private boolean           postSuccessful = false;
  
  public EchoHttpPost() {
  }

  public EchoHttpPost( final URI uri ) {
    super( uri );
  }

  public EchoHttpPost( final String uri ) {
    super( uri );
  }

  public HttpClientContext getContext() {
    return context;
  }

  public void setContext( final HttpClientContext context ) {
    this.context = context;
  }

  public void setUserAgentHeader( final String userAgent ) {
    super.setHeader( HttpHeaders.USER_AGENT, userAgent );
  }
  
  public void setRefererHeader( final String refererUrl ) {
    super.setHeader( HttpHeaders.REFERER, refererUrl );
  }
  
  public void setAcceptHeader( String acceptString ) {
    super.setHeader( "Accept", acceptString );
  }

  public void setAcceptHeaderToApplicationJson( ) {
    setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
  }
  
  public void setApplicationJsonEntity( final String jsonString ) {
    StringEntity input = new StringEntity( jsonString, StandardCharsets.UTF_8.name() );
      
    input.setContentType( "application/json" );
      
    setEntity( input );
  }
  
  public void setUserContext( final User user ) {
    Preconditions.checkNotNull( user );
    
    this.context = user.getContext();
  }
  
  public boolean isPostSuccessful() {
    return postSuccessful;
  }

  public CloseableHttpResponse execute( final HttpClientPool httpClientPool ) throws ClientProtocolException, IOException {
    Preconditions.checkNotNull( httpClientPool );
    
    return httpClientPool.getHttpClient().execute( this, getContext() );
  }
  
  public void setEchoCsrfHeaderFromUserCookieStore( final User user ) {
    Preconditions.checkNotNull( user );
    Preconditions.checkNotNull( user.getCookieStore() );
    
    //TODO see if this is needed
    List<Cookie> cookies = user.getCookieStore().getCookies();
    for( Cookie cookie: cookies ) {
      if( cookie.getName().equalsIgnoreCase( "csrf" )) {
        setHeader( "csrf"
                 , cookie.getValue()
                 );
      }
    }
  }
  
  public void logHeaders() {
    if( log.isDebugEnabled() ) {
      for( Header header : getAllHeaders() ) {
        log.debug( "Header: " + header.toString() );
      }
    } 
  }
  
  public String parseResponse( final HttpResponse response ) throws HttpResponseException, IOException {
    Preconditions.checkNotNull( response );
    
    StatusLine   status   = response.getStatusLine();
    int          code     = status.getStatusCode();
    String       retval   = "";

    if( code == 200 ) {
      //response.getEntity();
      
      HttpEntity entity = response.getEntity();

      if( entity != null ) {
        EntityUtils.consume( entity );
      }
      
      retval = new BasicResponseHandler().handleResponse( response );
      postSuccessful = true;
      log.info( "Post successful" );
      log.debug( "amazonEchoPost returns: " + retval );
    }
    else {
      retval = new BasicResponseHandler().handleResponse( response );
      log.error( "amazonEchoPost returns Error(" + code + "): " + retval );
    }
    
    return retval;
  }
}
