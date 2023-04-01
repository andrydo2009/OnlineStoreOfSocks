package com.example.courseworkthree.service;

import com.example.courseworkthree.model.socks.ColorSocks;
import com.example.courseworkthree.model.socks.SizeSocks;
import com.example.courseworkthree.model.socks.Socks;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SocksDeserializer implements JsonDeserializer<Socks> {

    @Override
    public Socks deserialize(JsonElement jsonElement , Type type , JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = new JsonObject ();
        Socks socks = new Socks ();
        socks.setColorSocks ( ColorSocks.valueOf ( jsonObject.get ( "Цвет" ).getAsString () ) );
        socks.setSizeSocks ( SizeSocks.valueOf ( jsonObject.get ( "Размер" ).getAsString () ) );
        socks.setSocksOfComposition (jsonObject.get ( "Содержание хлопка" ).getAsInt () );
        socks.setQuantity ( jsonObject.get ( "Количество" ).getAsInt () );

        return socks;

    }
}
