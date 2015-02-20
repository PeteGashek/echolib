package com.javasteam.amazon.echo.plugin.util;

public class ListenerPropertyParser {
    
  public static TodoItemRetrievedListenerBuilder getListenerBuilder( String command ) {
    TodoItemRetrievedListenerBuilder builder = new TodoItemRetrievedListenerBuilder();
    
    if( command != null ) {
      command = command.trim();
        
      if( command.length() > 0 ) {
        String theClassname = command.split( "[\\s;]" )[0];
        
        builder.setTheClassname( theClassname.trim() );
          
        String remainder    = command.substring( theClassname.length() ).trim();
          
        String[] args = remainder.split( ",(?=(([^'\"]*['\"]){2})*[^'\"]*$)" );
        
        if( args.length > 0 ) {
          for( int i = 0; i < args.length; ++i ) {
            args[ i ] = args[ i ].trim();
            if( args[ i ].toLowerCase().startsWith( "key=" )) {
              builder.setKey( args[ i ].substring( "key=".length() ).trim() );
            }
            else if( args[ i ].toLowerCase().startsWith( "command=" )) {
              String temp = args[ i ].substring( "command=".length() );
              String[] commandArray = temp.split( " (?=(([^'\"]*['\"]){2})*[^'\"]*$)" );
              if( commandArray != null && commandArray.length > 0 ) {
                for( int y = 0; y < commandArray.length; ++y ) {
                  //System.out.println( "item: " + y + " :: " + commandArray[ y ] );
                  commandArray[ y ] = commandArray[ y ].trim();
                } 
                builder.setCommandArray( commandArray );
              }
            }
          }
        }
      }
    }
    
    return builder;
  }
  
  private ListenerPropertyParser() {
  }

}
