package com.example.courseworkthree.service;

import com.example.courseworkthree.model.operations.OperationsWithSocks;
import com.example.courseworkthree.model.socks.Socks;
import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapDeserialize implements JsonDeserializer<Map<OperationsWithSocks,Socks>> {
    @Override
    public Map<OperationsWithSocks,Socks> deserialize(JsonElement json, Type typeOfT,
                                                       JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        Map<OperationsWithSocks,Socks> map = new HashMap<> ();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            Socks socks = gson.fromJson(jsonArray.get(0), Socks.class);
            OperationsWithSocks operations = gson.fromJson(jsonArray.get(1), OperationsWithSocks.class);
            map.put(operations,socks);
        }
        return map;
    }
}
