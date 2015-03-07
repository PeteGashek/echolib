package com.javasteam.http;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import com.javasteam.restful.HttpClientPool;

public class HttpPostHelper extends HttpPost {
  private final static Log          log = LogFactory.getLog( HttpPostHelper.class.getName() );
  
  private HttpClientContext context;
  private boolean           postSuccessful = false;
  
  public HttpPostHelper() {
  }

  public HttpPostHelper( URI uri ) {
    super( uri );
  }

  public HttpPostHelper( String uri ) {
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
  
  public void setAcceptHeader( final String acceptString ) {
    super.setHeader( "Accept", acceptString );
  }

  public void setAcceptHeaderToApplicationJson( ) {
    setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
  }
  
  public void setApplicationJsonEntity( String jsonString ) {
    StringEntity input = new StringEntity( jsonString, StandardCharsets.UTF_8.name() );
      
    input.setContentType( "application/json" );
      
    setEntity( input );
  }
  
  public void setUserContext( final User user ) {
    if( user != null ) {
      this.context = user.getContext();
      //log.debug( "Set User Context to: " + context );
    }
  }
  
  public CloseableHttpResponse execute( final HttpClientPool httpClientPool ) throws ClientProtocolException, IOException {
    return httpClientPool.getHttpClient().execute( this, getContext() );
  }

  public boolean isPostSuccessful() {
    return postSuccessful;
  }
  
  public void logHeaders() {
    if( log.isDebugEnabled() ) {
      for( Header header : getAllHeaders() ) {
        log.debug( "Header: " + header.toString() );
      }
    } 
  }
  
  public String parseResponse( final HttpResponse response ) throws HttpResponseException, IOException {
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
      //log.info( "Post successful" );
      log.debug( "Post returns: " + retval );
    }
    else {
      retval = new BasicResponseHandler().handleResponse( response );
      log.error( "Post returns Error(" + code + "): " + retval );
    }
    
    return retval;
  }
}
