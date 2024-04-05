package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

/**
 * Clase que actúa como proxy para acceder a un servicio.
 *
 * @author Daniel Fernando Moreno Cerón
 * @version 1.0 (05/04/2024)
 */
public class ServiceProxy {


    private static String[] services;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static int instance = 0;

    /**
     * Método principal de la aplicación.
     *
     * @param args Parámetros de la línea de comandos.
     */
    public static void main(String[] args) {

        staticFiles.location("/public");
        port(getPort());
        System.out.println(System.getenv("SEARCH_SERVICES"));
        services = System.getenv("SEARCH_SERVICES").split(",");
        get("/linearsearch", (req, res) -> {
            res.type("application/json");
            String output = invoker(services[instance] + "linearsearch?list=" + req.queryParams("list") + "&value=" + req.queryParams("value"));
            System.out.println("Instancia elegida: " + services[instance]);
            changeServer();
            return output;
        });
        get("/binarysearch", (req, res) -> {
            res.type("application/json");
            String output = invoker(services[instance] + "binarysearch?list=" + req.queryParams("list") + "&value=" + req.queryParams("value"));
            System.out.println("Instancia elegida: " + services[instance]);
            changeServer();
            return output;
        });
    }

    /**
     * Obtiene el puerto para la aplicación.
     *
     * @return El número de puerto.
     */

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    /**
     * Cambia la instancia del servicio a utilizar.
     */
    private static void changeServer() {
        if (instance == services.length - 1) instance = 0;
        else instance++;
    }


    /**
     * Realiza una petición HTTP GET a una URL y devuelve la respuesta.
     *
     * @param url La URL a la que realizar la petición.
     * @return La respuesta de la petición en formato de cadena.
     * @throws IOException Si ocurre un error al realizar la petición.
     */
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