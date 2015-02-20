package com.javasteam.amazon.echo;

import java.util.Calendar;
import java.util.Date;


public class EchoTodoItemBase {
  public static String TASK_KEY              = "TASK";
    
  private String   itemId;
  private String   text;
  private boolean  complete;
  private Calendar createdDate;
  private Calendar lastLocalUpdateDate;
  private Calendar lastUpdatedDate;
  private boolean  deleted;
  private String[] nbestItems;
  private String   type;
  private Integer  version;
  private String   utteranceId;
  
  public EchoTodoItemBase( ) {
    
  }
  
  public EchoTodoItemBase( String text ) {
    this();
    this.text = text;
  }
  
  public EchoTodoItemBase( String itemId, String text ) {
    this( text );
    this.itemId = itemId;
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
  
  public boolean isComplete() {
    return complete;
  }


  public void setComplete( boolean complete ) {
    this.complete = complete;
  }


  public Calendar getCreatedDate() {
    return createdDate;
  }


  public void setCreatedDate( Calendar createdDate ) {
    this.createdDate = createdDate;
  }


  public Calendar getLastLocalUpdateDate() {
    return lastLocalUpdateDate;
  }


  public void setLastLocalUpdateDate( Calendar lastLocalUpdateDate ) {
    this.lastLocalUpdateDate = lastLocalUpdateDate;
  }


  public Calendar getLastUpdatedDate() {
    return lastUpdatedDate;
  }


  public void setLastUpdatedDate( Calendar lastUpdatedDate ) {
    this.lastUpdatedDate = lastUpdatedDate;
  }


  public boolean isDeleted() {
    return deleted;
  }


  public void setDeleted( boolean deleted ) {
    this.deleted = deleted;
  }


  public String[] getNbestItems() {
    return nbestItems;
  }


  public void setNbestItems( String[] nbestItems ) {
    this.nbestItems = nbestItems;
  }
  
 
  public String getType() {
    return type;
  }


  public void setType( String type ) {
    this.type = type;
  }


  public Integer getVersion() {
    return version;
  }


  public void setVersion( Integer version ) {
    this.version = version;
  }

  public String getItemId() {
    return itemId;
  }


  public void setItemId( String itemId ) {
    this.itemId = itemId;
  }

  public String getText() {
    return text;
  }


  public void setText( String text ) {
    this.text = text;
  }
  
  public String getUtteranceId() {
    return utteranceId;
  }


  public void setUtteranceId( String utteranceId ) {
    this.utteranceId = utteranceId;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer( "EchoTodoItem: " );
    
    buffer.append( "\n  ItemId:          " + this.getItemId() )
          .append( "\n  text:            " + this.getText() )
          .append( "\n  type:            " + this.getType() )
          .append( "\n  complete:        " + this.isComplete() )
          .append( "\n  deleted:         " + this.isDeleted() )
          .append( "\n  createdDate:     " + ( this.getCreatedDate()         != null ? this.getCreatedDate().getTimeInMillis()         : "null" ))
          .append( "\n  lastLocalUpdate: " + ( this.getLastLocalUpdateDate() != null ? this.getLastLocalUpdateDate().getTimeInMillis() : "null" ))
          .append( "\n  lastUpdated:     " + ( this.getLastUpdatedDate()     != null ? this.getLastUpdatedDate().getTimeInMillis()     : "null" ))
          .append( "\n  version:         " + this.getVersion() )
          .append( "\n  utteranceId:     " + this.getUtteranceId() )
          ;
    
    // if created in the web app there will be no NbestItems.....
    if( this.getNbestItems() != null ) {
      for( String item : this.getNbestItems() ) {
        buffer.append( "\n      ->" + item );
      }
    }
    
    
    return buffer.toString();
  }
}
