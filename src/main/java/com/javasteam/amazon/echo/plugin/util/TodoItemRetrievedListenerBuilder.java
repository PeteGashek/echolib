package com.javasteam.amazon.echo.plugin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TodoItemRetrievedListenerBuilder extends EchoListener {
  private final static Log log = LogFactory.getLog( TodoItemRetrievedListenerBuilder.class.getName() );
  
  public TodoItemRetrievedListenerBuilder() {
    super();
  }
  
  public TodoItemRetrievedListenerInterface generate() {
    TodoItemRetrievedListenerInterface retval = null;
    
    try {
      Class<?> theClass = Class.forName( this.getTheClassname() );
      retval  = (TodoItemRetrievedListenerInterface) theClass.newInstance();
      retval.setKey( this.getKey() );
      retval.setCommands( this.getCommandArray() );
    }
    catch( ClassNotFoundException e ) {
      log.error( this.getTheClassname() + " class must be in class path.", e );
    }
    catch( InstantiationException e ) {
     log.error( this.getTheClassname() + " class must be concrete.", e );
    }
    catch( IllegalAccessException e ) {
      log.error( this.getTheClassname() + " class must have a no-arg constructor.", e );
    }
    
    return retval;
  }
}
