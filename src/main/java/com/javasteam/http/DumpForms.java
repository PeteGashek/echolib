package com.javasteam.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.javasteam.restful.HttpClientPool;

public class DumpForms {
  //private final static Log          log = LogFactory.getLog( DumpForms.class.getName() );
  
  public final static String        DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13";
  
  private static HttpClientPool     httpClientPool     = new HttpClientPool( 1, 1 );
  
  public DumpForms() {
  }
  
  public CloseableHttpResponse execute( HttpClientPool httpClientPool, HttpGet httpGet, HttpClientContext context ) throws ClientProtocolException, IOException {
    return httpClientPool.getHttpClient().execute( httpGet, context );
  }
  
  public String parseResponse( HttpResponse response ) throws HttpResponseException, IOException {
    String     retval = "";
    StatusLine status = response.getStatusLine();
    int        code   = status.getStatusCode();

    if( code == HttpStatus.SC_OK ) {
      retval = new BasicResponseHandler().handleResponse( response );
      
      HttpEntity entity = response.getEntity();

      if( entity != null ) {
        EntityUtils.consume( entity );
      }
    }
    
    return retval;
  }
  
  public String httpGet( String url, User user ) throws ClientProtocolException, IOException {
    String retval = null;
    
    HttpGet httpGet = new HttpGet( url );

    httpGet.setHeader( HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT );

    HttpResponse response = execute( httpClientPool, httpGet, user.getContext() );

    retval = parseResponse( response );

    user.logCookies();
  
    return retval;
  }
  
  private void printAttributes( Element element, String lineIndent ) {
    for( Attribute attribute : element.attributes() ) {
      System.out.println( lineIndent + "Attribute: " + attribute.getKey() + " :: " + attribute.getValue() );
    }
  }
  
  private void printElement( Element element, String lineIndent ) {
    System.out.println( lineIndent + "Element: " + element.id() + " :: " + element.nodeName() );
    printAttributes( element, lineIndent + " -" );
    
    for( Element innerElement : element.getAllElements() ) {
      if( innerElement != element ) {
        printElement( innerElement, lineIndent + "    " );
      }
    }
  }
  
  public void dumpElementById( String url, String id ) {
   User user = new UserImpl();
    
    try {
      String   output = httpGet( url, user );
      Document doc    = Jsoup.parse( output );
      Element  element = doc.getElementById( id );
      printElement( element, "" );
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
  
  public void dump( String url ) {
    User user = new UserImpl();
    
    try {
      String   output = httpGet( url, user );
      Document doc    = Jsoup.parse( output );
      Elements forms  = doc.getElementsByTag( "form" );
      
      for( Element element : forms ) {
        printElement( element, "" );
      }

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
  
  private Map<String, String>  mapAttributes( Element element ) {
    Map<String, String> retval = new HashMap<String,String>();
    String              name   = element.attr( "name" );
    
    if( name != null && name.trim().length() > 0 ) {
      retval.put( name, element.attr( "value" ) );
    }
    
    return retval;
  }
  
  private Map<String, String> mapElement( Element element ) {
    Map<String, String> retval = new HashMap<String,String>();
    
    if( element.nodeName().equalsIgnoreCase( "input" )) {
      retval.putAll( mapAttributes( element ));
    }
    else {
      for( Element innerElement : element.getAllElements() ) {
        if( innerElement != element ) {
          retval.putAll( mapElement( innerElement ));
        }
      }
    }
    return retval;
  }

  
  public Map<String, String> getHtmlFormFieldsByGet( String url, String formName ) {
    Map<String,String> retval = null;
    User               user   = new UserImpl();
    
    try {
      String   output  = httpGet( url, user );
      Document doc     = Jsoup.parse( output );
      Element  element = doc.getElementById( formName );
      retval = mapElement( element );
    }
    catch( ClientProtocolException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return retval;
  }

}
