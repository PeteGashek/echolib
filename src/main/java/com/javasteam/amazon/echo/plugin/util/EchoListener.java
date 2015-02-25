package com.javasteam.amazon.echo.plugin.util;

/**
 * @author ddamon
 *
 */
public class EchoListener {

  private String   theClassname = null;
  private String   key          = null;
  private String[] commandArray = null;

  /**
   * 
   */
  public EchoListener() {
  }
  
  /**
   * @return
   */
  public String getTheClassname() {
    return theClassname;
  }

  /**
   * @param theClassname
   */
  public void setTheClassname( String theClassname ) {
    this.theClassname = theClassname;
  }

  /**
   * @return
   */
  public String getKey() {
    return key;
  }

  /**
   * @param key
   */
  public void setKey( String key ) {
    this.key = key;
  }

  /**
   * @return
   */
  public String[] getCommandArray() {
    return commandArray;
  }

  /**
   * @param commandArray
   */
  public void setCommandArray( String[] commandArray ) {
    this.commandArray = commandArray;
  }
}
