package com.example.courseworkthree.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// аннотация содержит все необходимые методы для создания объекта класса(equal,toString and ...)
@AllArgsConstructor
// аннотация создания конструктора со всеми параметрами
@NoArgsConstructor
//автоматическая генерация не параметризованного конструктора
public class Socks {

    private ColorSocks colorSocks;   // цвет носков(перечисление)
    private SizeSocks sizeSocks;     // размер носков(перечисление)
    private int socksOfComposition; // состав хлопка в носках в % соотношении от 0 до 100


}
