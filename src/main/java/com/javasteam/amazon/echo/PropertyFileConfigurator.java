package com.javasteam.amazon.echo;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;

/**
 * 
 */

/**
 * @author damon
 *
 */
public class PropertyFileConfigurator extends Properties implements Configurator {
  private final static Log log = LogFactory.getLog( PropertyFileConfigurator.class.getName() );
  
  /**
   * 
   */
  private static final long serialVersionUID = 902958559742811139L;

  /**
   * 
   */
  public PropertyFileConfigurator( String filename ) {
    loadProperties( filename );
  }

  /**
   * @param defaults
   */
  public PropertyFileConfigurator( Properties defaults ) {
    super( defaults );
    // TODO Auto-generated constructor stub
  }

  public boolean loadProperties( final String filename ) {
    boolean retval = false;
    
    Preconditions.checkNotNull( filename );
    
    try {
      load( EchoUserSession.class.getClassLoader().getResourceAsStream( filename ));
      retval = true;
    } 
    catch( Throwable e ) {
      log.fatal( "No Properties file: " + filename );
    }
    
    return retval;
  }
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.Configurator#getConfigurationProperty(java.lang.String)
   */
  public String get( String key ) {
    return super.getProperty( key );
  }

}
