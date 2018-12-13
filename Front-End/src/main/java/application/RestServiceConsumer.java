package application;

import ceng453.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestServiceConsumer {

    public RestServiceConsumer() {
    }

    public void register(User user) {
        try {

            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();

            String input = "";

            input.concat("{\"name\":\"");
            input.concat(name);
            input.concat("\",");
            input.concat(",\"password\":\"");
            input.concat(password);
            input.concat("\",");
            input.concat(",\"email\":\"");
            input.concat(email);
            input.concat("\"}");

            System.out.println(input);
//            String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
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


        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
}
