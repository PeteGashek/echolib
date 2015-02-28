package com.javasteam.amazon.echo;

import com.javasteam.http.User;

/**
 * @author ddamon
 *
 */
public interface EchoUser extends User {
  public abstract boolean           isLoggedIn();
  public abstract void              setLoggedIn( boolean loggedIn );
}
