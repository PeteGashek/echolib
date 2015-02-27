package com.javasteam.amazon.echo;

/**
 * @author ddamon
 *
 */
public class TodoResponse {
  private EchoTodoItemImpl[] values;

  /**
   * @return the values
   */
  public EchoTodoItemImpl[] getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues( EchoTodoItemImpl[] values ) {
    this.values = values;
  }
}
