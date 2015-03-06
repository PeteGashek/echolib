package com.javasteam.amazon.echo;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * @author ddamon
 *
 */
public class EchoTodoItemImpl extends EchoTodoItemBase {
 
  private String   customerId;
  private String   originalAudioId;
  
  
  public EchoTodoItemImpl( ) {
  }
  
  /**
   * @param itemId
   * @param text
   */
  public EchoTodoItemImpl( final String itemId, final String text ) {
    super( itemId, text );
  }
  
  /**
   * @param text
   */
  public EchoTodoItemImpl( final String text ) {
    super( text );
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItemImpl#stringToCalendarFromLong(java.lang.String)
   */
  public Calendar stringToCalendarFromLong( final String stringDate ) {
    Calendar calendar = null;
    if( stringDate != null ) {
      Date date = new Date( Long.parseLong( stringDate ));
      
      calendar = Calendar.getInstance();
      calendar.setTime( date );
    }
    
    return calendar;
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
    StringBuffer buffer = new StringBuffer( super.toString() );
    
    buffer.append( "\n  customerId:      " + this.getCustomerId() )
          .append( "\n  originalAudioId: " + this.getOriginalAudioId() )
          ;
    
    
    
    return buffer.toString();
  }
  
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      EchoTodoItemImpl comparisonObject = (EchoTodoItemImpl) otherObject;
    
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( customerId, comparisonObject.customerId )
                                  .append( originalAudioId, comparisonObject.originalAudioId )
                                  .isEquals();
    }
    
    return retval;
   }
}
