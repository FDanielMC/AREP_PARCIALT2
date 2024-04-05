package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static spark.Spark.*;

public class MathServices {

    private static final String ERROR = "El elemento no está presente";


    public static void main(String[] args) {
        //staticFiles.location("/public");
        port(getPort());
        get("/linearsearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            System.out.println("linear: " + value);
            String output = Integer.toString(linear(stringListToIntegerList(list),Integer.parseInt(value)));
            System.out.println(jsonResponse("linear", req.queryParams("list"), value, output == "-1" ? ERROR : output));
            return jsonResponse("linear", req.queryParams("list"), value, output == "-1" ? ERROR : output);
        });
        get("/binarysearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            System.out.println("binary: " + value);
            String output = Integer.toString(binary(stringListToIntegerList(list),Integer.parseInt(value)));
            return jsonResponse("binary", req.queryParams("list"), value, output == "-1" ? ERROR : output);
        });
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

    public static String jsonResponse(String operation, String inputList, String inputValue, String output) {
        return "{" +
                " \"operation\": \"" + operation + "\"," +
                " \"inputlist\": " + inputList + "," +
                " \"value\": " + inputValue + "," +
                " \"output\":  \"" + output + "\"" +
                "}";
    }

    public static int binary(int[] list, int value) {
        return recursiveBinary(list, value, 0, list.length-1);
    }

    private static int recursiveBinary(int[] list, int value, int first, int last) {
        int result = -1;
        int middle = first + (last - first) / 2;
        if (first > last) result =-1; // El elemento no se encontró.
        if (list[middle] == value) result = middle;
        else if (list[middle] > value) result = recursiveBinary(list, value, first, middle - 1);
        else result = recursiveBinary(list, value, middle + 1, last);
        return result;
    }

    public static int linear(int[] list, int value) {
        int result = -1;
        for(int i = 0; i < list.length;i++){
            if(list[i] == value){
                result = i;
            }
        }
        return result;
    }

    public static int[] stringListToIntegerList(String[] list){
        int[] listInt = new int[list.length];
        for(int i = 0; i < listInt.length; i++){
            listInt[i] = Integer.parseInt(list[i]);
        }
        return listInt;
    }

}