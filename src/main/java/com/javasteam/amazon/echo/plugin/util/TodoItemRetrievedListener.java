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
public interface TodoItemRetrievedListener {
  /**
   * @param todoItem
   * @param echoUserSession
   * @param remainder
   * @return
   */
  public boolean  handleTodoItem( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder );
  
  /**
   * @param name
   */
  public void     setName( String name );
  
  /**
   * @return
   */
  public String   getName();
  
  /**
   * @param key
   */
  public void     setKey( String key );
  
  /**
   * @return
   */
  public String   getKey();
  
  /**
   * @param commands
   */
  public void     setCommands( String[] commands );
  
  /**
   * @return
   */
  public String[] getCommands();

}
