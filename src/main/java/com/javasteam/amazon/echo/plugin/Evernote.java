package com.javasteam.amazon.echo.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;

import com.javasteam.amazon.echo.AmazonLoginException;
import com.javasteam.amazon.echo.EchoBase;
import com.javasteam.http.Form;
import com.javasteam.http.FormFieldMap;
import com.javasteam.http.User;

public class Evernote {
  private final static Log log            = LogFactory.getLog( Twitter.class
                                              .getName() );

  public static String     EVERNOTE_BASE  = "https://www.evernote.com";
  public static String     LOGIN_PAGE     = EVERNOTE_BASE
                                              + "/Login.action?targetUrl=/Home.action";
  public static String     LOGIN_FORM     = "login_form";
  public static String     USER_FIELD     = "username";
  public static String     PASSWORD_FIELD = "password";

  public Evernote() {
  }

  // not logging in yet.....
  public synchronized String login( final EchoBase base, final User user )
      throws ClientProtocolException, IOException {
    String retval = null;

    Document document = FormFieldMap.getDocumentByUrl( Evernote.LOGIN_PAGE,
        user );
    Form form = FormFieldMap.getHtmlFormFieldsByFormName( document,
        Evernote.LOGIN_FORM );

    ArrayList<String> scriptElements = FormFieldMap
        .getScriptElements( document );

    for( String script : scriptElements ) {
      if( script.contains( "\"actionBean\"" ) ) {
        System.out.println( "Script: " + script );

        Pattern pattern = Pattern.compile( ".*?\"(hpts)\":\"([^\"]*)\".*",
            Pattern.DOTALL );
        Matcher matcher = pattern.matcher( script );

        if( matcher.matches() ) {
          form.getFields().put( matcher.group( 1 ), matcher.group( 2 ) );
          System.out.println( "SET: " + matcher.group( 1 ) + " = "
              + matcher.group( 2 ) );
        }
        else {
          System.out.println( "match failed for script " + script );
        }

        pattern = Pattern.compile( ".*?\"(hptsh)\":\"([^\"]*)\".*",
            Pattern.DOTALL );
        matcher = pattern.matcher( script );
        if( matcher.matches() ) {
          form.getFields().put( matcher.group( 1 ), matcher.group( 2 ) );
          System.out.println( "SET: " + matcher.group( 1 ) + " = "
              + matcher.group( 2 ) );
        }
        else {
          System.out.println( "match failed for script " + script );
        }
        break;
      }
    }

    if( !form.getAction().isEmpty() ) {
      form.getFields().put( USER_FIELD, user.getUsername() );
      form.getFields().put( PASSWORD_FIELD, user.getPassword() );
      form.getFields().put( "login", "true" );
      form.getFields().put( "Login", "Sign in" );

      form.setAction( EVERNOTE_BASE + form.getAction() );
      System.out.println( "LoginAction: " + form.getAction() );
      for( String key : form.getFields().keySet() ) {
        System.out.println( "Field: " + key + " : "
            + form.getFields().get( key ) );
      }

      try {
        base.postForm( form, user );
        log.info( "Evernote login successful" );
        String actionUrl = EVERNOTE_BASE + "/Home.Action?__fp="
            + form.getFields().get( "__fp" ) + "&username="
            + user.getUsername() + "&login=true&_sourcePage="
            + form.getFields().get( "_sourcePage" );
        System.out.println( "actionUrl = " + actionUrl );
        System.out.println( base.httpGet( actionUrl, user ) );
        //user.logCookies();
      }
      catch( IOException e ) {
        log.error( "Evernote login Failed!", e );
      }
      catch( AmazonLoginException e ) {
        log.error( "Evernote login Failed!", e );
      }
    }

    return retval;
  }
}
