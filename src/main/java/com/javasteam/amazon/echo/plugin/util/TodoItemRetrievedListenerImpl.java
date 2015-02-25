package com.javasteam.amazon.echo.plugin.util;


public abstract class TodoItemRetrievedListenerImpl implements TodoItemRetrievedListener {

  private String   name = null;
  private String   key = null;
  private String[] commands = null;
  
  public TodoItemRetrievedListenerImpl() {
    super();
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public void setKey( String key ) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setCommands( String[] commands ) {
    this.commands = commands;
  }

  public String[] getCommands() {
    return commands;
  }
}
