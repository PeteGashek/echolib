package com.javasteam.amazon.echo.http;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.restful.HttpClientPool;

public class EchoHttpGet extends HttpGet {
  private final static Log          log = LogFactory.getLog( EchoHttpGet.class.getName() );
  
  private HttpClientContext context;
  
  public EchoHttpGet() {
  }

  public EchoHttpGet( URI uri ) {
    super( uri );
  }

  public EchoHttpGet( String uri ) {
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
  
  public void setUserContext( EchoUser user ) {
    if( user != null ) {
      this.context = user.getContext();
      //log.debug( "Set User Context to: " + context );
    }
  }
  
  public void setEchoCsrfHeaderFromUserCookieStore( EchoUser user ) {
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
      
      //if( log.isDebugEnabled() ) {
      //  log.debug( "amazonEchoGet returns: " + retval );
      //}
    }
    
    return retval;
  }
}
