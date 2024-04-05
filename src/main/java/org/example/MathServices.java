package org.example;

import static spark.Spark.*;

/**
 * Clase que proporciona servicios de búsqueda lineal y binario.
 *
 * @author Daniel Fernando Moreno Cerón
 * @version 1.0 (02/04/2024)
 */
public class MathServices {

    private static final String ERROR = "El elemento no está presente";

    /**
     * Método principal de la aplicación.
     *
     * @param args Parámetros de la línea de comandos.
     */
    public static void main(String[] args) {
        //staticFiles.location("/public");
        port(getPort());
        get("/linearsearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            String output = Integer.toString(linear(stringListToIntegerList(list),Integer.parseInt(value)));
            output = output.equals("-1") ? ERROR : output;
            System.out.println(jsonResponse("linear", req.queryParams("list"), value, output));
            return jsonResponse("linear", req.queryParams("list"), value, output);
        });
        get("/binarysearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            String output = Integer.toString(binary(stringListToIntegerList(list),Integer.parseInt(value)));
            output = output.equals("-1") ? ERROR : output;
            System.out.println(jsonResponse("binary", req.queryParams("list"), value, output));
            return jsonResponse("binary", req.queryParams("list"), value, output);
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
        return 35000;
    }

    /**
     * Método que crea una cadena en formato JSON con la operación, ya sea binaria o lineal, la lista en donde se va a
     * buscar el elemento, el elemento a buscar y el índice del elemento en caso que se encuentre.
     *
     * @param operation
     * @param inputList
     * @param inputValue
     * @param output
     * @return cadena en formato JSON con toda la información de la búsqueda binaria o lineal
     */
    public static String jsonResponse(String operation, String inputList, String inputValue, String output) {
        return "{" +
                " \"operation\": \"" + operation + "\"," +
                " \"inputlist\": " + inputList + "," +
                " \"value\": " + inputValue + "," +
                " \"output\":  \"" + output + "\"" +
                "}";
    }

    /**
     * Método que hace la búsqueda binaria de un entero en una lista de enteros.
     *
     * @param list
     * @param value
     * @return
     */
    public static int binary(int[] list, int value) {
        return recursiveBinary(list, value, 0, list.length-1);
    }

    /**
     * Método que hace la búsqueda binaria de un entero en una lista de enteros.
     *
     * @param list
     * @param value
     * @param first
     * @param last
     * @return
     */
    private static int recursiveBinary(int[] list, int value, int first, int last) {
        if(first > last) return -1;
        int middle = first + (last - first) / 2;
        if (list[middle] == value) return middle;
        else if (list[middle] > value) return recursiveBinary(list, value, first, middle - 1);
        else return recursiveBinary(list, value, middle + 1, last);
    }

    /**
     * Método que hace búsqueda lineal de un entero en una lista de enteros.
     *
     * @param list
     * @param value
     * @return
     */
    public static int linear(int[] list, int value) {
        int result = -1;
        for(int i = 0; i < list.length;i++){
            if(list[i] == value){
                result = i;
            }
        }
        return result;
    }

    /**
     * Método que convierte una lista de cadenas de texto que contienen enteros a una lista de enteros
     *
     * @param list
     * @return
     */
    public static int[] stringListToIntegerList(String[] list){
        int[] listInt = new int[list.length];
        for(int i = 0; i < listInt.length; i++){
            listInt[i] = Integer.parseInt(list[i]);
        }
        return listInt;
    }
}