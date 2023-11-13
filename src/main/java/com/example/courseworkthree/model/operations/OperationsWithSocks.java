package com.example.courseworkthree.model.operations;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsWithSocks {

    @SerializedName("ТИП ОПЕРАЦИИ")
    @JsonProperty("ТИП ОПЕРАЦИИ")
    private TypeOperationWithSocks typeOperation;

    @SerializedName("ВРЕМЯ ОПЕРАЦИИ")
    @JsonProperty("ВРЕМЯ ОПЕРАЦИИ")
    private  String dateOperation;
}
