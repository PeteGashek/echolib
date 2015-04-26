package com.javasteam.amazon.echo.activity.core;

public class SourceDeviceId {
  private String deviceType;
  private String serialNumber;

  public String getDeviceType() {
    return deviceType;
  }
  
  public void setDeviceType( String deviceType ) {
    this.deviceType = deviceType;
  }
  
  public String getSerialNumber() {
    return serialNumber;
  }
  
  public void setSerialNumber( String serialNumber ) {
    this.serialNumber = serialNumber;
  }
}
