/**
 * 
 */
package com.javasteam.amazon.echo;

import junit.framework.TestCase;

/**
 * @author ddamon
 *
 */
public class EchoTodoItemBaseTest extends TestCase {

  /**
   * 
   */
  public EchoTodoItemBaseTest() {
  }

  /**
   * @param name
   */
  public EchoTodoItemBaseTest( String name ) {
    super( name );
  }

  /**
   * 
   */
  public void testEchoTodoItemBase() throws Exception {
    // test constructor with text passed in as argument
    EchoTodoItemImpl todoItem = new EchoTodoItemImpl( "Test todo item" );
    
    assertEquals( "Test todo item", todoItem.getText() );
    assertEquals( null,             todoItem.getItemId() );
    assertEquals( null,             todoItem.getType() );
    assertEquals( null,             todoItem.getUtteranceId() );
    assertEquals( null,             todoItem.getCreatedDate() );
    assertEquals( null,             todoItem.getLastLocalUpdateDate() );
    assertEquals( null,             todoItem.getLastUpdatedDate() );
    assertEquals( null,             todoItem.getNbestItems() );
    assertEquals( null,             todoItem.getVersion() );
    assertFalse( todoItem.isDeleted() );
    assertFalse( todoItem.isComplete() );
    
    // constructor with itemId and text parameters
    todoItem = new EchoTodoItemImpl( "ITEMID", "Test todo item" );
    
    assertEquals( "Test todo item", todoItem.getText() );
    assertEquals( "ITEMID",         todoItem.getItemId() );
    assertEquals( null,             todoItem.getType() );
    assertEquals( null,             todoItem.getUtteranceId() );
    assertEquals( null,             todoItem.getCreatedDate() );
    assertEquals( null,             todoItem.getLastLocalUpdateDate() );
    assertEquals( null,             todoItem.getLastUpdatedDate() );
    assertEquals( null,             todoItem.getNbestItems() );
    assertEquals( null,             todoItem.getVersion() );
    assertFalse( todoItem.isDeleted() );
    assertFalse( todoItem.isComplete() );
    
    todoItem.setText( "updated text" );
    assertEquals( "updated text", todoItem.getText() );
    
    todoItem.setItemId( "NEW ITEM ID" );
    assertEquals( "NEW ITEM ID", todoItem.getItemId() );
    
    todoItem.setType( "TASK" );
    assertEquals( "TASK", todoItem.getType() );

  }
}
