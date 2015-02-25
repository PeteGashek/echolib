/**
 * 
 */
package com.javasteam.amazon.echo;

import junit.framework.TestCase;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @author ddamon
 *
 */
public class EchoUserTest extends TestCase {

  /**
   * 
   */
  public EchoUserTest() {
  }

  /**
   * @param name
   */
  public EchoUserTest( String name ) {
    super( name );
  }

  /**
   * 
   */
  public void testUser() throws Exception {
    EchoUser user = new EchoUser( "bob", "syouruncle" );
    
    assertEquals( "bob",        user.getUsername() );
    assertEquals( "syouruncle", user.getPassword() );
    
    user.setUser( "BOB" );
    assertEquals( "BOB",        user.getUsername() );

    user.setPassword( "SYOURUNCLE" );
    assertEquals( "SYOURUNCLE",        user.getPassword() );
    
    BasicCookieStore cookieStore = new BasicCookieStore();
    user.setCookieStore( cookieStore );
    assertTrue( cookieStore == user.getCookieStore() );
    
    // test getting the cookie store from the context
    assertTrue( cookieStore == user.getContext().getCookieStore() );
  }
}
