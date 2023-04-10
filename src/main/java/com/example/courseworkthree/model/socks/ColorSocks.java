package com.example.courseworkthree.model.socks;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;



public enum ColorSocks {
    @SerializedName("КРАСНЫЙ")
    @JsonProperty("КРАСНЫЙ")
    RED,
    @SerializedName("ЗЕЛЕНЫЙ")
    @JsonProperty("ЗЕЛЕНЫЙ")
    GREEN,
    @SerializedName("СИНИЙ")
    @JsonProperty("СИНИЙ")
    BLUE,
    @SerializedName("ЖЕЛТЫЙ")
    @JsonProperty("ЖЕЛТЫЙ")
    YELLOW,
    @SerializedName("ЧЕРНЫЙ")
    @JsonProperty("ЧЕРНЫЙ")
    BLACK,
    @SerializedName("СЕРЫЙ")
    @JsonProperty("СЕРЫЙ")
    GRAY,
    @SerializedName("БЕЛЫЙ")
    @JsonProperty("БЕЛЫЙ")
    WHITE;

}
