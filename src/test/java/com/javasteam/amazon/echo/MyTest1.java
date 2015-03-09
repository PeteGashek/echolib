package com.javasteam.amazon.echo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.plugin.Evernote;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandler;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerBuilder;
import com.javasteam.amazon.echo.plugin.util.EchoCommandHandlerDefinitionPropertyParser;
import com.javasteam.http.DumpForms;
import com.javasteam.http.Form;
import com.javasteam.http.FormFieldMap;
import com.javasteam.http.HttpGetHelper;
import com.javasteam.http.User;
import com.javasteam.http.UserImpl;

public class MyTest1 {
  private final static Log          log = LogFactory.getLog( MyTest1.class.getName() );
  
  public MyTest1() {
  }
  
  public boolean handleCommand( final EchoTodoItemImpl todoItem, final EchoUserSession echoUserSession, final String remainder, final String[] commands ) {
    Preconditions.checkNotNull( todoItem );
    
    System.out.println( "Called with: " + todoItem.getText() );
    
    if( commands != null ) {
      for( String command : commands ) {
        System.out.println( "command: " + command );
      }
    }
    
    return true;
  }
  
  public static void parseIt( final String theString ) {
    Preconditions.checkNotNull( theString );
    
    String   classnameAndMethodString = theString.split( "[\\s;]" )[0];
    String[] classnameAndMethodArray  = classnameAndMethodString.split( ":" );
    String   theClassname             = classnameAndMethodArray[ 0 ];
    String   theMethodname            = (classnameAndMethodArray.length > 1) ? classnameAndMethodArray[ 1 ] : "handleEchoItem"; 
    
    System.out.println( "Parsed: " + theClassname + "->" + theMethodname );
  }
  
  public static void dumpForms( final String url ) {
    Preconditions.checkNotNull( url );
    
    com.javasteam.http.DumpForms dumpForms = new com.javasteam.http.DumpForms();
    
    dumpForms.dump( url );
  }

  public static void dumpElementById( final String url, final String id ) {
    Preconditions.checkNotNull( url );
    Preconditions.checkNotNull( id );
    
    com.javasteam.http.DumpForms dumpForms = new com.javasteam.http.DumpForms();
    
    dumpForms.dumpElementById( url, id );
  }
  
  public static void printFormFields( final Form form ) {
    Preconditions.checkNotNull( form );
    
    Map<String,String> fields = form.getFields();
    
    for( String fieldName : fields.keySet() ) {
      System.out.println( "Field '" + fieldName + "' == '" + fields.get( fieldName ) + "'" );
    }    
  }
  
  public static void printFormFieldsForFormByName( final String url, final String id ) {
    Preconditions.checkNotNull( url );
    Preconditions.checkNotNull( id );
    
    Form form = FormFieldMap.getHtmlFormFieldsByFormName( url, id, new UserImpl() );
    printFormFields( form );
  }

  public static void printFormFieldsForFormByAction( final String url, final String action ) {
    Preconditions.checkNotNull( url );
    Preconditions.checkNotNull( action );
    
    Form form = FormFieldMap.getHtmlFormFieldsByAction( url, action, new UserImpl() );
    printFormFields( form );
  }

  
  
  public static synchronized String twitterLogin( final User user ) throws ClientProtocolException, IOException {
    Preconditions.checkNotNull( user );
    
    Form     form     = FormFieldMap.getHtmlFormFieldsByAction( "https://twitter.com/login", "https://twitter.com/sessions", user );
    Document doc      = FormFieldMap.getDocumentByUrl( "https://twitter.com/login", user );
    Elements elements = doc.getElementsByAttributeValue( "name", "authenticity_token" );
    
    String authenticityToken = elements.get(0).attr( "value" );
    System.out.println( "authenticityToken: " + authenticityToken );
    
    if( !form.getAction().isEmpty() ) {
      form.getFields().put( "session[username_or_email]",    user.getUsername() );
      form.getFields().put( "session[password]",             user.getPassword() );
      form.getFields().put( "authenticity_token", authenticityToken );
      EchoBase base = new EchoBase();
      
       
      try {
        base.postForm( form, user );
      }
      catch( IOException e ) {
        log.error( "Failed!", e );
      }
      catch( AmazonLoginException e ) {
        log.error( "Failed!", e );
      }

      log.info( "Login successful" );
    }
    
    return authenticityToken;
  }
  
  public static void sendTwit( final EchoBase base, final String authenticityToken, final String twitText, final User user ) {
    Preconditions.checkNotNull( base );
    Preconditions.checkNotNull( authenticityToken );
    Preconditions.checkNotNull( twitText );
    Preconditions.checkNotNull( user );
    
    Form               form = new Form( "https://twitter.com/i/tweet/create" );
    Map<String,String> fields = new HashMap<String,String>();
    
    form.setFields( fields );
    
    fields.put( "status",             twitText );
    fields.put( "place_id",           "" );
    fields.put( "tagged_users",       "" );
    fields.put( "page_context",       "profile" );
    fields.put( "authenticity_token", authenticityToken );
    
    printFormFields( form );
    
    try {
      base.postForm( form, user );
    }
    catch( AmazonLoginException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public static void main_indexTest( final String[] args ) {
    System.out.println( HTML.Attribute.VALUE.toString() );
    String message = "This is a hash tag test";
    
    int pos = message.indexOf( "hash tag " );
    System.out.println( message.substring( 0, pos ) + "#" + message.substring( pos+9 ));
  }
  
  public static void main_twitterTest( final String[] args ) {
    BasicConfigurator.configure();
    EchoBase.setHttpClientPool( 1, 1 );
    
    LogManager.getRootLogger().setLevel( Level.DEBUG );
    EchoBase           base = new EchoBase();
    
    //dumpForms( "https://pitangui.amazon.com/" );
    //dumpForms( "https://twitter.com/login" );
    //dumpElementById( "ap_signin_form" );
    //printFormFieldsForFormByName( "https://pitangui.amazon.com/", "ap_signin_form" );
    //printFormFieldsForFormByAction( "https://twitter.com/login", "https://twitter.com/sessions" );
    User user = new UserImpl( "username", "password" );
    
    try {
      String authenticityToken = twitterLogin( user );
      HttpGetHelper getHelper = new HttpGetHelper( "https://twitter.com/i/jot" );
      getHelper.httpGet( EchoBase.getHttpClientPool(), user );
      sendTwit( base, authenticityToken, "This is a test twit_001", user );
    }
    catch( ClientProtocolException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  public static void main_parseTest( final String[] args ) {
    parseIt( "com.junk.test.Blah:handleSomething:handleSomethingElse some other stuff" );
    parseIt( "com.junk.test.Blah:handleSomething some other stuff" );
    parseIt( "com.junk.test.Blah some other stuff" );
    parseIt( "" );
    //parseIt( null );

    EchoCommandHandlerBuilder builder = EchoCommandHandlerDefinitionPropertyParser.getCommandHandlerBuilder( "com.javasteam.amazon.echo.MyTest:handleCommand key=theKey,command=\"one two\" two" );
    
    EchoCommandHandler handler = builder.generate();

    EchoTodoItemImpl todoItem = new EchoTodoItemImpl();
    todoItem.setText( "This is a test todo item" );
    EchoUserSession echoUserSession = new EchoUserSession();
    String remainder = "extra stuff";
    
    boolean output = handler.handle( todoItem, echoUserSession, remainder );
    System.out.println( "returned: " + output );
  }

  public static void main_evernote( final String[] args ) {
    EchoUserSession echoUserSession = new EchoUserSession();
    
    if( echoUserSession.loadProperties( "echo.properties" )) {
      String username = echoUserSession.getProperty( "evernote.username" );
      String password = echoUserSession.getProperty( "evernote.password" );
      User   user     = new UserImpl( username, password );
      
      DumpForms dumper = new DumpForms();
      dumper.getHtmlFormFieldsByGet( "https://www.evernote.com/Login.action?targetUrl=/Home.action", "login_form" );
      //dumpForms( "https://www.evernote.com/Login.action?targetUrl=/Home.action", "login_form" );
      
      Evernote evernote = new Evernote();
      EchoBase base     = new EchoBase();
      EchoBase.setHttpClientPool( 5, 2 );
      
      try {
        evernote.login( base, user );
      }
      catch( ClientProtocolException e ) {
        e.printStackTrace();
      }
      catch( IOException e ) {
        e.printStackTrace();
      }
    }
    
    
  }
  
  public static void main( final String[] args ) {
    //String script = "define(\"actionBean\", [], function() {return {\"hpts\":\"1425864349277\",\"hptsh\":\"5mae+ef3KQqDijo/XzjkJytFpt8=\",\"endpoints\":{\"noteStoreUrlForBusiness\":null,\"noteStoreUrlForBusinessAdmin\":null,\"utilityUrlForBusiness\":null,\"businessShardUtilityUrl\":null,\"userStoreUrlForBusiness\":null,\"noteStoreUrl\":null,\"userStoreUrl\":null,\"utilityUrl\":null,\"messageStoreUrl\":null},\"userPrivilege\":null};});";
    //System.out.println( "lscript:\n    " + script );
    main_evernote( args );
    /*
    Pattern pattern = Pattern.compile( ".*?(\"hpts\"):\"([^\"]*)\".*" );
    Matcher matcher = pattern.matcher( script );
    System.out.println( "Matches: " + matcher.matches() );
    System.out.println( matcher.group( 2 ) );
    */
    //while( matcher.find() ) {
    //  System.out.println( "Matches: " + matcher.matches() );
    //  System.out.println( matcher.group() );
    //}
  }
}
