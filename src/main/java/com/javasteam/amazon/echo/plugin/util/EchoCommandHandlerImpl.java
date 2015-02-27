package com.javasteam.amazon.echo.plugin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUserSession;


/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerImpl implements EchoCommandHandler {

  private String   name     = null;
  private String   key      = null;
  private String[] commands = null;
  
  private Object   executor = null;
  private Method   method   = null;
  
  /**
   * 
   */
  public EchoCommandHandlerImpl() {
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
  
  public Object   getExecutor() {
    return executor;
  }
  
  public void     setExecutor( Object executor ) {
    this.executor = executor;
  }

  public Method   getMethod() {
    return method;
  }
  
  public void     setMethod( Method method ) {
    this.method = method;
  }
  
  private Object  makeMethodCall( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Object[] args = { todoItem
                    , echoUserSession
                    , remainder
                    , this.commands
                    };

    return getMethod().invoke( getExecutor(), args );
  }
  
  public boolean  handle( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder ) {
    boolean retval = false;
    
    try {
      Object response = makeMethodCall( todoItem, echoUserSession, remainder );
      if( response instanceof Boolean ) {
        retval = ( Boolean ) response;
      }
    }
    catch( IllegalAccessException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IllegalArgumentException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( InvocationTargetException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return retval;
  }
}
