/**
 * 
 */
package com.javasteam.amazon.echo.http;

import java.net.URI;
import java.util.List;

import org.apache.http.cookie.Cookie;

import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.http.HttpPutHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author ddamon
 *
 */
public class EchoHttpPut extends HttpPutHelper {
  //private final static Log          log = LogFactory.getLog( EchoHttpPut.class.getName() );
 

  /**
   * 
   */
  public EchoHttpPut() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param uri
   */
  public EchoHttpPut( URI uri ) {
    super( uri );
  }

  /**
   * @param uri
   */
  public EchoHttpPut( String uri ) {
    super( uri );
  }


  public void setEchoCsrfHeaderFromUserCookieStore( EchoUser user ) {
    checkNotNull( user );
    checkNotNull( user.getCookieStore() );
    
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
