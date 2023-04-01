package com.example.courseworkthree.model.operations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsWithSocks {

    @SerializedName("ТИП ОПЕРАЦИИ")
    private TypeOperationWithSocks typeOperation;

    @SerializedName("ВРЕМЯ ОПЕРАЦИИ")
    private  String dateOperation;

}
