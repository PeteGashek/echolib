package com.javasteam.amazon.echo.activity;

import com.javasteam.amazon.echo.EchoResponseItem;

public class EchoActivityResponseItem extends EchoResponseItem {

  public EchoActivityResponseItem( String keyword, String remainder, Object echoResponseObject ) {
    super( keyword, remainder, echoResponseObject );
  }

  @Override
  public String getText() {
    // TODO Auto-generated method stub
    return ((EchoActivityItem) this.getEchoResponseObject() ).getActivityDescription().getSummary();
  }

}
