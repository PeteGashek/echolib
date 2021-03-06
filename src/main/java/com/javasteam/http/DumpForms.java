package com.javasteam.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
  
  public CloseableHttpResponse execute( final HttpClientPool httpClientPool, final HttpGet httpGet, final HttpClientContext context ) throws ClientProtocolException, IOException {
    return httpClientPool.getHttpClient().execute( httpGet, context );
  }
  
  public String parseResponse( final HttpResponse response ) throws HttpResponseException, IOException {
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
  
  public String httpGet( final String url, final User user ) throws ClientProtocolException, IOException {
    String retval = null;
    
    HttpGet httpGet = new HttpGet( url );

    httpGet.setHeader( HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT );

    HttpResponse response = execute( httpClientPool, httpGet, user.getContext() );

    retval = parseResponse( response );

    user.logCookies();
  
    return retval;
  }
  
  private void printAttributes( final Element element, final String lineIndent ) {
    for( Attribute attribute : element.attributes() ) {
      System.out.println( lineIndent + "Attribute: " + attribute.getKey() + " :: " + attribute.getValue() );
    }
  }
  
  private void printElement( final Element element, final String lineIndent ) {
    System.out.println( lineIndent + "Element: " + element.id() + " :: " + element.nodeName() );
    printAttributes( element, lineIndent + " -" );
    
    for( Element innerElement : element.getAllElements() ) {
      if( innerElement != element ) {
        printElement( innerElement, lineIndent + "    " );
      }
    }
  }
  
  public void dumpElementById( final String url, final String id ) {
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
  
  public void dump( final String url ) {
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
  
  private Map<String, String>  mapAttributes( final Element element ) {
    Map<String, String> retval = new HashMap<String,String>();
    String              name   = element.attr( "name" );
    
    if( StringUtils.isNotBlank( name  )) {
      retval.put( name, element.attr( "value" ) );
    }
    
    return retval;
  }
  
  private Map<String, String> mapElement( final Element element, Map<String, String> map ) {
    
    
    if( element.getAllElements() == null ) {
      if( element.nodeName().equalsIgnoreCase( "input" )) {
        map.putAll( mapAttributes( element ));
      }
    }
    else {
      for( Element innerElement : element.getAllElements() ) {
        if( innerElement != element ) {
          mapElement( innerElement, map );
        }
      }
    }
    
    /*
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
    */
    
    return map;
  }

  
  public Map<String, String> getHtmlFormFieldsByGet( final String url, final String formName ) {
    Map<String,String> retval = null;
    User               user   = new UserImpl();
    
    try {
      String   output  = httpGet( url, user );
      Document doc     = Jsoup.parse( output );
      Element  element = doc.getElementById( formName );
      retval = mapElement( element, new HashMap<String,String>() );
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
