package com.javasteam.amazon.echo.activity;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.activity.core.ActivityDescription;
import com.javasteam.amazon.echo.activity.core.SourceDeviceId;


/**
 * @author ddamon
 *
 */
public class EchoActivityItemImpl implements EchoActivityItem {
  private static final ObjectMapper mapper                     = new ObjectMapper();
  
  private String              _disambiguationId;
  private String              activityStatus;
  private Calendar            creationTimestamp;
  private ActivityDescription activityDescription;  
  private String              description;  
  private String              domainAttributes;
  private String              domainType;
  private String              feedbackAttributes;
  private String              id;
  private String              intentType;
  private String              registeredCustomerId;
  private String              sourceActiveUsers;
  private SourceDeviceId[]    sourceDeviceIds;
  private String              utteranceId;
  private Integer             version;
  
  public EchoActivityItemImpl( ) {    
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
   * @see com.javasteam.amazon.echo.EchoActivityItem#getCreationTimestamp()
   */
  public Calendar getCreationTimestamp() {
    return this.creationTimestamp;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setCreationTimestamp(java.util.Calendar)
   */
  public void setCreationTimestamp( final  Calendar creationTimestamp ) {
    this.creationTimestamp = creationTimestamp;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getVersion()
   */
  public Integer getVersion() {
    return version;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setVersion(java.lang.Integer)
   */
  public void setVersion( final Integer version ) {
    this.version = version;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getUtteranceId()
   */
  public String getUtteranceId() {
    return utteranceId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setUtteranceId(java.lang.String)
   */
  public void setUtteranceId( final String utteranceId ) {
    this.utteranceId = utteranceId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#get_disambiguationId()
   */
  public String get_disambiguationId() {
    return _disambiguationId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#set_disambiguationId(java.lang.String)
   */
  public void set_disambiguationId( String _disambiguationId ) {
    this._disambiguationId = _disambiguationId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getActivityStatus()
   */
  public String getActivityStatus() {
    return activityStatus;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setActivityStatus(java.lang.String)
   */
  public void setActivityStatus( String activityStatus ) {
    this.activityStatus = activityStatus;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDescription()
   */
  public String getDescription() {
    return description;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDescription(java.lang.String)
   */
  public void setDescription( String description ) throws JsonParseException, JsonMappingException, IOException {
    this.activityDescription = mapper.readValue( description, ActivityDescription.class );
    this.description = description;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getActivityDescription()
   */
  public ActivityDescription getActivityDescription() {
    return this.activityDescription;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDomainAttributes()
   */
  public String getDomainAttributes() {
    return domainAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDomainAttributes(java.lang.String)
   */
  public void setDomainAttributes( String domainAttributes ) throws JsonParseException, JsonMappingException, IOException {
    this.domainAttributes = domainAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDomainType()
   */
  public String getDomainType() {
    return domainType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDomainType(java.lang.String)
   */
  public void setDomainType( String domainType ) {
    this.domainType = domainType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getFeedbackAttributes()
   */
  public String getFeedbackAttributes() {
    return feedbackAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setFeedbackAttributes(java.lang.String)
   */
  public void setFeedbackAttributes( String feedbackAttributes ) {
    this.feedbackAttributes = feedbackAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getId()
   */
  public String getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setId(java.lang.String)
   */
  public void setId( String id ) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getIntentType()
   */
  public String getIntentType() {
    return intentType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setIntentType(java.lang.String)
   */
  public void setIntentType( String intentType ) {
    this.intentType = intentType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getRegisteredCustomerId()
   */
  public String getRegisteredCustomerId() {
    return registeredCustomerId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setRegisteredCustomerId(java.lang.String)
   */
  public void setRegisteredCustomerId( String registeredCustomerId ) {
    this.registeredCustomerId = registeredCustomerId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getSourceActiveUsers()
   */
  public String getSourceActiveUsers() {
    return sourceActiveUsers;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setSourceActiveUsers(java.lang.String)
   */
  public void setSourceActiveUsers( String sourceActiveUsers ) {
    this.sourceActiveUsers = sourceActiveUsers;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getSourceDeviceIds()
   */
  public SourceDeviceId[] getSourceDeviceIds() {
    return sourceDeviceIds;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setSourceDeviceIds(com.javasteam.amazon.echo.activity.SourceDeviceId[])
   */
  public void setSourceDeviceIds( SourceDeviceId[] sourceDeviceIds ) {
    this.sourceDeviceIds = sourceDeviceIds;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder( 250 );

    /*
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
    */
    
    return builder.toString();
  }
  
  @Override
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      //EchoTodoItemImpl comparisonObject = (EchoTodoItemImpl) otherObject;
    
      /*
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
      */
    }
    
    return retval;
   }
}
