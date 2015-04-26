package com.javasteam.amazon.echo.todo;

import com.javasteam.amazon.echo.EchoResponseItem;

public class EchoTodoResponseItem extends EchoResponseItem {
  
  public EchoTodoResponseItem( String keyword, String remainder, EchoTodoItem todoItem )  {
    super( keyword, remainder, todoItem );
  }

  @Override
  public String getText() {
    return ((EchoTodoItem) this.getEchoResponseObject() ).getText();
  }

}
