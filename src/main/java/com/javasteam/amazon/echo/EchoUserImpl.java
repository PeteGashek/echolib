package com.javasteam.amazon.echo;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

public class EchoUserImpl implements EchoUser {
  private String            username    = null;
  private String            password    = null;
  private BasicCookieStore  cookieStore = new BasicCookieStore();
  private boolean           loggedIn    = false;
  
  public EchoUserImpl() {
  }
  
  public EchoUserImpl( String user, String password ) {
    this.username     = user;
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


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#isLoggedIn()
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoUserInterface#setLoggedIn(boolean)
   */
  public void setLoggedIn( boolean loggedIn ) {
    this.loggedIn = loggedIn;
  }
  
}
