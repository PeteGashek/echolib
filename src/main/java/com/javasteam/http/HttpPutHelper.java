/**
 * 
 */
package com.javasteam.http;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

import com.javasteam.restful.HttpClientPool;

/**
 * @author ddamon
 *
 */
public class HttpPutHelper extends HttpPut {
  private final static Log          log = LogFactory.getLog( HttpPutHelper.class.getName() );
  
  private HttpClientContext context;
  

  /**
   * 
   */
  public HttpPutHelper() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param uri
   */
  public HttpPutHelper( final URI uri ) {
    super( uri );
  }

  /**
   * @param uri
   */
  public HttpPutHelper( final String uri ) {
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
  
  public void setAcceptHeader( final String acceptString ) {
    super.setHeader( "Accept", acceptString );
  }

  public void setAcceptHeaderToApplicationJson() {
    setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
  }
  
  public void setApplicationJsonEntity( final String jsonString ) {
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
      response.getEntity();
      retval = new BasicResponseHandler().handleResponse( response );
      log.debug( "amazonEchoPut returns: " + retval );
    }
    else {
      retval = new BasicResponseHandler().handleResponse( response );
      log.error( "amazonEchoPut returns Error(" + code + "): " + retval );
    }
    
    return retval;
  }
}
