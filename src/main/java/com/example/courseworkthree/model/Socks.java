package com.example.courseworkthree.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data// аннотация содержит все необходимые методы для создания объекта класса(equal,toString and ...)
@AllArgsConstructor// аннотация создания конструктора со всеми параметрами
@NoArgsConstructor//автоматическая генерация не параметризованного конструктора
public class Socks {

    @SerializedName ( "Цвет" )
    @JsonProperty("Цвет")
    private ColorSocks colorSocks;   // цвет носков(перечисление)

    @SerializedName ( "Размер" )
    @JsonProperty("Размер")
    private SizeSocks sizeSocks;     // размер носков(перечисление)

    @SerializedName ( "Содержание хлопка" )
    @JsonProperty("Содержание хлопка" )
    private int socksOfComposition; // состав хлопка в носках в % соотношении от 0 до 100

    @SerializedName ( "Количество" )
    @JsonProperty("Количество")
    private int quantity; //количество носков на складе
}
