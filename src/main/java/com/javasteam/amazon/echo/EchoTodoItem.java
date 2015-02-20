package com.javasteam.amazon.echo;

import java.util.Calendar;
import java.util.Date;


public class EchoTodoItem extends EchoTodoItemBase {
 
  private String   customerId;
  private String   originalAudioId;
  
  
  public EchoTodoItem( ) {
  }
  
  public EchoTodoItem( String itemId, String text ) {
    super( itemId, text );
  }
  
  public EchoTodoItem( String text ) {
    super( text );
  }


  public Calendar stringToCalendarFromLong( String stringDate ) {
    Calendar calendar = null;
    if( stringDate != null ) {
      Date date = new Date( Long.parseLong( stringDate ));
      
      calendar = Calendar.getInstance();
      calendar.setTime( date );
    }
    
    return calendar;
  }
  
  public String getOriginalAudioId() {
    return originalAudioId;
  }


  public void setOriginalAudioId( String originalAudioId ) {
    this.originalAudioId = originalAudioId;
  }



  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId( String customerId ) {
    this.customerId = customerId;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer( super.toString() );
    
    buffer.append( "\n  customerId:      " + this.getCustomerId() )
          .append( "\n  originalAudioId: " + this.getOriginalAudioId() )
          ;
    
    
    
    return buffer.toString();
  }
}
