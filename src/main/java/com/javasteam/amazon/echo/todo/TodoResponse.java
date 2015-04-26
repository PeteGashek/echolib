package com.javasteam.amazon.echo.todo;


/**
 * @author ddamon
 *
 */
public class TodoResponse {
  private EchoTodoItemRetrieved[] values;

  /**
   * @return the values
   */
  public EchoTodoItemRetrieved[] getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues( final EchoTodoItemRetrieved[] values ) {
    this.values = values;
  }
}
