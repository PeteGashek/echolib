package com.javasteam.amazon.echo.plugin.util;

/**
 * @author ddamon
 *
 */
public class EchoCommandHandlerDefinitionPropertyParser {
    
  /**
   * @param command
   * @return
   */
  public static EchoCommandHandlerBuilder getCommandHandlerBuilder( final String command ) {
    EchoCommandHandlerBuilder builder = new EchoCommandHandlerBuilder();
    String commandTrimmed = command.trim();
    
    if( command != null ) {
      commandTrimmed = command.trim();
        
      if( commandTrimmed.length() > 0 ) {
        String   classnameAndMethodString = commandTrimmed.split( "[\\s;]" )[0];
        String[] classnameAndMethodArray  = classnameAndMethodString.split( ":" );
        String   theClassname             = classnameAndMethodArray[ 0 ];
        String   theMethodName            = (classnameAndMethodArray.length > 1) ? classnameAndMethodArray[ 1 ]
                                                                                 : "handleEchoItem"; 
        
        builder.setTheClassname( theClassname.trim() );
        builder.setTheMethodName( theMethodName.trim() );
          
        String   remainder = commandTrimmed.substring( classnameAndMethodString.length() ).trim();
        String[] args      = remainder.split( ",(?=(([^'\"]*['\"]){2})*[^'\"]*$)" );
        
        if( args.length > 0 ) {
          for( int i = 0; i < args.length; ++i ) {
            args[ i ] = args[ i ].trim();
            
            if( args[ i ].toLowerCase().startsWith( "key=" )) {
              builder.setKey( args[ i ].substring( "key=".length() ).trim() );
            }
            else if( args[ i ].toLowerCase().startsWith( "command=" )) {
              String   temp         = args[ i ].substring( "command=".length() );
              String[] commandArray = temp.split( " (?=(([^'\"]*['\"]){2})*[^'\"]*$)" );
              
              if( commandArray != null && commandArray.length > 0 ) {
                for( int y = 0; y < commandArray.length; ++y ) {
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
  
  /**
   * 
   */
  private EchoCommandHandlerDefinitionPropertyParser() {
  }

}
