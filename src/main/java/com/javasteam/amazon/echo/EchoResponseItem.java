package com.javasteam.amazon.echo;

public abstract class EchoResponseItem {
  private String keyword;
  private String remainder;
  private Object echoResponseObject;

  public EchoResponseItem( String keyword, String remainder, Object echoResponseObject ) {
    this.keyword            = keyword;
    this.remainder          = remainder;
    this.echoResponseObject = echoResponseObject;
  }

  public abstract String getText();
  
  public String getKeyword() {
    return keyword;
  }

  public void setKeyword( String keyword ) {
    this.keyword = keyword;
  }

  public String getRemainder() {
    return remainder;
  }

  public void setRemainder( String remainder ) {
    this.remainder = remainder;
  }

  public Object getEchoResponseObject() {
    return echoResponseObject;
  }

  public void setEchoResponseObject( Object echoResponseObject ) {
    this.echoResponseObject = echoResponseObject;
  }

}
