package com.javasteam.http;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @author ddamon
 *
 */
public class UserImpl implements User {
  private final static Log log = LogFactory.getLog( UserImpl.class.getName() );
  
  private String            username    = null;
  private String            password    = null;
  private BasicCookieStore  cookieStore = new BasicCookieStore();
  
  public UserImpl() {
  }
  
  /**
   * @param user
   * @param password
   */
  public UserImpl( String user, String password ) {
    this.username = user;
    this.password = password;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#getUsername()
   */
  public String getUsername() {
    return username;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#setUser(java.lang.String)
   */
  public void setUser( String username ) {
    this.username = username;
  }

  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#getPassword()
   */
  public String getPassword() {
    return password;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#setPassword(java.lang.String)
   */
  public void setPassword( String password ) {
    this.password = password;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#getCookieStore()
   */
  public BasicCookieStore getCookieStore() {
    return cookieStore;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#setCookieStore(org.apache.http.impl.client.BasicCookieStore)
   */
  public void setCookieStore( BasicCookieStore cookieStore ) {
    this.cookieStore = cookieStore;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#getContext()
   */
  public HttpClientContext getContext() {
    HttpClientContext context = HttpClientContext.create();
    
    context.setCookieStore( cookieStore );
    //context.setUserToken( getPrincipal() );
    
    return context;
  }

  public void logCookies() {
    if( log.isDebugEnabled() ) {
      List<Cookie> cookies = getCookieStore().getCookies();
      
      for( Cookie cookie : cookies ) {
        log.debug( "User Cookie: " + cookie.getName() + "==" + cookie.getValue() );
      }
    } 
  }
  
}
