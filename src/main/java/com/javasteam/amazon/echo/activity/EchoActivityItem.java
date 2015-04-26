package com.javasteam.amazon.echo.activity;

import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.javasteam.amazon.echo.activity.core.ActivityDescription;
import com.javasteam.amazon.echo.activity.core.SourceDeviceId;

public interface EchoActivityItem {

  /**
   * @return
   */
  public abstract Calendar getCreationTimestamp();

  /**
   * @param createdDate
   */
  public abstract void setCreationTimestamp( Calendar creationTimestamp );

  /**
   * @return
   */
  public abstract Integer getVersion();

  /**
   * @param version
   */
  public abstract void setVersion( Integer version );

  /**
   * @return
   */
  public abstract String getUtteranceId();

  /**
   * @param utteranceId
   */
  public abstract void setUtteranceId( String utteranceId );

  public abstract String get_disambiguationId();

  public abstract void set_disambiguationId( String _disambiguationId );

  public abstract String getActivityStatus();

  public abstract void setActivityStatus( String activityStatus );

  public abstract String getDescription();

  public abstract void setDescription( String description )
      throws JsonParseException, JsonMappingException, IOException;

  public abstract ActivityDescription getActivityDescription();

  public abstract String getDomainAttributes();

  public abstract void setDomainAttributes( String domainAttributes )
      throws JsonParseException, JsonMappingException, IOException;

  public abstract String getDomainType();

  public abstract void setDomainType( String domainType );

  public abstract String getFeedbackAttributes();

  public abstract void setFeedbackAttributes( String feedbackAttributes );

  public abstract String getId();

  public abstract void setId( String id );

  public abstract String getIntentType();

  public abstract void setIntentType( String intentType );

  public abstract String getRegisteredCustomerId();

  public abstract void setRegisteredCustomerId( String registeredCustomerId );

  public abstract String getSourceActiveUsers();

  public abstract void setSourceActiveUsers( String sourceActiveUsers );

  public abstract SourceDeviceId[] getSourceDeviceIds();

  public abstract void setSourceDeviceIds( SourceDeviceId[] sourceDeviceIds );

}