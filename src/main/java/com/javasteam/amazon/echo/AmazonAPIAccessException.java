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
  public AmazonAPIAccessException( String message ) {
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
  public AmazonAPIAccessException( String message, Throwable cause ) {
    super( message, cause );
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public AmazonAPIAccessException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
    super( message, cause, enableSuppression, writableStackTrace );
  }

}
