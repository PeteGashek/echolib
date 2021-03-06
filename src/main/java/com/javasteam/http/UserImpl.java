package com.javasteam.http;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import com.google.common.base.Preconditions;

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
  public UserImpl( final String user, final String password ) {
    Preconditions.checkNotNull( user );
    
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
  public void setUser( final String username ) {
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
  public void setPassword( final String password ) {
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
  public void setCookieStore( final BasicCookieStore cookieStore ) {
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
  
  @Override
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      UserImpl comparisonObject = (UserImpl) otherObject;
    
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( getUsername(),    comparisonObject.getUsername() )
                                  .append( getPassword(),    comparisonObject.getPassword() )
                                  .append( getCookieStore(), comparisonObject.getCookieStore() )
                                  .isEquals();
    }
    
    return retval;
   }  
  
}
