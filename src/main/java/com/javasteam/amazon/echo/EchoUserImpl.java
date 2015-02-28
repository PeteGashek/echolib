package com.javasteam.amazon.echo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import com.javasteam.http.UserImpl;

/**
 * @author ddamon
 *
 */
public class EchoUserImpl extends UserImpl implements EchoUser {
  private final static Log log = LogFactory.getLog( EchoUserImpl.class.getName() );
  
  
  private boolean           loggedIn    = false;
  
  public EchoUserImpl() {
  }
  
  /**
   * @param user
   * @param password
   */
  public EchoUserImpl( String user, String password ) {
    super( user, password );
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
