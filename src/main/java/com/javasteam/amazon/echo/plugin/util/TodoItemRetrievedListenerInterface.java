/**
 * 
 */
package com.javasteam.amazon.echo.plugin.util;

import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUserSession;

/**
 * @author ddamon
 *
 */
public interface TodoItemRetrievedListenerInterface {
  public boolean  handleTodoItem( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder );
  public void     setName( String name );
  public String   getName();
  public void     setKey( String key );
  public String   getKey();
  public void     setCommands( String[] commands );
  public String[] getCommands();

}
