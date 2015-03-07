package com.javasteam.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Form {
  private String             action = null;
  private Map<String,String> fields = null;
  
  public Form() {
    
  }
  
  public Form( final String action ) {
    this.action = action;
  }
  
  public String getAction() {
    return action;
  }

  public void setAction( final String action ) {
    this.action = action;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public void setFields( final Map<String, String> fields ) {
    this.fields = fields;
  }

  public List<NameValuePair> generateFormData() {
    List<NameValuePair> formData = new ArrayList<NameValuePair>();
    
    for( String key : fields.keySet() ) {
      formData.add( new BasicNameValuePair( key, fields.get( key )));
    }
   
    return formData;
  }
}
