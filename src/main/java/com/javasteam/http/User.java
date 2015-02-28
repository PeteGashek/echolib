package com.javasteam.http;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @author ddamon
 *
 */
public interface User {
  public abstract String            getUsername();
  public abstract void              setUser( String username );
  public abstract String            getPassword();
  public abstract void              setPassword( String password );
  public abstract BasicCookieStore  getCookieStore();
  public abstract void              setCookieStore( BasicCookieStore cookieStore );
  public abstract HttpClientContext getContext();
  public abstract void              logCookies();
}
