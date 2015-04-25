package com.javasteam.util;

public interface Configurator {
  public String get( String key );
  public void set( String key, String value );
}
