package com.javasteam.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.javasteam.restful.HttpClientPool;

public class FormFieldMap {
  private final static Log          log                = LogFactory.getLog( FormFieldMap.class.getName() );
  
  public final static String         DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13";
  private static      HttpClientPool httpClientPool     = new HttpClientPool( 1, 1 );
  
  private FormFieldMap( String uri) {
  }

  private static CloseableHttpResponse execute( final HttpClientPool httpClientPool, final HttpGet httpGet, final HttpClientContext context ) throws ClientProtocolException, IOException {
    return httpClientPool.getHttpClient().execute( httpGet, context );
  }
  
  private static String parseResponse( final HttpResponse response ) throws HttpResponseException, IOException {
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
  
  public static String httpGet( final String url, final User user ) throws ClientProtocolException, IOException {
    String retval = null;
    
    HttpGet httpGet = new HttpGet( url );

    httpGet.setHeader( HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT );

    HttpResponse response = execute( httpClientPool, httpGet, user.getContext() );

    retval = parseResponse( response );

    user.logCookies();
    
    return retval;
  }
  
  private static Map<String, String>  mapAttributes( final Element element ) {
    Map<String, String> retval = new HashMap<String,String>();
    String              name   = element.attr( "name" );
    
    if( name != null && name.trim().length() > 0 ) {
      retval.put( name, element.attr( "value" ) );
    }
    
    return retval;
  }
  
  private static  Map<String, String> mapElement( final Element element ) {
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

  public static Document getDocumentByUrl( final String url, final User user ) throws ClientProtocolException, IOException {
    String   output  = httpGet( url, user );
    return Jsoup.parse( output );
  }
  
  public static Form getHtmlFormFieldsByFormName( final String url, final String formName, final User user ) {
    Form retval = new Form();
    
    retval.setFields( new HashMap<String,String>() );
    
    try {
      Document doc     = getDocumentByUrl( url, user );
      Element  element = doc.getElementById( formName );
      if( element != null ) {
        retval.setAction( element.attr( "action" ) );
        retval.getFields().putAll( mapElement( element ));
      }
      else {
        log.error( "Can't find requested form: " + formName );
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
    
    return retval;    
  }
    
  public static Form getHtmlFormFieldsByAction( final String url, final String action, final User user ) {
   Form retval = new Form();
    
    retval.setFields( new HashMap<String,String>() );
    
    try {
      Document doc     = getDocumentByUrl( url, user );
      Element  element = doc.getElementsByAttributeValueContaining( "action", action ).get( 0 );
      
      if( element != null ) {
        retval.setAction( element.attr( "action" ) );
        retval.getFields().putAll( mapElement( element ));
      }
      else {
        log.error( "Can't find requested form with action: " + action );
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
    
    return retval;    
  }

  public static Form getHtmlFormFieldsByType( final String url, final String type, final User user ) {
    Form retval = new Form();
     
     retval.setFields( new HashMap<String,String>() );
     
     try {
       Document doc     = getDocumentByUrl( url, user );
       Elements elements = doc.getElementsByClass( type );
       
       if( elements != null ) {
         for( Element element : elements ) {
           retval.getFields().putAll( mapElement( element ));
         }
       }
       else {
         log.error( "Can't find elements of type: " + type );
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
     
     return retval;    
   }
}
