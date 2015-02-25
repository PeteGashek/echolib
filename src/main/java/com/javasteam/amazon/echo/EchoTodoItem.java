package com.javasteam.amazon.echo;

import java.util.Calendar;
import java.util.Date;


/**
 * @author ddamon
 *
 */
public class EchoTodoItem extends EchoTodoItemImpl {
 
  private String   customerId;
  private String   originalAudioId;
  
  
  public EchoTodoItem( ) {
  }
  
  /**
   * @param itemId
   * @param text
   */
  public EchoTodoItem( String itemId, String text ) {
    super( itemId, text );
  }
  
  /**
   * @param text
   */
  public EchoTodoItem( String text ) {
    super( text );
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItemImpl#stringToCalendarFromLong(java.lang.String)
   */
  public Calendar stringToCalendarFromLong( String stringDate ) {
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
  public void setOriginalAudioId( String originalAudioId ) {
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
  public void setCustomerId( String customerId ) {
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
}
