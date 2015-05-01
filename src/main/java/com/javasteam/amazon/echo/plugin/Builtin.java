package com.javasteam.amazon.echo.plugin;

import java.io.IOException;

import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.AmazonAPIAccessException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.amazon.echo.EchoResponseItem;
import com.javasteam.amazon.echo.EchoUser;
import com.javasteam.amazon.echo.EchoUserSession;
import com.javasteam.amazon.echo.todo.EchoTodoItemRetrieved;

public class Builtin {
  private final static Log log = LogFactory.getLog( Builtin.class.getName() );

  private final static HashMap<String,HashMap<String,ArrayList<String>>> commandQueue = new HashMap<String,HashMap<String,ArrayList<String>>>();
  
  public final static void queueItem( String name, String queue, String text ) {
    HashMap<String,ArrayList<String>> userQueue     = null;
    String                            namename      = name.toLowerCase();
    String                            userQueueName = queue.toLowerCase();
    
    synchronized( commandQueue ) {
      if( commandQueue.containsKey( namename )) {
        userQueue = commandQueue.get( namename );
      }
      else {
        userQueue = new HashMap<String,ArrayList<String>>();
        commandQueue.put( namename, userQueue );
      }
    }
    
    if( userQueue != null ) {
      ArrayList<String> commands = userQueue.get( userQueueName );
      if( commands == null ) {
        commands = new ArrayList<String>();
        userQueue.put( userQueueName, commands );
      }
      
      if( commands != null ) {
        String textToAdd = text.trim();
        
        if( textToAdd.length() > 0 ) {
          commands.add( textToAdd.trim() );
        }
      }
    }
  }
  
  public final static void queueItem( EchoUserSession session, String queue, String text ) {
    queueItem( session.getUsername().toLowerCase(), queue, text );
  }
  
  public final static void queueItem( String queue, String text ) {
    queueItem( "global", queue, text );
  }
  
  public final static String fetchQueuedItem( String name, String queue ) {
    HashMap<String,ArrayList<String>> userQueue     = null;
    String                            username      = name.toLowerCase();
    String                            userQueueName = queue.toLowerCase();
    String                            retval        = "";
    
    synchronized( commandQueue ) {
      if( commandQueue.containsKey( username )) {
        userQueue = commandQueue.get( username );
  
        ArrayList<String> commands = userQueue.get( userQueueName );
        if( commands != null && !commands.isEmpty() ) {
          retval = commands.get( 0 );
          commands.remove( 0 );
        }
      }
    }
    
    return retval;
  }
  
  public final static String fetchQueuedItem( EchoUserSession session, String queue ) {
    return fetchQueuedItem( session.getUsername(), queue );
  }
  
  public final static String fetchQueuedItem( String queue ) {
    return fetchQueuedItem( "global", queue );
  }
  
  public static String dumpQueues( ) {
    StringBuilder builder = new StringBuilder();
    
    if( commandQueue != null && !commandQueue.isEmpty() ) {
      synchronized( commandQueue ) {
        for( String key : commandQueue.keySet() ) {
          builder.append( "User: " ).append( key ).append( '\n' );
          
          HashMap<String,ArrayList<String>> userQueue = commandQueue.get( key );
          for( String device : userQueue.keySet() ) {
            builder.append( "  Device: " ).append( device ).append( '\n' );
            
            for( String command : userQueue.get( device )) {
              builder.append( "    Command: " ).append( command ).append( '\n' );              
            }
          }
        }
      }
    }
    
    return builder.toString();
  }
  
  public Builtin() {
  }

  public boolean createTodo( final EchoResponseItem responseItem, final EchoUserSession echoUserSession, final String[] commands ) {
    Preconditions.checkNotNull( responseItem,    "Can't process a null response item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );
    
    boolean retval = false;
    
    log.info(  "Processing create todo: " + responseItem.getText() );
      
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

  public boolean executeExternal( final EchoResponseItem responseItem, final EchoUserSession echoUserSession, final String[] commands ) {
    Preconditions.checkNotNull( responseItem,    "Can't process a null response item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );

    boolean retval = false;
    
    String command = responseItem.getText();
    
    log.info(  "Processing command: " + command );
      
    retval = true;
      
    if( commands != null && commands.length > 0 ) {
      CommandLine commandLine = buildCommandLine( commands, responseItem.getRemainder() );
        
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

  public boolean doNothing( final EchoResponseItem responseItem, final EchoUserSession echoUserSession, final String[] commands ) {
    return true;
  }

  public boolean shutdownTodoPoller( final EchoResponseItem responseItem, final EchoUserSession echoUserSession, final String[] commands ) {
    Preconditions.checkNotNull( responseItem,    "Can't process a null response item" );
    Preconditions.checkNotNull( echoUserSession, "EchoUserSession can not be null" );
    
    boolean retval = false;

    EchoUser echoUser = echoUserSession.getEchoUser();
    EchoBase echoBase = echoUserSession.getEchoBase();
    
    try {
      if( responseItem.getEchoResponseObject() instanceof EchoTodoItemRetrieved )
      // We do this here since we are shutting down and it won't be marked
      // complete otherwise.  We'd just process it again on the next start.
      echoBase.completeTodoItem((EchoTodoItemRetrieved) responseItem.getEchoResponseObject(), echoUser );
    }
    catch( AmazonAPIAccessException e ) {
      log.error( "Failed completing and deleting stop poller command" );
    }
    
    echoUserSession.shutdown();
    
    return retval;
  }
}
