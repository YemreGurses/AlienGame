package application;

import ceng453.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestServiceConsumer {

    public RestServiceConsumer() {
    }

    public String register(User user) {
        try {

            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();

            String input = "{\"name\":\"";

            input = input.concat(name);
            input = input.concat("\",\"password\":\"");
            input = input.concat(password);
            input = input.concat("\",\"email\":\"");
            input = input.concat(email);
            input = input.concat("\"}");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                return output;
            }

            conn.disconnect();


        } catch (IOException e) {

            e.printStackTrace();

        }

        return "Fail";
    }

    public String login(User user) {
        try {

            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = user.getName();
            String password = user.getPassword();

            String input = "{\"name\":\"";

            input = input.concat(name);
            input = input.concat("\",\"password\":\"");
            input = input.concat(password);
            input = input.concat("\"}");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                return output;
            }

            conn.disconnect();


        } catch (IOException e) {

            e.printStackTrace();

        }

        return "Fail";
    }

    public void addScore(String userId, String score) {
        try {

            String scoreUrl = "http://localhost:8080/users/" + userId + "/" + score;
            URL url = new URL(scoreUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = " ";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();


        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
