package com.javasteam.amazon.echo;

import com.javasteam.amazon.echo.object.EchoTodoItem;

public class EchoTodoResponseItem extends EchoResponseItem {
  
  public EchoTodoResponseItem( String keyword, String remainder, EchoTodoItem todoItem )  {
    super( keyword, remainder, todoItem );
  }

  @Override
  public String getText() {
    // TODO Auto-generated method stub
    return ((EchoTodoItem) this.getEchoResponseObject() ).getText();
  }

}
