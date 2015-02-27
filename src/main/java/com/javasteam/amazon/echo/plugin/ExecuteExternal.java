package com.javasteam.amazon.echo.plugin;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javasteam.amazon.echo.EchoTodoItemImpl;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerImpl;

/**
 * @author ddamon
 *
 */
@Deprecated
public class ExecuteExternal extends EchoCommandHandlerImpl {
  private final static Log          log = LogFactory.getLog( ExecuteExternal.class.getName() );
  
  public ExecuteExternal() {
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.plugin.util.TodoItemRetrievedListener#handleTodoItem(com.javasteam.amazon.echo.EchoTodoItem, com.javasteam.amazon.echo.EchoUserSession, java.lang.String)
   */
  public boolean handleTodoItem( EchoTodoItemImpl todoItem, EchoUserSession echoUserSession, String remainder, String[] commands ) {
    boolean retval = false;
    
    String command = todoItem.getText();
    
    log.info(  "Processing command: " + command );
      
    retval = true;
      
    if( commands.length > 0 ) {
      CommandLine commandLine = new CommandLine( commands[ 0 ] );
      
      if( this.getCommands().length > 1 ) {
        for( int i = 1; i < commands.length; ++i ) {
          String theCommand = commands[ i ].trim();
          if( theCommand.equalsIgnoreCase( "%text%" )) {
            theCommand = remainder;
          }
          commandLine.addArgument( theCommand );            
        }
      }
        
      DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

      ExecuteWatchdog watchdog = new ExecuteWatchdog( 60 * 1000 );
      Executor executor = new DefaultExecutor();
      executor.setExitValue( 1 );
      executor.setWatchdog( watchdog );
      try {
        executor.execute( commandLine, resultHandler );
      }
      catch( ExecuteException e ) {
        log.error( "External progam failed to execute", e );
      }
      catch( IOException e ) {
        log.error( "External progam had IO exception", e );
      }
    }

    // TODO: do this later
    //int exitValue = resultHandler.waitFor();

    return retval;
  }
}
