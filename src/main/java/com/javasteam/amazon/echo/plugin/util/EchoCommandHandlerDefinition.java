package com.javasteam.amazon.echo.plugin.util;


/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerDefinition {
  private String   theClassname  = null;
  private String   theMethodName = null;
  private String   key           = null;
  private String[] commandArray  = null;

  public EchoCommandHandlerDefinition() {
  }
  
  public String getTheClassname() {
    return theClassname;
  }

  public void setTheMethodName( String theMethodName ) {
    this.theMethodName = theMethodName;
  }
  
  public String getTheMethodName() {
    return theMethodName;
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
