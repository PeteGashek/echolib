package com.javasteam.amazon.echo;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * @author ddamon
 *
 */
public class EchoActivityItemImpl extends EchoActivityItemBase {
 
  
  public EchoActivityItemImpl( ) {
  }
  
  


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItemImpl#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder( 50 );

    /*
    builder.append( "\n  customerId:      " ).append( this.getCustomerId() )
           .append( "\n  originalAudioId: " ).append( this.getOriginalAudioId() )
           ;
    
    */
    
    return builder.toString();
  }
  
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      //EchoActivityItemImpl comparisonObject = (EchoActivityItemImpl) otherObject;
    
      /*
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( customerId, comparisonObject.customerId )
                                  .append( originalAudioId, comparisonObject.originalAudioId )
                                  .isEquals();
      */
    }
    
    return retval;
   }
}
