package application;

import ceng453.entity.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class RestServiceConsumer {

    private static final String restUrl = "http://localhost:8080/";

    RestServiceConsumer() {
    }

    String register(User user) {
        try {
            String registerUrl = restUrl.concat("users");
            URL url = new URL(registerUrl);
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
            output = br.readLine();

            conn.disconnect();

            return output;

        } catch (IOException e) {

            e.printStackTrace();
            return "Fail";

        }

    }

    User login(User user) {
        try {
            String loginUrl = restUrl.concat("login");
            URL url = new URL(loginUrl);
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
            String loginUser = "";

            while ((output = br.readLine()) != null) {
                loginUser = loginUser.concat(output);
            }

            if (loginUser.equals("")) {
                conn.disconnect();
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
            mapper.addMixIn(Player.class, PlayerMixIn.class);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            conn.disconnect();

            return mapper.readValue(loginUser, User.class);

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    void addScore(String userId, String score) {
        try {

            String scoreUrl = restUrl + "users/" + userId + "/" + score;
            URL url = new URL(scoreUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");


            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }


            conn.disconnect();


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    String getScoreboard(String time) {
        try {
            String leaderBoardURL = restUrl + "scores/";
            if (time.equals("weekly")) {
                leaderBoardURL = leaderBoardURL.concat("weekly");
            } else {
                leaderBoardURL = leaderBoardURL.concat("all");
            }
            URL url = new URL(leaderBoardURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String scoreBoard = "";
            while ((output = br.readLine()) != null) {
                scoreBoard = scoreBoard.concat(output);
            }

            conn.disconnect();

            return scoreBoard;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }
    }
}
