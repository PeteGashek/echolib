package com.javasteam.amazon.echo;

/**
 * @author ddamon
 *
 */
public class TodoResponse {
  private EchoTodoItem[] values;

  /**
   * @return the values
   */
  public EchoTodoItem[] getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues( EchoTodoItem[] values ) {
    this.values = values;
  }
}
