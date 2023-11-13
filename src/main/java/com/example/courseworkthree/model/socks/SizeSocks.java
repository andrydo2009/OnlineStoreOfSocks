package com.example.courseworkthree.model.socks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public enum SizeSocks {
    @SerializedName("25")
    @JsonProperty("25")
    S25,
    @SerializedName("27")
    @JsonProperty("27")
    S27,
    @SerializedName("30")
    @JsonProperty("30")
    S30,
    @SerializedName("32")
    @JsonProperty("32")
    S32,
    @SerializedName("34")
    @JsonProperty("34")
    S34,
    @SerializedName("36")
    @JsonProperty("36")
    S36,
    @SerializedName("40")
    @JsonProperty("40")
    S40,
    @SerializedName("42")
    @JsonProperty("42")
    S42,
    @SerializedName("44")
    @JsonProperty("44")
    S44,
    @SerializedName("50")
    @JsonProperty("50")
    S50,
    @SerializedName("52")
    @JsonProperty("52")
    S52;
}
