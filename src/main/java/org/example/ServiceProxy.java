package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class ServiceProxy {


    private static String[] math_services;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static int instance = 0;

    public static void main(String[] args) {

        staticFiles.location("/public");
        port(getPort());
        math_services = System.getenv("MATH_SERVICES").split(",");
        get("/linearsearch", (req, res) -> {
            res.type("application/json");
            String output = invoker(math_services[instance] + "linearsearch?list=" + req.queryParams("list") + "&value=" + req.queryParams("value"));
            changeInstance();
            return output;
        });
        get("/binarysearch", (req, res) -> {
            res.type("application/json");
            String output = invoker(math_services[instance] + "binarysearch?list=" + req.queryParams("list") + "&value=" + req.queryParams("value"));
            changeInstance();
            return output;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static void changeInstance() {
        if (instance == math_services.length - 1) instance = 0;
        else instance++;
    }

    private static String invoker(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        String inputLine;
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return response.toString();
    }
}