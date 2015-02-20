package com.javasteam.amazon.echo.plugin.util;

public class EchoListener {

  private String   theClassname = null;
  private String   key          = null;
  private String[] commandArray = null;

  public EchoListener() {
  }
  
  public String getTheClassname() {
    return theClassname;
  }

  public void setTheClassname( String theClassname ) {
    this.theClassname = theClassname;
  }

  public String getKey() {
    return key;
  }

  public void setKey( String key ) {
    this.key = key;
  }

  public String[] getCommandArray() {
    return commandArray;
  }

  public void setCommandArray( String[] commandArray ) {
    this.commandArray = commandArray;
  }

}
