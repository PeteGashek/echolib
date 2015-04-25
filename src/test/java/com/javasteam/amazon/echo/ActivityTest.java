package com.javasteam.amazon.echo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasteam.amazon.echo.object.EchoActivityItem;
import com.javasteam.amazon.echo.object.EchoActivityItemImpl;

public class ActivityTest {
  private static final ObjectMapper mapper                     = new ObjectMapper();
//  /private static final Log          log                        = LogFactory.getLog( ActivityTest.class.getName() );
  
  private String testData = "{\"activities\":[{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339383970,\"description\":\"{\\\"summary\\\":\\\"alexa search somo\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\\\"}\",\"domainAttributes\":\"{\\\"disambiguated\\\":false,\\\"nBestList\\\":[{\\\"songName\\\":\\\"Ride [Explicit]\\\",\\\"entryType\\\":\\\"FindMusic\\\",\\\"mediaOwnerCustomerId\\\":\\\"A3JJZQV8OKZJOE\\\",\\\"playedMediaArtistName\\\":\\\"SoMo\\\",\\\"playQueuePrime\\\":false,\\\"playedMediaSongName\\\":\\\"Ride [Explicit]\\\",\\\"artistId\\\":\\\"303936095\\\",\\\"serviceName\\\":\\\"DIGITAL_MUSIC_STORE\\\",\\\"trackReferenceId\\\":\\\"c023ea3a-0b3c-416a-8e4a-9b4e016ee71d:1\\\",\\\"playQueueId\\\":\\\"c023ea3a-0b3c-416a-8e4a-9b4e016ee71d\\\",\\\"playedMediaAlbumName\\\":\\\"SoMo [Explicit] [+digital booklet]\\\",\\\"completionCode\\\":\\\"SUCCESS\\\",\\\"artistName\\\":\\\"SoMo\\\",\\\"artistAsin\\\":\\\"B009FOJB0M\\\"}]}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339383970\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339377034,\"description\":\"{\\\"summary\\\":\\\"play somo\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/1\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\\\"}\",\"domainAttributes\":\"{\\\"disambiguated\\\":false,\\\"nBestList\\\":[{\\\"entryType\\\":\\\"PlayStation\\\",\\\"mediaOwnerCustomerId\\\":\\\"A3JJZQV8OKZJOE\\\",\\\"playQueueId\\\":\\\"b6c85a94-216f-4d2b-ad60-3091766a4699\\\",\\\"artistName\\\":\\\"SoMo\\\",\\\"stationName\\\":\\\"SoMo\\\",\\\"serviceName\\\":\\\"I_HEART_RADIO\\\",\\\"trackReferenceId\\\":\\\"b6c85a94-216f-4d2b-ad60-3091766a4699:1\\\"}]}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339377034\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339374671,\"description\":\"{\\\"summary\\\":\\\"alexa\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\\\"}\",\"domainAttributes\":null,\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339374671\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425231175524,\"description\":\"{\\\"summary\\\":\\\"alexa to do\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV\\\"}\",\"domainAttributes\":\"{\\\"toDoId\\\":\\\"51ed3ec5-9508-34ab-82f7-563e4fc8c37b\\\",\\\"customerId\\\":\\\"A3JJZQV8OKZJOE\\\"}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425231175524\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV\",\"version\":2}],\"endDate\":1425339383970,\"startDate\":1425231175524}";
  //private String testData = "{\"activities\":[{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339383970,\"description\":\"{\\\"summary\\\":\\\"alexa search somo\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\\\"}\",\"domainAttributes\":\"{\\\"disambiguated\\\":false,\\\"nBestList\\\":[]}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339383970\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339377034,\"description\":\"{\\\"summary\\\":\\\"play somo\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/1\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\\\"}\",\"domainAttributes\":\"{\\\"disambiguated\\\":false,\\\"nBestList\\\":[{\\\"entryType\\\":\\\"PlayStation\\\",\\\"mediaOwnerCustomerId\\\":\\\"A3JJZQV8OKZJOE\\\",\\\"playQueueId\\\":\\\"b6c85a94-216f-4d2b-ad60-3091766a4699\\\",\\\"artistName\\\":\\\"SoMo\\\",\\\"stationName\\\":\\\"SoMo\\\",\\\"serviceName\\\":\\\"I_HEART_RADIO\\\",\\\"trackReferenceId\\\":\\\"b6c85a94-216f-4d2b-ad60-3091766a4699:1\\\"}]}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339377034\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339374671,\"description\":\"{\\\"summary\\\":\\\"alexa\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\\\"}\",\"domainAttributes\":null,\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425339374671\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\",\"version\":1},{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425231175524,\"description\":\"{\\\"summary\\\":\\\"alexa to do\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV\\\"}\",\"domainAttributes\":\"{\\\"toDoId\\\":\\\"51ed3ec5-9508-34ab-82f7-563e4fc8c37b\\\",\\\"customerId\\\":\\\"A3JJZQV8OKZJOE\\\"}\",\"domainType\":null,\"feedbackAttributes\":null,\"id\":\"A3JJZQV8OKZJOE#1425231175524\",\"intentType\":null,\"registeredCustomerId\":\"A3JJZQV8OKZJOE\",\"sourceActiveUsers\":null,\"sourceDeviceIds\":[{\"deviceType\":\"AB72C64C86AW2\",\"serialNumber\":\"B0F007125026018C\"}],\"utteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV\",\"version\":2}],\"endDate\":1425339383970,\"startDate\":1425231175524}";
  private String itemData = "{\"_disambiguationId\":null,\"activityStatus\":\"SUCCESS\",\"creationTimestamp\":1425339383970,\"description\":\"{\\\"summary\\\":\\\"alexa search somo\\\",\\\"firstUtteranceId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV/0\\\",\\\"firstStreamId\\\":\\\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\\\"}\"}";


  public ActivityTest() {
    
  }
  
  private void run() throws JsonParseException, JsonMappingException, IOException {
    ActivityResponse items = mapper.readValue( testData, ActivityResponse.class );
    System.out.println( "Items: " + items.getEndDate() );
    System.out.println( "ItemDevice: " + items.getActivities()[0].getSourceDeviceIds()[ 0 ].getDeviceType());
    System.out.println( "DomainAttributes: " + items.getActivities()[0].getDomainAttributes() );
    
    EchoActivityItem item = mapper.readValue( itemData, EchoActivityItemImpl.class );
    System.out.println( "Item: " + item.getActivityDescription().getSummary() );
    item.getDomainType();
    

    
    
    //for( EchoActivityItemImpl activity : items.getActivities() ) {
    //  System.out.println( activity.getText() );
    //}
  }

  public static void main( String[] args ) {
    ActivityTest test = new ActivityTest();
    try {
      test.run();
    }
    catch( JsonParseException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( JsonMappingException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
