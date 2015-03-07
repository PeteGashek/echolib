package com.javasteam.amazon.echo;

import com.google.common.base.Preconditions;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.time.DateUtils;


/**
 * @author ddamon
 *
 */
public class EchoTodoItemBase {
  public static String TASK_KEY = "TASK";
    
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
  
  /**
   * @param text
   */
  public EchoTodoItemBase( final String text ) {
    this();
    this.text = text;
  }
  
  /**
   * @param itemId
   * @param text
   */
  public EchoTodoItemBase( final String itemId, final String text ) {
    this( text );
    this.itemId = itemId;
  }
  

  /**
   * @param stringDate
   * @return
   */
  public Calendar generateCalendarFromLongAsString( final String stringDate ) {
    Preconditions.checkNotNull( stringDate );
    
    Long milliseconds = Long.parseLong( stringDate );
    Date date         = new Date( milliseconds );
      
    return DateUtils.toCalendar( date );
  }
  
  /**
   * @return
   */
  public boolean isComplete() {
    return complete;
  }


  /**
   * @param complete
   */
  public void setComplete( final boolean complete ) {
    this.complete = complete;
  }


  /**
   * @return
   */
  public Calendar getCreatedDate() {
    return createdDate;
  }


  /**
   * @param createdDate
   */
  public void setCreatedDate(final  Calendar createdDate ) {
    this.createdDate = createdDate;
  }

  public void setCreatedDateToNow() {
    Calendar localUpdate = Calendar.getInstance();

    localUpdate.setTime( new Date() );
    setCreatedDate( localUpdate );
  }

  /**
   * @return
   */
  public Calendar getLastLocalUpdateDate() {
    return lastLocalUpdateDate;
  }


  /**
   * @param lastLocalUpdateDate
   */
  public void setLastLocalUpdateDate( final Calendar lastLocalUpdateDate ) {
    this.lastLocalUpdateDate = lastLocalUpdateDate;
  }


  /**
   * @return
   */
  public Calendar getLastUpdatedDate() {
    return lastUpdatedDate;
  }


  /**
   * @param lastUpdatedDate
   */
  public void setLastUpdatedDate( final Calendar lastUpdatedDate ) {
    this.lastUpdatedDate = lastUpdatedDate;
  }


  /**
   * @return
   */
  public boolean isDeleted() {
    return deleted;
  }


  /**
   * @param deleted
   */
  public void setDeleted( final boolean deleted ) {
    this.deleted = deleted;
  }


  /**
   * @return
   */
  public String[] getNbestItems() {
    return nbestItems;
  }


  /**
   * @param nbestItems
   */
  public void setNbestItems( final String[] nbestItems ) {
    this.nbestItems = nbestItems;
  }
  
 
  /**
   * @return
   */
  public String getType() {
    return type;
  }


  /**
   * @param type
   */
  public void setType( final String type ) {
    this.type = type;
  }


  /**
   * @return
   */
  public Integer getVersion() {
    return version;
  }


  /**
   * @param version
   */
  public void setVersion( final Integer version ) {
    this.version = version;
  }

  /**
   * @return
   */
  public String getItemId() {
    return itemId;
  }


  /**
   * @param itemId
   */
  public void setItemId( final String itemId ) {
    this.itemId = itemId;
  }

  /**
   * @return
   */
  public String getText() {
    return text;
  }


  /**
   * @param text
   */
  public void setText( final String text ) {
    this.text = text;
  }
  
  /**
   * @return
   */
  public String getUtteranceId() {
    return utteranceId;
  }


  /**
   * @param utteranceId
   */
  public void setUtteranceId( final String utteranceId ) {
    this.utteranceId = utteranceId;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder( 250 );
    
    builder.append( "EchoTodoItem: \n  ItemId:          " ).append( this.getItemId() )
           .append( "\n  text:            " ).append( this.getText() )
           .append( "\n  type:            " ).append( this.getType() )
           .append( "\n  complete:        " ).append( this.isComplete() )
           .append( "\n  deleted:         " ).append( this.isDeleted() )
           .append( "\n  createdDate:     " ).append( ( this.getCreatedDate()         != null ? this.getCreatedDate().getTimeInMillis()         : "null" ))
           .append( "\n  lastLocalUpdate: " ).append( ( this.getLastLocalUpdateDate() != null ? this.getLastLocalUpdateDate().getTimeInMillis() : "null" ))
           .append( "\n  lastUpdated:     " ).append( ( this.getLastUpdatedDate()     != null ? this.getLastUpdatedDate().getTimeInMillis()     : "null" ))
           .append( "\n  version:         " ).append( this.getVersion() )
           .append( "\n  utteranceId:     " ).append( this.getUtteranceId() )
           ;
    
    // if created in the web app there will be no NbestItems.....
    if( this.getNbestItems() != null ) {
      for( String item : this.getNbestItems() ) {
        builder.append( "\n      ->" ).append( item );
      }
    }
    
    
    return builder.toString();
  }
  
  @Override
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      EchoTodoItemImpl comparisonObject = (EchoTodoItemImpl) otherObject;
    
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( getItemId(),              comparisonObject.getItemId() )
                                  .append( getText(),                comparisonObject.getText() )
                                  .append( getType(),                comparisonObject.getType() )
                                  .append( isComplete(),             comparisonObject.isComplete() )
                                  .append( isDeleted(),              comparisonObject.isDeleted() )
                                  .append( getCreatedDate(),         comparisonObject.getCreatedDate() )
                                  .append( getLastLocalUpdateDate(), comparisonObject.getLastLocalUpdateDate() )
                                  .append( getLastUpdatedDate(),     comparisonObject.getLastUpdatedDate() )
                                  .append( getVersion(),             comparisonObject.getVersion() )
                                  .append( getUtteranceId(),         comparisonObject.getUtteranceId() )
                                  .isEquals();
    }
    
    return retval;
   }
}
