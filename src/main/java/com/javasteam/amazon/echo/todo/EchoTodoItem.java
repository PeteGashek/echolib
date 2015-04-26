package com.javasteam.amazon.echo.todo;

import java.util.Calendar;

public interface EchoTodoItem {

  /**
   * @return
   */
  public abstract boolean isComplete();

  /**
   * @param complete
   */
  public abstract void setComplete( boolean complete );

  /**
   * @return
   */
  public abstract Calendar getCreatedDate();

  /**
   * @param createdDate
   */
  public abstract void setCreatedDate( Calendar createdDate );

  public abstract void setCreatedDateToNow();

  /**
   * @return
   */
  public abstract Calendar getLastLocalUpdateDate();

  /**
   * @param lastLocalUpdateDate
   */
  public abstract void setLastLocalUpdateDate( Calendar lastLocalUpdateDate );

  /**
   * @return
   */
  public abstract Calendar getLastUpdatedDate();

  /**
   * @param lastUpdatedDate
   */
  public abstract void setLastUpdatedDate( Calendar lastUpdatedDate );

  /**
   * @return
   */
  public abstract boolean isDeleted();

  /**
   * @param deleted
   */
  public abstract void setDeleted( boolean deleted );

  /**
   * @return
   */
  public abstract String[] getNbestItems();

  /**
   * @param nbestItems
   */
  public abstract void setNbestItems( String[] nbestItems );

  /**
   * @return
   */
  public abstract String getType();

  /**
   * @param type
   */
  public abstract void setType( String type );

  /**
   * @return
   */
  public abstract Integer getVersion();

  /**
   * @param version
   */
  public abstract void setVersion( Integer version );

  /**
   * @return
   */
  public abstract String getItemId();

  /**
   * @param itemId
   */
  public abstract void setItemId( String itemId );

  /**
   * @return
   */
  public abstract String getText();

  /**
   * @param text
   */
  public abstract void setText( String text );

  /**
   * @return
   */
  public abstract String getUtteranceId();

  /**
   * @param utteranceId
   */
  public abstract void setUtteranceId( String utteranceId );

}