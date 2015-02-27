package com.javasteam.amazon.echo.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerImpl;

/**
 * @author ddamon
 *
 */
@Deprecated
public class ShutdownTodoItemRetrievedPoller extends EchoCommandHandlerImpl {
  private final static Log          log = LogFactory.getLog( ShutdownTodoItemRetrievedPoller.class.getName() );
  
  public ShutdownTodoItemRetrievedPoller() {
  }

  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#handleTodoItem(com.javasteam.amazon.echo.EchoTodoItem, com.javasteam.amazon.echo.EchoUserSession, java.lang.String)
   */
  public boolean handleTodoItem( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder, String[] commands ) {
    boolean retval = false;

    EchoUser echoUser = echoUserSession.getEchoUser();
    EchoBase          echoBase = echoUserSession.getEchoBase();
    
    try {
      // We're shuttinng down so we complete this todo item.
      echoBase.completeTodoItem( todoItem, echoUser );
    }
    catch( AmazonAPIAccessException e ) {
      log.error( "Failed completing and deleting stop poller command" );
    }
    
    echoUserSession.shutdown();
    
    return retval;
  }
}
