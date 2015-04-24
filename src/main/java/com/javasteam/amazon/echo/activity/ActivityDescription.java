package com.javasteam.amazon.echo.activity;

public class ActivityDescription {
  private String summary;
  private String firstUtteranceId;
  private String firstStreamId;
  
  public ActivityDescription() {  
  }
  
  public String getSummary() {
    return summary;
  }
  
  public void setSummary( String summary ) {
    this.summary = summary;
  }
  
  public String getFirstUtteranceId() {
    return firstUtteranceId;
  }
  
  public void setFirstUtteranceId( String firstUtteranceId ) {
    this.firstUtteranceId = firstUtteranceId;
  }
  
  public String getFirstStreamId() {
    return firstStreamId;
  }
  
  public void setFirstStreamId( String firstStreamId ) {
    this.firstStreamId = firstStreamId;
  }

}
