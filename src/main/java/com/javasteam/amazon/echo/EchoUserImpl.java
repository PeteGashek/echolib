package com.javasteam.amazon.echo;

import com.javasteam.http.UserImpl;

/**
 * @author ddamon
 *
 */
public class EchoUserImpl extends UserImpl implements EchoUser {
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
