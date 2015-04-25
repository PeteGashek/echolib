package com.javasteam.util;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.EchoUserSession;

/**
 * 
 */

/**
 * @author damon
 *
 */
public class PropertyFileConfigurator extends Properties implements Configurator {
  private static final Log  log              = LogFactory.getLog( PropertyFileConfigurator.class.getName() );
  private static final long serialVersionUID = 902958559742811139L;
  
  private Hashtable<String, String> modifiedVals = new Hashtable<String, String>();

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
    String retval = null;
    
    if( this.modifiedVals.containsKey( key )) {
      retval = this.modifiedVals.get( key );
    }
    else {
      retval = super.getProperty( key );
    }
    
    return retval;
  }
  
  public void set( String key, String value ) {
    if( this.modifiedVals.containsKey( key )) {
      this.modifiedVals.remove( key );
    }
    
    this.modifiedVals.put( key, value );
  }

}
