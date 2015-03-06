/**
 * 
 */
package com.javasteam.amazon.echo;

/**
 * @author damon
 *
 */
public class AmazonAPIAccessException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 3753155001753691115L;

  /**
   * 
   */
  public AmazonAPIAccessException() {
  }

  /**
   * @param message
   */
  public AmazonAPIAccessException( final String message ) {
    super( message );
  }

  /**
   * @param cause
   */
  public AmazonAPIAccessException( Throwable cause ) {
    super( cause );
  }

  /**
   * @param message
   * @param cause
   */
  public AmazonAPIAccessException( final String message, final Throwable cause ) {
    super( message, cause );
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public AmazonAPIAccessException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace ) {
    super( message, cause, enableSuppression, writableStackTrace );
  }

}
