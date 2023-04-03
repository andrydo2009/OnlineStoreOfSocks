package com.example.courseworkthree.model.operations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public enum TypeOperationWithSocks {
    @SerializedName("СПИСАНИЕ ТОВАРА")
    @JsonProperty("СПИСАНИЕ ТОВАРА")
    EXTRADITION_SOCKS,
    @SerializedName("ПРИЕМ ТОВАРА")
    @JsonProperty("ПРИЕМ ТОВАРА")
    ACCEPTANCE_SOCKS
}
