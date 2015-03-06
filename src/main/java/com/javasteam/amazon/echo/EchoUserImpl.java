package com.javasteam.amazon.echo;

import org.apache.commons.lang3.builder.EqualsBuilder;

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
  public EchoUserImpl( final String user, final String password ) {
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
  public void setLoggedIn( final boolean loggedIn ) {
    this.loggedIn = loggedIn;
  }
  
  @Override
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      EchoUserImpl comparisonObject = (EchoUserImpl) otherObject;
    
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( isLoggedIn(), comparisonObject.isLoggedIn() )
                                  .isEquals();
    }
    
    return retval;
   }
  
}
