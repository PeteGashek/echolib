package com.javasteam.amazon.echo;

import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;

public class MyTest {

  public MyTest() {
    // TODO Auto-generated constructor stub
  }
  
  public boolean handleCommand( EchoTodoItemImpl todoItem, EchoUserSession echoUserSession, String remainder, String[] commands ) {
    System.out.println( "Called with: " + todoItem.getText() );
    
    if( commands != null ) {
      for( String command : commands ) {
        System.out.println( "command: " + command );
      }
    }
    
    return true;
  }
  
  public static void parseIt( String theString ) {
    String classnameAndMethodString = theString.split( "[\\s;]" )[0];
    
    String[] classnameAndMethodArray = classnameAndMethodString.split( ":" );
        
    String theClassname  = classnameAndMethodArray[ 0 ];
    String theMethodname = (classnameAndMethodArray.length > 1) ? classnameAndMethodArray[ 1 ] : "handleEchoItem"; 
    
    System.out.println( "Parsed: " + theClassname + "->" + theMethodname );
  }

  public static void main( String[] args ) {
    parseIt( "com.junk.test.Blah:handleSomething:handleSomethingElse some other stuff" );
    parseIt( "com.junk.test.Blah:handleSomething some other stuff" );
    parseIt( "com.junk.test.Blah some other stuff" );
    parseIt( "" );
    //parseIt( null );

    /*
    EchoCommandHandlerBuilder builder = new EchoCommandHandlerBuilder();
    builder.setKey( "theKey" );
    builder.setTheClassname( "com.javasteam.amazon.echo.MyTest" );
    builder.setTheMethodName( "handleCommand" );
    builder.setCommandArray( new String[] { "one", "two" } );
    */
    
    EchoCommandHandlerBuilder builder = EchoCommandHandlerDefinitionPropertyParser.getCommandHandlerBuilder( "com.javasteam.amazon.echo.MyTest:handleCommand key=theKey,command=\"one two\" two" );
    
    EchoCommandHandler handler = builder.generate();

    EchoTodoItemImpl todoItem = new EchoTodoItemImpl();
    todoItem.setText( "This is a test todo item" );
    EchoUserSession echoUserSession = new EchoUserSession();
    String remainder = "extra stuff";
    
    boolean output = handler.handle( todoItem, echoUserSession, remainder );
    System.out.println( "returned: " + output );
    
    
  }
}
