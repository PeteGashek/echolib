package com.javasteam.http;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import com.javasteam.amazon.echo.http.EchoHttpGet;
import com.javasteam.restful.HttpClientPool;

public class HttpGetHelper extends HttpGet {
  private final static Log          log = LogFactory.getLog( HttpGetHelper.class.getName() );
  
  public final static String        DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13";
  
  private HttpClientContext context;
  
  public HttpGetHelper() {
  }

  public HttpGetHelper( URI uri ) {
    super( uri );
  }

  public HttpGetHelper( String uri ) {
    super( uri );
  }
  
  public HttpClientContext getContext() {
    return context;
  }
  
  public void setContext( HttpClientContext context ) {
    this.context = context;
  }

  public void setUserAgentHeader( String userAgent ) {
    super.setHeader( HttpHeaders.USER_AGENT, userAgent );
  }
  
  public void setAcceptHeader( String acceptString ) {
    super.setHeader( "Accept", acceptString );
  }

  public void setAcceptHeaderToApplicationJson( ) {
    setAcceptHeader( "application/json, text/javascript, */*; q=0.01" );
  }
  
  public CloseableHttpResponse execute( HttpClientPool httpClientPool ) throws ClientProtocolException, IOException {
    return httpClientPool.getHttpClient().execute( this, getContext() );
  }
  
  public void setUserContext( User user ) {
    if( user != null ) {
      this.context = user.getContext();
      //log.debug( "Set User Context to: " + context );
    }
  }
  
  public void logHeaders() {
    if( log.isDebugEnabled() ) {
      for( Header header : getAllHeaders() ) {
        log.debug( "Header: " + header.toString() );
      }
    } 
  }
  
  public String parseResponse( HttpResponse response ) throws HttpResponseException, IOException {
    String     retval = "";
    StatusLine status = response.getStatusLine();
    int        code   = status.getStatusCode();

    if( code == HttpStatus.SC_OK ) {
      retval = new BasicResponseHandler().handleResponse( response );
      
      HttpEntity entity = response.getEntity();

      if( entity != null ) {
        EntityUtils.consume( entity );
      }
    }
    
    return retval;
  }
  
  /**
   * @param actionUrl
   * @param user
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  public String httpGet( HttpClientPool httpClientPool, User user ) throws ClientProtocolException, IOException {
    String retval = "";
    
    setUserAgentHeader( DEFAULT_USER_AGENT );
    setUserContext( user );

    HttpResponse response = execute( httpClientPool );

    retval = parseResponse( response );

    user.logCookies();
    logHeaders();
    
    return retval;
  }
}
