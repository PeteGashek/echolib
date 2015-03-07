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

import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoTodoItemImpl;
import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.amazon.echo.EchoUserSession;
import com.google.common.base.Preconditions;

public class Builtin {
  private final static Log log = LogFactory.getLog( Builtin.class.getName() );
  
  public Builtin() {
  }

  public boolean createTodo( final EchoTodoItemImpl todoItem, final EchoUserSession echoUserSession, final String remainder, final String[] commands ) {
    Preconditions.checkNotNull( todoItem,        "Can't process a null todo item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );
    
    boolean retval = false;
    
    log.info(  "Processing create todo: " + todoItem.getText() );
      
    retval = true;
      
    if( commands != null && commands.length > 0 ) {
      for( String todo : commands ) {
        try {
          todo = todo.replaceAll( "\"", "" );
          log.info( "Adding todo: " + todo );
          echoUserSession.getEchoBase().addTodoItem( todo, echoUserSession.getEchoUser() );
          retval = true;
        }
        catch( AmazonAPIAccessException e ) {
          log.error( "Failed addin todo item: " + todo );
          retval = false;
        }
      }        
    }

    return retval;
  }
  
  private CommandLine buildCommandLine( final String[] commands, final String remainder ) {
    CommandLine commandLine = new CommandLine( commands[ 0 ] );
    
    if( commands.length > 1 ) {
      for( int i = 1; i < commands.length; ++i ) {
        String theCommand = commands[ i ].trim();
        if( theCommand.equalsIgnoreCase( "%text%" )) {
          theCommand = remainder;
        }
        commandLine.addArgument( theCommand );            
      }
    }
    
    return commandLine;
  }
  
  public boolean executeExternal( final EchoTodoItemImpl todoItem, final EchoUserSession echoUserSession, final String remainder, final String[] commands ) {
    Preconditions.checkNotNull( todoItem,        "Can't process a null todo item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );

    boolean retval = false;
    
    String command = todoItem.getText();
    
    log.info(  "Processing command: " + command );
      
    retval = true;
      
    if( commands != null && commands.length > 0 ) {
      CommandLine commandLine = buildCommandLine( commands, remainder );
        
      DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

      ExecuteWatchdog watchdog = new ExecuteWatchdog( 60 * 1000 );
      Executor        executor = new DefaultExecutor();
      
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
  
  public boolean shutdownTodoPoller( final EchoTodoItemImpl todoItem, final EchoUserSession echoUserSession, final String remainder, final String[] commands ) {
    Preconditions.checkNotNull( todoItem,        "Can't process a null todo item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );
    
    boolean retval = false;

    EchoUser echoUser = echoUserSession.getEchoUser();
    EchoBase echoBase = echoUserSession.getEchoBase();
    
    try {
      // We do this here since we are shutting down and it won't be marked
      // complete otherwise.  We'd just process it again on the next start.
      echoBase.completeTodoItem( todoItem, echoUser );
    }
    catch( AmazonAPIAccessException e ) {
      log.error( "Failed completing and deleting stop poller command" );
    }
    
    echoUserSession.shutdown();
    
    return retval;
  }
}
