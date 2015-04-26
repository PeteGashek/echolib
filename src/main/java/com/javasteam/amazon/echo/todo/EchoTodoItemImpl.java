package com.javasteam.amazon.echo.todo;

import com.google.common.base.Preconditions;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.time.DateUtils;


/**
 * @author ddamon
 *
 */
public class EchoTodoItemImpl implements EchoTodoItem {
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
  
  public EchoTodoItemImpl( ) {
    
  }
  
  /**
   * @param text
   */
  public EchoTodoItemImpl( final String text ) {
    this();
    
    Preconditions.checkNotNull( text  );
    
    this.text = text;
  }
  
  /**
   * @param itemId
   * @param text
   */
  public EchoTodoItemImpl( final String itemId, final String text ) {
    this( text );
    
    Preconditions.checkNotNull( itemId  );
    
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
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#isComplete()
   */
  public boolean isComplete() {
    return complete;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setComplete(boolean)
   */
  public void setComplete( final boolean complete ) {
    this.complete = complete;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getCreatedDate()
   */
  public Calendar getCreatedDate() {
    return createdDate;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setCreatedDate(java.util.Calendar)
   */
  public void setCreatedDate(final  Calendar createdDate ) {
    this.createdDate = createdDate;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setCreatedDateToNow()
   */
  public void setCreatedDateToNow() {
    Calendar localUpdate = Calendar.getInstance();

    localUpdate.setTime( new Date() );
    setCreatedDate( localUpdate );
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getLastLocalUpdateDate()
   */
  public Calendar getLastLocalUpdateDate() {
    return lastLocalUpdateDate;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setLastLocalUpdateDate(java.util.Calendar)
   */
  public void setLastLocalUpdateDate( final Calendar lastLocalUpdateDate ) {
    this.lastLocalUpdateDate = lastLocalUpdateDate;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getLastUpdatedDate()
   */
  public Calendar getLastUpdatedDate() {
    return lastUpdatedDate;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setLastUpdatedDate(java.util.Calendar)
   */
  public void setLastUpdatedDate( final Calendar lastUpdatedDate ) {
    this.lastUpdatedDate = lastUpdatedDate;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#isDeleted()
   */
  public boolean isDeleted() {
    return deleted;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setDeleted(boolean)
   */
  public void setDeleted( final boolean deleted ) {
    this.deleted = deleted;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getNbestItems()
   */
  public String[] getNbestItems() {
    return nbestItems;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setNbestItems(java.lang.String[])
   */
  public void setNbestItems( final String[] nbestItems ) {
    this.nbestItems = nbestItems;
  }
  
 
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getType()
   */
  public String getType() {
    return type;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setType(java.lang.String)
   */
  public void setType( final String type ) {
    this.type = type;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getVersion()
   */
  public Integer getVersion() {
    return version;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setVersion(java.lang.Integer)
   */
  public void setVersion( final Integer version ) {
    this.version = version;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getItemId()
   */
  public String getItemId() {
    return itemId;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setItemId(java.lang.String)
   */
  public void setItemId( final String itemId ) {
    this.itemId = itemId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getText()
   */
  public String getText() {
    return text;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setText(java.lang.String)
   */
  public void setText( final String text ) {
    this.text = text;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#getUtteranceId()
   */
  public String getUtteranceId() {
    return utteranceId;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoTodoItem#setUtteranceId(java.lang.String)
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
      EchoTodoItemRetrieved comparisonObject = (EchoTodoItemRetrieved) otherObject;
    
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
