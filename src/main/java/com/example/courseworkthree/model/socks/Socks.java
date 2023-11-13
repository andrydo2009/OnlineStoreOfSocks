package com.example.courseworkthree.model.socks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks  {

    @SerializedName ( "Цвет" )
    @JsonProperty("Цвет")
    private ColorSocks colorSocks;

    @SerializedName ( "Размер" )
    @JsonProperty("Размер")
    private SizeSocks sizeSocks;

    @SerializedName ( "Содержание хлопка" )
    @JsonProperty("Содержание хлопка" )
    private int socksOfComposition;

    @SerializedName ( "Количество" )
    @JsonProperty("Количество")
    private int quantity;
}
