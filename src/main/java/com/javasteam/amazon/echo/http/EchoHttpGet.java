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
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

import com.javasteam.http.HttpGetHelper;
import com.javasteam.http.User;
import com.javasteam.restful.HttpClientPool;

public class EchoHttpGet extends HttpGetHelper {
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
  

  public void setEchoCsrfHeaderFromUserCookieStore( User user ) {
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
}
