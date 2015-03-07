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
            String trimmedArg = args[ i ].trim();
            
            builder.checkStringForKeyOption( trimmedArg );
            builder.checkStringForCommandOption( trimmedArg );
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
