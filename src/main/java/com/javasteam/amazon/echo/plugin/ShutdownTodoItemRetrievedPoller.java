package com.javasteam.amazon.echo.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListenerImpl;

public class ShutdownTodoItemRetrievedPoller extends TodoItemRetrievedListenerImpl {
  private final static Log          log = LogFactory.getLog( ShutdownTodoItemRetrievedPoller.class.getName() );
  
  public ShutdownTodoItemRetrievedPoller() {
  }


  //@Override
  public boolean handleTodoItem( EchoTodoItem todoItem, EchoUserSession echoUserSession, String remainder ) {
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
