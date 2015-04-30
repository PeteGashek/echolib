package com.javasteam.amazon.echo.plugin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.EchoResponseItem;
import com.javasteam.amazon.echo.EchoUserSession;


/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerImpl implements EchoCommandHandler {
  private final static Log log = LogFactory.getLog( EchoCommandHandlerImpl.class.getName() );
  
  private String   name     = null;
  private String   key      = null;
  private String   queue    = null;
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
  public void setName( final String name ) {
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
  public void setKey( final String key ) {
    this.key = key;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#getKey()
   */
  public String getKey() {
    return key;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#setQueue(java.lang.String)
   */
  public void setQueue( final String queue ) {
    this.queue = queue;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#getQueue()
   */
  public String getQueue() {
    return queue;
  }

 /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#setCommands(java.lang.String[])
   */
  public void setCommands( final String[] commands ) {
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
  
  public void     setExecutor( final Object executor ) {
    this.executor = executor;
  }

  public Method   getMethod() {
    return method;
  }
  
  public void     setMethod( final Method method ) {
    this.method = method;
  }
  
  //TODO this is targeted for todo items right now.... needs generalization
  private Object  makeMethodCall( final EchoResponseItem responseItem, final EchoUserSession echoUserSession ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Preconditions.checkNotNull( getExecutor() );
    Preconditions.checkNotNull( getMethod() );
    
    Object[] args = { responseItem
                    , echoUserSession
                    , this.commands
                    };

    if( log.isDebugEnabled() ) {
      log.debug( "Making handler call for: " + getExecutor().getClass().getSimpleName() + "." + getMethod().getName() + "()" );
    }
    
    return getMethod().invoke( getExecutor(), args );
  }
  
  public boolean  handle( final EchoResponseItem responseItem, final EchoUserSession echoUserSession) {
    boolean retval = false;
    
    try {
      Object response = makeMethodCall( responseItem, echoUserSession );
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
