package com.javasteam.restful;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author ddamon
 *
 */
public final class HttpClientPool {
  //private final static Log log = LogFactory.getLog( HttpClientPool.class.getName() );
  
  private final CloseableHttpClient                clientPool;
  private final IdleConnectionMonitor              monitor;
  private final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
  
  private int totalConnections;
  private int maxConnectionsPerRoute;
  
  
  /**
   * @param totalConnections
   * @param maxConnectionsPerRoute
   */
  public HttpClientPool( int totalConnections, int maxConnectionsPerRoute ) {
    this.totalConnections       = totalConnections;
    this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    
    connectionManager.setMaxTotal( totalConnections );
    connectionManager.setDefaultMaxPerRoute( maxConnectionsPerRoute );
    
    clientPool = HttpClients.custom().setConnectionManager( connectionManager ).build();
    monitor    = new IdleConnectionMonitor( connectionManager );
    
    monitor.setDaemon( true );
    monitor.start();
  }
  
 
  /**
   * @return
   */
  public int getTotalConnections() {
    return totalConnections;
  }

  /**
   * @return
   */
  public int getMaxConnectionsPerRoute() {
    return maxConnectionsPerRoute;
  }


  /**
   * @return
   */
  public CloseableHttpClient getHttpClient() {
    return clientPool;
  }
   
  /**
   * @return
   */
  public IdleConnectionMonitor getMonitor() {
    return monitor;
  }
  
  /**
   * @throws InterruptedException
   * @throws IOException
   */
  public void shutdown() throws InterruptedException, IOException {
    // Shutdown the monitor.
    getMonitor().shutdown();
  }
}
