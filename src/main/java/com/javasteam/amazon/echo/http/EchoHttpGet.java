package com.javasteam.amazon.echo.http;

import java.net.URI;
import java.util.List;

import org.apache.http.cookie.Cookie;

import com.google.common.base.Preconditions;
import com.javasteam.http.HttpGetHelper;
import com.javasteam.http.User;

public class EchoHttpGet extends HttpGetHelper {
  //private final static Log          log = LogFactory.getLog( EchoHttpGet.class.getName() );
  
  
  public EchoHttpGet() {
  }

  public EchoHttpGet( final URI uri ) {
    super( uri );
  }

  public EchoHttpGet( final String uri ) {
    super( uri );
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
}
