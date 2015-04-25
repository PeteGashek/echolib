package com.javasteam.amazon.echo.object;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.javasteam.amazon.echo.activity.ActivityDescription;
import com.javasteam.amazon.echo.activity.SourceDeviceId;


/**
 * @author ddamon
 *
 */
//curl 'https://pitangui.amazon.com/api/activities?startTime=1426849096030&endTime=1429458542471&size=50&offset=-1&_=1429751485214' -H 'Host: pitangui.amazon.com' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'DNT: 1' -H 'Referer: http://echo.amazon.com/' -H 'Origin: http://echo.amazon.com' -H 'Cookie: session-id-time=2082787201l; ubid-main=180-5233141-3792217; x-main=eQ7jAC6NSYy2FpSDzKYCmhxpNFPwk1dq; at-main=5|JrQd1reFnkDGUiEjpeRMSuwcbr7rpCDMJbAhcURfhwcCleKtIoYm4OwqpNz0YYOgW3QlvYcIaE4F8X+ng6KgMkRLR0cyXQa3ZRezgyJ4tYYVaqRZfNFWoe8ZqklCpOCExOC8xGs6lFlRaHB68tljJxTykj+i+5p+thB6LgyC4QalWfXFvu6TN8Oye1+yUSKZv4B+VH74n6cVTYiRukhke0rx1OFoYiAl09W0rOGVRllNYcNs+C3xDgiK9/bO3kp/dJSbnouTSicfs5MEyz0KHI7Whr0fONQY; x-wl-uid=1AS8Sorw1RtdqmCum97iAULUjQibjq9p1QMlDfTBsLfXJ3uTcAP5bCCP9FRV4N3vD4afl0N+be0R/9yepiy1MyA==; lc-main=en_US; dmusic_jsEnabled=1; s_fid=3AF93B9A539C281C-1FA8AC5D481F6B5D; s_dslv=1395519903728; aws-ubid-main=180-5310168-0627539; __utma=194891197.241837367.1388248068.1401839095.1423706380.8; __utmv=194891197.%22sc2oPFUgtITtnRuA0qomcny%40It7R10i%3F%22; regStatus=registering; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A389663172085%3Aroot%22%2C%22alias%22%3A%22%22%2C%22username%22%3A%22David%2520W%2520Damon%22%2C%22keybase%22%3A%22%22%2C%22issuer%22%3A%22https%3A%2F%2Fwww.amazon.com%2Fap%2Fsignin%22%7D; aws-session-id=179-6698741-3034320; aws-session-id-time=2032559105l; csrf=601350944; session-id=183-4101810-3819961; session-token=lLeBAmx4QVtrDN30dGBsnOlGLUoF70rsvfWGo++U99ExYZ+2RuRra07IzoVnCptZxsoTZ3cUKZsWwy5GyLB3kB2mH2sNrjRb/U3k13jY4//AOlZItdHC9Io9wwFzvlhl9u/+pJ/EEuPujScpkh/37O+dKxNEB6fp233c+J7QYxhjbLnwXwT/zTKR1z7V0TwWFrwz3Tj1gPSq8FRKe7vKaX2VHQTQLzLAA4VG4tNk/3Bt0Wh62H2ny6kSeHh2hMaGiWw2OdEjEFSYFdNDLBwy9/RQX5Rrksb6; __utmz=194891197.1423706380.8.1.utmccn=(organic)|utmcsr=google|utmctr=|utmcmd=organic; s_pers=%20s_vnum%3D1426299204534%2526vn%253D1%7C1426299204534%3B%20s_invisit%3Dtrue%7C1423709101813%3B%20s_nr%3D1423707301814-New%7C1431483301814%3B; skin=noskin; sess-at-main="sZKK5s9ToSJuuDhssooCnggB1LFGRiS1xmpakrm63Nk="' -H 'Connection: keep-alive'
/*
 * 
 * {"activities":[{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339383970,"description":"{\"summary\":\"alexa search somo\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"songName\":\"Ride [Explicit]\",\"entryType\":\"FindMusic\",\"mediaOwnerCustomerId\":\"A3JJZQV8OKZJOE\",\"playedMediaArtistName\":\"SoMo\",\"playQueuePrime\":false,\"playedMediaSongName\":\"Ride [Explicit]\",\"artistId\":\"303936095\",\"serviceName\":\"DIGITAL_MUSIC_STORE\",\"trackReferenceId\":\"c023ea3a-0b3c-416a-8e4a-9b4e016ee71d:1\",\"playQueueId\":\"c023ea3a-0b3c-416a-8e4a-9b4e016ee71d\",\"playedMediaAlbumName\":\"SoMo [Explicit] [+digital booklet]\",\"completionCode\":\"SUCCESS\",\"artistName\":\"SoMo\",\"artistAsin\":\"B009FOJB0M\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339383970","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:22::TNIH_2V.f78d2d75-3121-42b6-b0ee-3751258e6a87ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339377034,"description":"{\"summary\":\"play somo\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"entryType\":\"PlayStation\",\"mediaOwnerCustomerId\":\"A3JJZQV8OKZJOE\",\"playQueueId\":\"b6c85a94-216f-4d2b-ad60-3091766a4699\",\"artistName\":\"SoMo\",\"stationName\":\"SoMo\",\"serviceName\":\"I_HEART_RADIO\",\"trackReferenceId\":\"b6c85a94-216f-4d2b-ad60-3091766a4699:1\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339377034","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339374671,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339374671","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/36:13::TNIH_2V.af0c2ca5-fa11-4acf-a284-b6a1179ae140ZXV","version":1},{"_disambiguationId":null,"activityStatus":"DISCARDED_NON_DEVICE_DIRECTED_INTENT","creationTimestamp":1425339353946,"description":"{\"summary\":\"oh alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:52::TNIH_2V.aa989ac5-1a87-49d3-b958-054826432ad7ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:52::TNIH_2V.aa989ac5-1a87-49d3-b958-054826432ad7ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339353946","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:52::TNIH_2V.aa989ac5-1a87-49d3-b958-054826432ad7ZXV","version":1},{"_disambiguationId":null,"activityStatus":"IN_PROGRESS","creationTimestamp":1425339343782,"description":"{\"summary\":\"play on off\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339343782","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339340942,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339340942","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:40::TNIH_2V.02c47fe4-c3a7-4636-b570-696c606c08caZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339334501,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:33::TNIH_2V.c0644152-707a-4185-9042-37bff30ed2c3ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:33::TNIH_2V.c0644152-707a-4185-9042-37bff30ed2c3ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339334501","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:33::TNIH_2V.c0644152-707a-4185-9042-37bff30ed2c3ZXV","version":1},{"_disambiguationId":null,"activityStatus":"INVALID","creationTimestamp":1425339323100,"description":"{\"summary\":\"search trav queen by honey black\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"entryType\":\"FindMusic\",\"mediaOwnerCustomerId\":\"A3JJZQV8OKZJOE\",\"playQueuePrime\":false,\"completionCode\":\"ITEM_NOT_FOUND\",\"artistName\":\"honey black\",\"serviceName\":\"DIGITAL_MUSIC_STORE\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339323100","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339317233,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339317233","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:15::TNIH_2V.cae13a9f-63f5-4ebc-a75f-5a44fe85de45ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339306052,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:05::TNIH_2V.c1d8d5b4-2ea0-40c7-a65f-0e9884432360ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:05::TNIH_2V.c1d8d5b4-2ea0-40c7-a65f-0e9884432360ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339306052","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:05::TNIH_2V.c1d8d5b4-2ea0-40c7-a65f-0e9884432360ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339304221,"description":"{\"summary\":\"alexa stop\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:03::TNIH_2V.8407b71c-9ddf-4cc2-99e5-9fd8b3062c8dZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:03::TNIH_2V.8407b71c-9ddf-4cc2-99e5-9fd8b3062c8dZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339304221","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/35:03::TNIH_2V.8407b71c-9ddf-4cc2-99e5-9fd8b3062c8dZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339288271,"description":"{\"summary\":\"alexa stop alexa search trap queen\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:43::TNIH_2V.aabdc96a-dc50-44eb-b4cb-ded0b885c1e9ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:43::TNIH_2V.aabdc96a-dc50-44eb-b4cb-ded0b885c1e9ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"songName\":\"We Will Rock You\",\"entryType\":\"FindMusic\",\"mediaOwnerCustomerId\":\"A3JJZQV8OKZJOE\",\"playedMediaArtistName\":\"Queen\",\"playQueuePrime\":false,\"playedMediaSongName\":\"We Will Rock You\",\"artistId\":\"300149122\",\"serviceName\":\"DIGITAL_MUSIC_STORE\",\"trackReferenceId\":\"ade6dcd7-cc83-4931-82c6-fff111d3823a:1\",\"playQueueId\":\"ade6dcd7-cc83-4931-82c6-fff111d3823a\",\"playedMediaAlbumName\":\"Greatest Hits\",\"completionCode\":\"SUCCESS\",\"artistName\":\"Queen\",\"artistAsin\":\"B000QK71AG\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339288271","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:43::TNIH_2V.aabdc96a-dc50-44eb-b4cb-ded0b885c1e9ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339278415,"description":"{\"summary\":\"play trap queen\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339278415","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV","version":3},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339276490,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339276490","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:35::TNIH_2V.df8784fd-72f8-4021-94d1-ffef06d57f96ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339249140,"description":"{\"summary\":\"alexa please what is an amazon echo\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:06::TNIH_2V.c5acbf09-d0df-4d14-b145-877f9e644843ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:06::TNIH_2V.c5acbf09-d0df-4d14-b145-877f9e644843ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"entryType\":\"Knowledge\",\"answerText\":\"Amazon Echo is a device designed around your voice that can provide information, music, news, weather, and more.\",\"answered\":true,\"searchQueryText\":\"what is an amazon echo\",\"validForGUI\":true,\"domains\":[],\"answerEntities\":[{\"tkid\":\"[amazon echo]\",\"commonTranslation\":\"Amazon Echo\",\"properties\":{\"display_hint\":\"[type of manufactured item]\",\"urs\":\"Amazon Echo, a device designed around your voice that can provide information, music, news, weather, and more\",\"name\":\"Amazon Echo\",\"wikipedia_page\":\"http://en.wikipedia.org/wiki/Amazon_Echo\"},\"wikipediaUrl\":\"http://en.wikipedia.org/wiki/Amazon_Echo\"}],\"spokenAnswerText\":\"Amazon Echo is a device designed around your voice that can provide information, music, news, weather, and more.\",\"understood\":true,\"questionText\":\"What is an amazon echo?\",\"answerListIntroductionText\":\"Amazon Echo is a device designed around your voice that can provide information, music, news, weather, and more.\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339249140","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/34:06::TNIH_2V.c5acbf09-d0df-4d14-b145-877f9e644843ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339233904,"description":"{\"summary\":\"alexa what are you\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:50::TNIH_2V.3a144f12-362d-4d7b-be76-f10482232996ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:50::TNIH_2V.3a144f12-362d-4d7b-be76-f10482232996ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"entryType\":\"Knowledge\",\"answerText\":\"I'm an Amazon Echo.\",\"answered\":true,\"searchQueryText\":\"what are you\",\"validForGUI\":false,\"domains\":[\"[phatic domain]\"],\"spokenAnswerText\":\"I'm an Amazon Echo.\",\"understood\":true,\"questionText\":\"What are you?\",\"answerListIntroductionText\":\"I'm an Amazon Echo.\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339233904","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:50::TNIH_2V.3a144f12-362d-4d7b-be76-f10482232996ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425339222291,"description":"{\"summary\":\"alexa hello\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:39::TNIH_2V.d354075d-5fdd-4d34-b49f-6990b60d1df9ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:39::TNIH_2V.d354075d-5fdd-4d34-b49f-6990b60d1df9ZXV\"}","domainAttributes":"{\"disambiguated\":false,\"nBestList\":[{\"entryType\":\"Knowledge\",\"answerText\":\"Hi.\",\"answered\":true,\"searchQueryText\":\"hello\",\"validForGUI\":false,\"domains\":[\"[phatic domain]\"],\"spokenAnswerText\":\"Hi.\",\"understood\":true,\"questionText\":\"Hello\",\"answerListIntroductionText\":\"Hi.\"}]}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425339222291","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/23/B0F007125026018C/33:39::TNIH_2V.d354075d-5fdd-4d34-b49f-6990b60d1df9ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425299360880,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/29:19::TNIH_2V.5d0ba127-cfed-48de-a783-0396ce78b447ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/29:19::TNIH_2V.5d0ba127-cfed-48de-a783-0396ce78b447ZXV\"}","domainAttributes":"{\"toDoId\":\"60732ec2-8ab0-3219-b55b-0ea496e11f24\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425299360880","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/29:19::TNIH_2V.5d0ba127-cfed-48de-a783-0396ce78b447ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425299314403,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:33::TNIH_2V.bedac0e3-0349-4844-8d33-d863fa416198ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:33::TNIH_2V.bedac0e3-0349-4844-8d33-d863fa416198ZXV\"}","domainAttributes":"{\"toDoId\":\"ad958842-1981-36be-b240-06e2f00eedf1\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425299314403","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:33::TNIH_2V.bedac0e3-0349-4844-8d33-d863fa416198ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425299300768,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:19::TNIH_2V.345d70e6-fdbe-4d79-960b-68e4199722d9ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:19::TNIH_2V.345d70e6-fdbe-4d79-960b-68e4199722d9ZXV\"}","domainAttributes":"{\"toDoId\":\"9e58ace6-b63e-3eaa-bdea-ceb4ff41f526\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425299300768","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/12/B0F007125026018C/28:19::TNIH_2V.345d70e6-fdbe-4d79-960b-68e4199722d9ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425266136927,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:35::TNIH_2V.c699fe4b-10ac-4b4b-9663-9640e3b7c85dZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:35::TNIH_2V.c699fe4b-10ac-4b4b-9663-9640e3b7c85dZXV\"}","domainAttributes":"{\"toDoId\":\"e0a1c268-9933-3259-8f67-2b69e0190bee\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425266136927","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:35::TNIH_2V.c699fe4b-10ac-4b4b-9663-9640e3b7c85dZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425266121989,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:20::TNIH_2V.0f3207c2-815d-491a-b787-28a20ed97e9bZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:20::TNIH_2V.0f3207c2-815d-491a-b787-28a20ed97e9bZXV\"}","domainAttributes":"{\"toDoId\":\"239a13fe-2dad-34cf-bd00-dc182e9f8e57\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425266121989","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:20::TNIH_2V.0f3207c2-815d-491a-b787-28a20ed97e9bZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425266106930,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:05::TNIH_2V.694c7527-0fc6-43ec-a4b1-d8855b7c8435ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:05::TNIH_2V.694c7527-0fc6-43ec-a4b1-d8855b7c8435ZXV\"}","domainAttributes":"{\"toDoId\":\"d10bff8f-6a27-3696-9b67-70f66d386719\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425266106930","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/03/B0F007125026018C/15:05::TNIH_2V.694c7527-0fc6-43ec-a4b1-d8855b7c8435ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264764801,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:43::TNIH_2V.6db39c1c-1656-40c2-8726-6bbbf36acbbfZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:43::TNIH_2V.6db39c1c-1656-40c2-8726-6bbbf36acbbfZXV\"}","domainAttributes":"{\"toDoId\":\"743e0b7b-b105-3da3-b8d8-45d350425246\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264764801","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:43::TNIH_2V.6db39c1c-1656-40c2-8726-6bbbf36acbbfZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264752939,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:31::TNIH_2V.28b764b5-7960-4641-a9a3-5c0a530cbe54ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:31::TNIH_2V.28b764b5-7960-4641-a9a3-5c0a530cbe54ZXV\"}","domainAttributes":"{\"toDoId\":\"71931d83-cbdf-3e43-9dac-2a7a5edf73c2\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264752939","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:31::TNIH_2V.28b764b5-7960-4641-a9a3-5c0a530cbe54ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264739942,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:18::TNIH_2V.a2203969-b731-4401-9133-0450cac0cca7ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:18::TNIH_2V.a2203969-b731-4401-9133-0450cac0cca7ZXV\"}","domainAttributes":"{\"toDoId\":\"8863a178-3dde-3f12-8610-f7580f9e03cc\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264739942","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:18::TNIH_2V.a2203969-b731-4401-9133-0450cac0cca7ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264726904,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:05::TNIH_2V.188a5c34-186b-4bf6-b063-e4513d92bb72ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:05::TNIH_2V.188a5c34-186b-4bf6-b063-e4513d92bb72ZXV\"}","domainAttributes":"{\"toDoId\":\"a23d8c0d-8057-3d1b-8166-2cbbac7f82ff\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264726904","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/52:05::TNIH_2V.188a5c34-186b-4bf6-b063-e4513d92bb72ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264715504,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/51:54::TNIH_2V.bd19a38b-f68f-41fc-b354-b13aca82417dZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/51:54::TNIH_2V.bd19a38b-f68f-41fc-b354-b13aca82417dZXV\"}","domainAttributes":"{\"toDoId\":\"e96d400e-a51c-33c9-8c3f-bc0f53849e7d\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264715504","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/51:54::TNIH_2V.bd19a38b-f68f-41fc-b354-b13aca82417dZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264594844,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:53::TNIH_2V.ef68d605-212d-42d8-807d-88a6aa42abc3ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:53::TNIH_2V.ef68d605-212d-42d8-807d-88a6aa42abc3ZXV\"}","domainAttributes":"{\"toDoId\":\"2b222acd-d05d-37b6-9684-16fdc019b9ae\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264594844","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:53::TNIH_2V.ef68d605-212d-42d8-807d-88a6aa42abc3ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264581174,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:39::TNIH_2V.2cb3142f-2563-414d-b1c9-78e3ebdb096aZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:39::TNIH_2V.2cb3142f-2563-414d-b1c9-78e3ebdb096aZXV\"}","domainAttributes":"{\"toDoId\":\"741eb591-5785-3951-a6f4-196382e9936e\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264581174","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/49:39::TNIH_2V.2cb3142f-2563-414d-b1c9-78e3ebdb096aZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264533257,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:51::TNIH_2V.66598930-3d91-4723-860b-b0c56b7d5cd2ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:51::TNIH_2V.66598930-3d91-4723-860b-b0c56b7d5cd2ZXV\"}","domainAttributes":"{\"toDoId\":\"1528d62f-1f8a-3f47-bf12-a79d7efec3bc\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264533257","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:51::TNIH_2V.66598930-3d91-4723-860b-b0c56b7d5cd2ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264518773,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:37::TNIH_2V.12ffed86-9a92-4dbd-8887-b7b007e936f7ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:37::TNIH_2V.12ffed86-9a92-4dbd-8887-b7b007e936f7ZXV\"}","domainAttributes":"{\"toDoId\":\"b0c22043-c93d-3118-9090-636af12174ad\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264518773","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/48:37::TNIH_2V.12ffed86-9a92-4dbd-8887-b7b007e936f7ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264433291,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/47:11::TNIH_2V.4eed1a3f-ec4d-47e1-8800-9942c48e07c9ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/47:11::TNIH_2V.4eed1a3f-ec4d-47e1-8800-9942c48e07c9ZXV\"}","domainAttributes":"{\"toDoId\":\"a8b131e7-3ec1-3a7e-a157-6c4fafc47cbd\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264433291","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/47:11::TNIH_2V.4eed1a3f-ec4d-47e1-8800-9942c48e07c9ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264415020,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:53::TNIH_2V.3fa66730-67ef-4b44-b64b-fc6c02bfbaabZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:53::TNIH_2V.3fa66730-67ef-4b44-b64b-fc6c02bfbaabZXV\"}","domainAttributes":"{\"toDoId\":\"c5020ed4-a7f2-34c4-921a-2df710073a2c\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264415020","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:53::TNIH_2V.3fa66730-67ef-4b44-b64b-fc6c02bfbaabZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264403089,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:41::TNIH_2V.54758141-5a6b-44ec-af39-35a92d1bffdbZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:41::TNIH_2V.54758141-5a6b-44ec-af39-35a92d1bffdbZXV\"}","domainAttributes":"{\"toDoId\":\"ca409449-e061-301b-8f2a-383faf76ef1b\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264403089","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:41::TNIH_2V.54758141-5a6b-44ec-af39-35a92d1bffdbZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264382755,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:21::TNIH_2V.7043cc58-41e2-4aaa-8a2b-8baf42907cf8ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:21::TNIH_2V.7043cc58-41e2-4aaa-8a2b-8baf42907cf8ZXV\"}","domainAttributes":"{\"toDoId\":\"44405894-7b17-371c-9d71-32768e507e28\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264382755","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:21::TNIH_2V.7043cc58-41e2-4aaa-8a2b-8baf42907cf8ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264364132,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:02::TNIH_2V.c76b4660-ab1d-4877-9505-ae34a8c679ddZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:02::TNIH_2V.c76b4660-ab1d-4877-9505-ae34a8c679ddZXV\"}","domainAttributes":"{\"toDoId\":\"6ad4311a-1ec9-34eb-8f89-723984b4013a\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264364132","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/46:02::TNIH_2V.c76b4660-ab1d-4877-9505-ae34a8c679ddZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425264350708,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/45:49::TNIH_2V.e407bf37-7004-49f4-8e7e-8cb626323bb5ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/45:49::TNIH_2V.e407bf37-7004-49f4-8e7e-8cb626323bb5ZXV\"}","domainAttributes":"{\"toDoId\":\"441fb096-a955-39b3-bcd8-944368aa6e37\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425264350708","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/02/02/B0F007125026018C/45:49::TNIH_2V.e407bf37-7004-49f4-8e7e-8cb626323bb5ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238601217,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/36:39::TNIH_2V.1cbf2663-7ea8-4fde-976a-15794c1fedb3ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/36:39::TNIH_2V.1cbf2663-7ea8-4fde-976a-15794c1fedb3ZXV\"}","domainAttributes":"{\"toDoId\":\"28272a2c-5090-31c6-a6e6-fc45b7794c8e\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238601217","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/36:39::TNIH_2V.1cbf2663-7ea8-4fde-976a-15794c1fedb3ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238533822,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:32::TNIH_2V.76c0ad3d-aaca-4527-bac6-f0d063c10549ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:32::TNIH_2V.76c0ad3d-aaca-4527-bac6-f0d063c10549ZXV\"}","domainAttributes":"{\"toDoId\":\"27f9d5a0-6819-35e1-9966-3df4296df9e3\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238533822","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:32::TNIH_2V.76c0ad3d-aaca-4527-bac6-f0d063c10549ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238508293,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:06::TNIH_2V.37558e7c-c99f-4cb1-b0f9-9b98fc310825ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:06::TNIH_2V.37558e7c-c99f-4cb1-b0f9-9b98fc310825ZXV\"}","domainAttributes":"{\"toDoId\":\"58634272-b472-3065-9175-12b9def8614b\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238508293","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/35:06::TNIH_2V.37558e7c-c99f-4cb1-b0f9-9b98fc310825ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238377277,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:56::TNIH_2V.5f138b62-dff4-4a83-99b5-0eeb6ef9fdeaZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:56::TNIH_2V.5f138b62-dff4-4a83-99b5-0eeb6ef9fdeaZXV\"}","domainAttributes":"{\"toDoId\":\"5eb80d6a-7e8a-3de4-b959-c8352dec3894\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238377277","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:56::TNIH_2V.5f138b62-dff4-4a83-99b5-0eeb6ef9fdeaZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238355808,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:34::TNIH_2V.69630479-7fd1-45bb-ba40-01d048fe56ecZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:34::TNIH_2V.69630479-7fd1-45bb-ba40-01d048fe56ecZXV\"}","domainAttributes":"{\"toDoId\":\"c26c074c-a742-355f-958e-603ffe36a459\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238355808","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:34::TNIH_2V.69630479-7fd1-45bb-ba40-01d048fe56ecZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238339415,"description":"{\"summary\":\"to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV\"}","domainAttributes":"{\"toDoId\":\"603dcf3b-8fdc-3b47-810c-c864bccd7c8f\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238339415","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425238337566,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425238337566","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/19/B0F007125026018C/32:16::TNIH_2V.0ad8568d-b82d-44eb-baa2-4c81bbe1f9f6ZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425231340386,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:38::TNIH_2V.acc22243-c8dc-4d3c-804e-54d313dc97aeZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:38::TNIH_2V.acc22243-c8dc-4d3c-804e-54d313dc97aeZXV\"}","domainAttributes":"{\"toDoId\":\"cef42892-7426-3781-b3d1-1b30a09c0fa9\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425231340386","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:38::TNIH_2V.acc22243-c8dc-4d3c-804e-54d313dc97aeZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425231314865,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:13::TNIH_2V.6e046846-b5d8-4435-919f-1367cb4764e7ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:13::TNIH_2V.6e046846-b5d8-4435-919f-1367cb4764e7ZXV\"}","domainAttributes":"{\"toDoId\":\"59fc7267-cd10-3545-bba2-8192417103b2\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425231314865","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/35:13::TNIH_2V.6e046846-b5d8-4435-919f-1367cb4764e7ZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425231291790,"description":"{\"summary\":\"to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV/1\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV\"}","domainAttributes":"{\"toDoId\":\"41194574-ff54-30e1-8ddc-7b930a20ff3d\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425231291790","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV","version":2},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425231289994,"description":"{\"summary\":\"alexa\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV\"}","domainAttributes":null,"domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425231289994","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/34:49::TNIH_2V.f0b772b6-5d9a-498b-9167-eb1dc90f97ceZXV","version":1},{"_disambiguationId":null,"activityStatus":"SUCCESS","creationTimestamp":1425231175524,"description":"{\"summary\":\"alexa to do\",\"firstUtteranceId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV/0\",\"firstStreamId\":\"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV\"}","domainAttributes":"{\"toDoId\":\"51ed3ec5-9508-34ab-82f7-563e4fc8c37b\",\"customerId\":\"A3JJZQV8OKZJOE\"}","domainType":null,"feedbackAttributes":null,"id":"A3JJZQV8OKZJOE#1425231175524","intentType":null,"registeredCustomerId":"A3JJZQV8OKZJOE","sourceActiveUsers":null,"sourceDeviceIds":[{"deviceType":"AB72C64C86AW2","serialNumber":"B0F007125026018C"}],"utteranceId":"AB72C64C86AW2:1.0/2015/03/01/17/B0F007125026018C/32:53::TNIH_2V.09f87307-1231-4e19-86ff-246cc19227d5ZXV","version":2}],"endDate":1425339383970,"startDate":1425231175524}
 */
public class EchoActivityItemImpl implements EchoActivityItem {
  private static final ObjectMapper mapper                     = new ObjectMapper();
  
  private String              _disambiguationId;
  private String              activityStatus;
  private Calendar            creationTimestamp;
  private ActivityDescription activityDescription;  
  private String              description;  
  private String              domainAttributes;
  private String              domainType;
  private String              feedbackAttributes;
  private String              id;
  private String              intentType;
  private String              registeredCustomerId;
  private String              sourceActiveUsers;
  private SourceDeviceId[]    sourceDeviceIds;
  private String              utteranceId;
  private Integer             version;
  
  public EchoActivityItemImpl( ) {    
  }
  
  /**
   * @param stringDate
   * @return
   */
  public Calendar generateCalendarFromLongAsString( final String stringDate ) {
    Preconditions.checkNotNull( stringDate );
    
    Long milliseconds = Long.parseLong( stringDate );
    Date date         = new Date( milliseconds );
      
    return DateUtils.toCalendar( date );
  }
  

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getCreationTimestamp()
   */
  public Calendar getCreationTimestamp() {
    return this.creationTimestamp;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setCreationTimestamp(java.util.Calendar)
   */
  public void setCreationTimestamp( final  Calendar creationTimestamp ) {
    this.creationTimestamp = creationTimestamp;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getVersion()
   */
  public Integer getVersion() {
    return version;
  }


  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setVersion(java.lang.Integer)
   */
  public void setVersion( final Integer version ) {
    this.version = version;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getUtteranceId()
   */
  public String getUtteranceId() {
    return utteranceId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setUtteranceId(java.lang.String)
   */
  public void setUtteranceId( final String utteranceId ) {
    this.utteranceId = utteranceId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#get_disambiguationId()
   */
  public String get_disambiguationId() {
    return _disambiguationId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#set_disambiguationId(java.lang.String)
   */
  public void set_disambiguationId( String _disambiguationId ) {
    this._disambiguationId = _disambiguationId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getActivityStatus()
   */
  public String getActivityStatus() {
    return activityStatus;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setActivityStatus(java.lang.String)
   */
  public void setActivityStatus( String activityStatus ) {
    this.activityStatus = activityStatus;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDescription()
   */
  public String getDescription() {
    return description;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDescription(java.lang.String)
   */
  public void setDescription( String description ) throws JsonParseException, JsonMappingException, IOException {
    this.activityDescription = mapper.readValue( description, ActivityDescription.class );
    this.description = description;
  }
  
  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getActivityDescription()
   */
  public ActivityDescription getActivityDescription() {
    return this.activityDescription;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDomainAttributes()
   */
  public String getDomainAttributes() {
    return domainAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDomainAttributes(java.lang.String)
   */
  public void setDomainAttributes( String domainAttributes ) throws JsonParseException, JsonMappingException, IOException {
    this.domainAttributes = domainAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getDomainType()
   */
  public String getDomainType() {
    return domainType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setDomainType(java.lang.String)
   */
  public void setDomainType( String domainType ) {
    this.domainType = domainType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getFeedbackAttributes()
   */
  public String getFeedbackAttributes() {
    return feedbackAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setFeedbackAttributes(java.lang.String)
   */
  public void setFeedbackAttributes( String feedbackAttributes ) {
    this.feedbackAttributes = feedbackAttributes;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getId()
   */
  public String getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setId(java.lang.String)
   */
  public void setId( String id ) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getIntentType()
   */
  public String getIntentType() {
    return intentType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setIntentType(java.lang.String)
   */
  public void setIntentType( String intentType ) {
    this.intentType = intentType;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getRegisteredCustomerId()
   */
  public String getRegisteredCustomerId() {
    return registeredCustomerId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setRegisteredCustomerId(java.lang.String)
   */
  public void setRegisteredCustomerId( String registeredCustomerId ) {
    this.registeredCustomerId = registeredCustomerId;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getSourceActiveUsers()
   */
  public String getSourceActiveUsers() {
    return sourceActiveUsers;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setSourceActiveUsers(java.lang.String)
   */
  public void setSourceActiveUsers( String sourceActiveUsers ) {
    this.sourceActiveUsers = sourceActiveUsers;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#getSourceDeviceIds()
   */
  public SourceDeviceId[] getSourceDeviceIds() {
    return sourceDeviceIds;
  }

  /* (non-Javadoc)
   * @see com.javasteam.amazon.echo.EchoActivityItem#setSourceDeviceIds(com.javasteam.amazon.echo.activity.SourceDeviceId[])
   */
  public void setSourceDeviceIds( SourceDeviceId[] sourceDeviceIds ) {
    this.sourceDeviceIds = sourceDeviceIds;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder( 250 );

    /*
    builder.append( "EchoTodoItem: \n  ItemId:          " ).append( this.getItemId() )
           .append( "\n  text:            " ).append( this.getText() )
           .append( "\n  type:            " ).append( this.getType() )
           .append( "\n  complete:        " ).append( this.isComplete() )
           .append( "\n  deleted:         " ).append( this.isDeleted() )
           .append( "\n  createdDate:     " ).append( ( this.getCreatedDate()         != null ? this.getCreatedDate().getTimeInMillis()         : "null" ))
           .append( "\n  lastLocalUpdate: " ).append( ( this.getLastLocalUpdateDate() != null ? this.getLastLocalUpdateDate().getTimeInMillis() : "null" ))
           .append( "\n  lastUpdated:     " ).append( ( this.getLastUpdatedDate()     != null ? this.getLastUpdatedDate().getTimeInMillis()     : "null" ))
           .append( "\n  version:         " ).append( this.getVersion() )
           .append( "\n  utteranceId:     " ).append( this.getUtteranceId() )
           ;
    
    // if created in the web app there will be no NbestItems.....
    if( this.getNbestItems() != null ) {
      for( String item : this.getNbestItems() ) {
        builder.append( "\n      ->" ).append( item );
      }
    }
    */
    
    return builder.toString();
  }
  
  @Override
  public boolean equals( final Object otherObject ) {
    boolean retval =  ( otherObject == null ) 
                   && ( otherObject == this )
                   && ( otherObject.getClass() != getClass() );
    
    if( retval ) {  
      //EchoTodoItemImpl comparisonObject = (EchoTodoItemImpl) otherObject;
    
      /*
      retval = new EqualsBuilder().appendSuper( super.equals( otherObject ))
                                  .append( getItemId(),              comparisonObject.getItemId() )
                                  .append( getText(),                comparisonObject.getText() )
                                  .append( getType(),                comparisonObject.getType() )
                                  .append( isComplete(),             comparisonObject.isComplete() )
                                  .append( isDeleted(),              comparisonObject.isDeleted() )
                                  .append( getCreatedDate(),         comparisonObject.getCreatedDate() )
                                  .append( getLastLocalUpdateDate(), comparisonObject.getLastLocalUpdateDate() )
                                  .append( getLastUpdatedDate(),     comparisonObject.getLastUpdatedDate() )
                                  .append( getVersion(),             comparisonObject.getVersion() )
                                  .append( getUtteranceId(),         comparisonObject.getUtteranceId() )
                                  .isEquals();
      */
    }
    
    return retval;
   }
}
