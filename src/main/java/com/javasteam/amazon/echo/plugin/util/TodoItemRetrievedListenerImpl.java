package com.javasteam.amazon.echo.plugin.util;


/**
 * @author ddamon
 *
 */
public abstract class TodoItemRetrievedListenerImpl implements TodoItemRetrievedListener {

  private String   name = null;
  private String   key = null;
  private String[] commands = null;
  
  /**
   * 
   */
  public TodoItemRetrievedListenerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#setName(java.lang.String)
   */
  public void setName( String name ) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#getName()
   */
  public String getName() {
    return name;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#setKey(java.lang.String)
   */
  public void setKey( String key ) {
    this.key = key;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#getKey()
   */
  public String getKey() {
    return key;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#setCommands(java.lang.String[])
   */
  public void setCommands( String[] commands ) {
    this.commands = commands;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#getCommands()
   */
  public String[] getCommands() {
    return commands;
  }
}
