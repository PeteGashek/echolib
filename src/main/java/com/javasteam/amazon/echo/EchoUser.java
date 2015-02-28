package com.javasteam.amazon.echo;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import com.javasteam.http.User;

/**
 * @author ddamon
 *
 */
public interface EchoUser extends User {
  public abstract boolean           isLoggedIn();
  public abstract void              setLoggedIn( boolean loggedIn );
}
