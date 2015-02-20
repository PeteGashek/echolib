/**
 * 
 */
package com.javasteam.amazon.echo;

/**
 * @author damon
 *
 */
public class AmazonLoginException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 3222375154049092591L;

  /**
   * 
   */
  public AmazonLoginException() {
  }

  /**
   * @param message
   */
  public AmazonLoginException( String message ) {
    super( message );
  }

  /**
   * @param cause
   */
  public AmazonLoginException( Throwable cause ) {
    super( cause );
  }

  /**
   * @param message
   * @param cause
   */
  public AmazonLoginException( String message, Throwable cause ) {
    super( message, cause );
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public AmazonLoginException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
    super( message, cause, enableSuppression, writableStackTrace );
  }

}
