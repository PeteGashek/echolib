package com.javasteam.amazon.echo.plugin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javasteam.amazon.echo.EchoTodoItem;
import com.javasteam.amazon.echo.EchoUserSession;

/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerBuilder extends EchoCommandHandlerDefinition {
  private final static Log log = LogFactory.getLog( EchoCommandHandlerBuilder.class.getName() );
  
  //TODO this is targed for todo items right now.... needs generalization
  private static Class[] methodSignature = new Class[] { EchoTodoItem.class
                                                       , EchoUserSession.class
                                                       , String.class
                                                       , String[].class 
                                                       };
  
  
  /**
   * 
   */
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
  
  /**
   * @return
   */
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
}
