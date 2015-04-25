package com.javasteam.amazon.echo.plugin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javasteam.amazon.echo.EchoResponseItem;
import com.javasteam.amazon.echo.EchoUserSession;

/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerBuilder extends EchoCommandHandlerDefinition {
  private final static Log log = LogFactory.getLog( EchoCommandHandlerBuilder.class.getName() );
  
  //TODO this is targeted for todo items right now.... needs generalization
  private static Class<?>[] methodSignature = new Class[] { EchoResponseItem.class
                                                          , EchoUserSession.class
                                                          , String[].class 
                                                          };
  
  
  public EchoCommandHandlerBuilder() {
    super();
  }
  
  private EchoCommandHandler getEchoCommandHandler() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {
    EchoCommandHandlerImpl retval = new EchoCommandHandlerImpl();
    
    Class<?> theClass = Class.forName( this.getTheClassname() );
    retval.setExecutor( theClass.newInstance() );
    retval.setKey( this.getKey() );
    retval.setCommands( this.getCommandArray() );
    retval.setName( this.getTheClassname() );
    retval.setMethod( theClass.getDeclaredMethod( getTheMethodName(), methodSignature ));
    
    return retval;
  }
  
  public EchoCommandHandler generate() {
    EchoCommandHandler retval = null;
    
    try {
      retval = getEchoCommandHandler();
      
      if( retval.getMethod().getReturnType() != Boolean.TYPE ) {
        log.error( "Method: " + this.getTheMethodName() + " in class: " + this.getTheClassname() + "must return a boolean" );
        retval = null;
      }
    }
    catch( ClassNotFoundException e ) {
      log.error( this.getTheClassname() + " class must be in class path." );
    }
    catch( InstantiationException e ) {
     log.error( this.getTheClassname() + " class is not concrete." );
    }
    catch( IllegalAccessException e ) {
      log.error( this.getTheClassname() + " class must have a no-arg constructor." );
    }
    catch( NoSuchMethodException e ) {
      log.error( this.getTheClassname() + " does not have specified method: " + this.getTheMethodName()+ "." );
    }
    catch( SecurityException e ) {
      log.error( "Security Exception", e );
    }
    
    return retval;
  }
  
  public void checkStringForKeyOption( final String theString ) {
    if( theString.toLowerCase().startsWith( "key=" )) {
      setKey( theString.substring( "key=".length() ).trim() );
    }
  }
  
  public void checkStringForCommandOption( final String theString ) {
    if( theString.toLowerCase().startsWith( "command=" )) {
      String   temp         = theString.substring( "command=".length() );
      String[] commandArray = cleanupCommandArray( temp.split( " (?=(([^'\"]*['\"]){2})*[^'\"]*$)" ));
      
      if( commandArray.length > 0 ) {
        setCommandArray( commandArray );
      }
    }
  }
  
  public String[] cleanupCommandArray( final String[] commandArray ) {
    String[] retval = new String[ commandArray.length ];
    
    if( commandArray != null && commandArray.length > 0 ) {
      for( int y = 0; y < commandArray.length; ++y ) {
        retval[ y ] = commandArray[ y ].trim();
      }
    }
    
    return retval;
  }
}
