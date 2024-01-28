package com.gingos.services;

import com.gingos.models.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UsersHttpService {
    ObjectMapper objectMapper;

    public UsersHttpService(){
         this.objectMapper = new ObjectMapper();
         this.objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CompletableFuture<User> GetRandomUserAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<User> user = CompletableFuture.supplyAsync(() -> {
            var rndInt = new Random().nextInt(0, 15);
            var userAsString = httpGetAsString(rndInt);
            try {
                return objectMapper.readValue(userAsString, User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return user;
    }

    private String httpGetAsString(int rndInt){
        var apiUrl = "https://jsonplaceholder.org/users/" + rndInt;
        try {
            // Specify the URL to send the request to
            URL url = new URL(apiUrl);

            // Open a connection to the specified URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the input stream
            var inputStream = connection.getInputStream();
            var ips = new InputStreamReader(inputStream);
            var br = new BufferedReader(ips);
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            connection.disconnect();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
