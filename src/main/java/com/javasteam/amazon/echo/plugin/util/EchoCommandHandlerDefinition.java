package com.javasteam.amazon.echo.plugin.util;


/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerDefinition {
  private String   theClassname  = null;
  private String   theMethodName = null;
  private String   key           = null;
  private String   queue         = null;
  private String[] commandArray  = null;

  public EchoCommandHandlerDefinition() {
  }
  
  public String getTheClassname() {
    return theClassname;
  }

  public void setTheMethodName( final String theMethodName ) {
    this.theMethodName = theMethodName;
  }
  
  public String getTheMethodName() {
    return theMethodName;
  }

  public void setTheClassname( final String theClassname ) {
    this.theClassname = theClassname;
  }

  public String getKey() {
    return key;
  }

  public void setKey( final String key ) {
    this.key = key;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue( final String queue ) {
    this.queue = queue;
  }

  public String[] getCommandArray() {
    return commandArray;
  }

  public void setCommandArray( final String[] commandArray ) {
    this.commandArray = commandArray;
  }
}
