package com.javasteam.amazon.echo;

import java.util.Calendar;

import com.javasteam.amazon.echo.object.EchoActivityItemImpl;

/**
 * @author ddamon
 *
 */
public class ActivityResponse {
  private EchoActivityItemImpl[] activities;
  private Calendar startDate;
  private Calendar endDate;
  
  public ActivityResponse() {  
  }

  public Calendar getStartDate() {
    return startDate;
  }

  public void setStartDate( Calendar startDate ) {
    this.startDate = startDate;
  }

  public Calendar getEndDate() {
    return endDate;
  }

  public void setEndDate( Calendar endDate ) {
    this.endDate = endDate;
  }
  
  /**
   * @return the values
   */
  public EchoActivityItemImpl[] getActivities() {
    return activities;
  }

  /**
   * @param values the values to set
   */
  public void setActivities( final EchoActivityItemImpl[] activities ) {
    this.activities = activities;
  }
}
