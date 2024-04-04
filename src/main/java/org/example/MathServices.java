package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static spark.Spark.*;

public class MathServices {

    private static final String ERROR = "El elemento no está presente";


    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getPort());
        get("/linearsearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            System.out.println("linear: " + value);
            String output = Integer.toString(linear(list,value));
            System.out.println(jsonResponse("linear", req.queryParams("list"), value, output == "-1" ? ERROR : output));
            return jsonResponse("linear", req.queryParams("list"), value, output == "-1" ? ERROR : output);
        });
        get("/binarysearch", (req, res) -> {
            res.type("application/json");
            String[] list = req.queryParams("list").split(",");
            String value = req.queryParams("value");
            System.out.println("binary: " + value);
            String output = Integer.toString(linear(list,value));
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


    public static int binary(String[] list, String value) {
        int intValue = Integer.parseInt(value);
        int[] listValues =  stringListToIntegerList(list);
        int result = -1;
        int middle = listValues.length/2;        // Mitad del array
        if (listValues[middle] == intValue)
            result = middle;
        else if (listValues.length == 1)
            result = -1;
        else if (listValues[middle] > intValue)
            return binary(Arrays.copyOfRange(listValues,0,middle),intValue);
        else
            return binary(Arrays.copyOfRange(listValues,middle+1,listValues.length),intValue);
        return result;
    }


    public static int linear(String[] list, String value) {
        int intValue = Integer.parseInt(value);
        int[] listValues =  stringListToIntegerList(list);
        int result = -1;
        for(int i = 0; i < listValues.length;i++){
            if(listValues[i] == intValue){
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


