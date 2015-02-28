/**
 * 
 */
package com.javasteam.amazon.echo.plugin.util;

import java.lang.reflect.Method;

import com.javasteam.amazon.echo.EchoTodoItemImpl;
import com.javasteam.amazon.echo.EchoUserSession;

/**
 * @author ddamon
 *
 */
public interface EchoCommandHandler {
 
  //TODO this is targeted for todo items right now.... needs generalization
  public boolean  handle( EchoTodoItemImpl todoItem, EchoUserSession echoUserSession, String remainder );
  
  public void     setName( String name );
  public String   getName();
  
  public void     setKey( String key );
  public String   getKey();
  
  public void     setCommands( String[] commands );
  public String[] getCommands();
  
  public Object   getExecutor();
  public void     setExecutor( Object executor );
  
  public Method   getMethod();
  public void     setMethod( Method method );
}
