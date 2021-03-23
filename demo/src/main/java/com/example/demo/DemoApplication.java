package com.example.demo;

import com.example.demo.model.Example;
import com.example.demo.model.Payload;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/api")
    public String getH(@RequestBody Example ex) throws IOException {
        System.out.println("Hello :) ");
        //Important
        Payload payload = ex.payload;
        System.out.println(payload.getCandidate().emails);
        String email = payload.getCandidate().emails.get(0);
        System.out.println(payload.details.toStage.name);

        String urlFormed = "https://api.lemlist.com/api/campaigns/cam_qyJDT27AGJThBe2ME/leads/";

        //Make an array for campaigns and replace in above string respectively for the incoming payload.details.toStage.name

        urlFormed = urlFormed.concat(email);
        System.out.println(urlFormed);
        URL url = new URL(urlFormed);


        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

        //For Basic Authentication
        String auth =  ":f0777b800f62ebaf6e57b03d8ebb1887";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        connection.setRequestProperty("Authorization", authHeaderValue);

        //For Sending Data like FirstName, Lastname
        String jsonInputString = "{\"firstName\": \"John\", \"lastName\": \"Wick\"}";
        // For POST only - START
        connection.setDoOutput(true);
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();
            // For POST only - END
        }


        // This line makes the request
        InputStream responseStream = connection.getInputStream();
        // Finally we have the response
        System.out.println(responseStream);

        return "Execution Done :) ";
    }

}
