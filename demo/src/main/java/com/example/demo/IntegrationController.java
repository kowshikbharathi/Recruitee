package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.RecrutieeResponse;
import com.example.demo.model.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/integration.com")
public class IntegrationController {
	
    @GetMapping("/api")
    public CompletableFuture<Void> integrationByRecrutiee(@RequestBody RecrutieeResponse recrutieeResponse) throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        
        //Important
        Payload payload = recrutieeResponse.payload;
        String campaginDOJ = payload.details.toStage.name;
        Map<String, String> campaginMap = new HashMap<String, String>()
        {{
             put("Ideas2IT: Immediate Joiners", "cam_uvYa6TGqt3JSRbyPs");
             put("Ideas2IT: DOJ in 1 month", "cam_Ko8yJWBrtLgqeFEod");
             put("Ideas2IT: DOJ in 2 month", "cam_C6TEDMeZMGPYxzBgk");
             put("Ideas2IT: DOJ in 3 month", "cam_qyJDT27AGJThBe2ME");
             put("Ideas2IT: DOJ in 15 days", "cam_Ko8yJWBrtLgqeFEod");
        }};
        
       if(campaginMap.containsKey(campaginDOJ)) {
    	   //For Basic Authentication
    	   String auth =  ":f0777b800f62ebaf6e57b03d8ebb1887";
    	   byte[] encodedAuth = Base64.encodeBase64(auth.getBytes("UTF-8"));
    	   String authHeaderValue = "Basic " + new String(encodedAuth);
    	   String email = payload.getCandidate().emails.get(0);
        
    	   //API url formation
    	   String urlFormed = "https://api.lemlist.com/api/campaigns/"+campaginMap.get(payload.details.toStage.name)+"/leads/";
    	   urlFormed = urlFormed.concat(email);
    	   URI uri = new URI(urlFormed);       
    	   ObjectMapper objectMapper = new ObjectMapper();
    	   //RequestBody
    	   Map<String,String>map=new HashMap<>();
    	   map.put("firstName", payload.getCandidate().name);
    	   map.put("companyName", payload.getCompany().name);
    	   String requestBody = objectMapper
              .writerWithDefaultPrettyPrinter()
              .writeValueAsString(map);

    	   // Create HTTP request object
    	   HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST((BodyPublishers.ofString(requestBody)))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build();
    	   // Send HTTP request
    	   return  HttpClient.newHttpClient()
    			   .sendAsync(request, BodyHandlers.ofString())
    			   .thenApply(HttpResponse::statusCode)
    			   .thenAccept(System.out::println);
	   
    }
       if(campaginDOJ.equals("Hired")) {
	   
       }
       return null;
}
}
