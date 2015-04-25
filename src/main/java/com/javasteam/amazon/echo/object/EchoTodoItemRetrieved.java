package com.javasteam.amazon.echo.object;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * @author ddamon
 *
 */
public class EchoTodoItemRetrieved extends EchoTodoItemImpl {
 
  private String   customerId;
  private String   originalAudioId;
  
  
  public EchoTodoItemRetrieved( ) {
  }
  
  /**
   * @param itemId
   * @param text
   */
  public EchoTodoItemRetrieved( final String itemId, final String text ) {
    super( itemId, text );
  }
  
  /**
   * @param text
   */
  public EchoTodoItemRetrieved( final String text ) {
    super( text );
  }


  /**
   * @return
   */
  public String getOriginalAudioId() {
    return originalAudioId;
  }


  /**
   * @param originalAudioId
   */
  public void setOriginalAudioId( final String originalAudioId ) {
    this.originalAudioId = originalAudioId;
  }


  /**
   * @return
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * @param customerId
   */
  public void setCustomerId( final String customerId ) {
    this.customerId = customerId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItemImpl#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder( 50 );
    
    builder.append( "\n  customerId:      " ).append( this.getCustomerId() )
           .append( "\n  originalAudioId: " ).append( this.getOriginalAudioId() )
           ;
    
    
    
    return builder.toString();
  }
  
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      EchoTodoItemRetrieved comparisonObject = (EchoTodoItemRetrieved) otherObject;
    
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( customerId, comparisonObject.customerId )
                                  .append( originalAudioId, comparisonObject.originalAudioId )
                                  .isEquals();
    }
    
    return retval;
   }
}
