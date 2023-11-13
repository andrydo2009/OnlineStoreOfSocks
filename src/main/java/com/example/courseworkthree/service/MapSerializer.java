package com.example.courseworkthree.service;

import com.example.courseworkthree.model.operations.OperationsWithSocks;
import com.example.courseworkthree.model.socks.Socks;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class MapSerializer implements JsonSerializer<Map<OperationsWithSocks, Socks>> {
    @Override
    public JsonElement serialize(Map<OperationsWithSocks, Socks> map , Type typeOfSrc ,
                                 JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject ();

        String head = "Отчет об операциях";
        Gson gson = new GsonBuilder ().setDateFormat ( "yyyy-MM-dd" ).create ();
        JsonArray array = new JsonArray ();

        for (Map.Entry<OperationsWithSocks,Socks> entry : map.entrySet()) {
            array.add ( gson.toJsonTree ( "-------" ) );
            array.add ( gson.toJsonTree ( "Информация об операции :" ) );
            array.add ( gson.toJsonTree (entry.getKey()));
            array.add ( gson.toJsonTree ( "Информация о товаре :" ) );
            array.add ( gson.toJsonTree (entry.getValue ()));
            array.add ( gson.toJsonTree ( "-------" ) );
            jsonObject.add ( head,array );
        }
        return jsonObject;
    }
}