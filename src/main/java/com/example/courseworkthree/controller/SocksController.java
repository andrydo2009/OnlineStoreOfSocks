package com.example.courseworkthree.controller;

import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socks")
@Tag(name = " Носки ", description = " Операции с носками ")
public class SocksController {

    public SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }


    @PostMapping("/post") //добавить носки на склад
    @Operation(
            summary = "Заносим товар на склад",
            description = "Пополняем ассортимент нашего склада"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Товар добавлен на склад",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema =
                                            @Schema(implementation = Socks.class))
                                    )
                            }
                    ) ,
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат"

                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )
            }
    )
    public ResponseEntity<Socks> addSocksToStore(@RequestBody Socks socks) {
        Socks addNewSocks = socksService.addSocksToList ( socks );
        return ResponseEntity.ok ( addNewSocks );
    }

    @GetMapping("/list") //показать весь список носков на складе
    @Operation(
            summary = "Полный ассортимент товара",
            description = "Выводим список полного ассортимента товара"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Доступен список товара",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema =
                                            @Schema(implementation = Socks.class))
                                    )
                            }
                    ) ,
                    @ApiResponse(
                            responseCode = "400",
                            description = "Список не содержит товара"

                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )
            }
    )
    public ResponseEntity<Object> showSocksStore() {
        List<Socks> socksList = socksService.showSocksList ();
        if (socksList.size () != 0) {
            return ResponseEntity.ok ( socksList );
        } else return new ResponseEntity<> ( "Список не содержит товара" , HttpStatus.BAD_REQUEST );
    }

    @PutMapping("/put")///отпускаем носки со склада
    @Operation(
            summary = "Списываем товар со склада",
            description = "Списываем необходимое количество товара"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Товар успешно списан со склада",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema =
                                            @Schema(implementation = Socks.class))
                                    )
                            }
                    ) ,
                    @ApiResponse(
                            responseCode = "400",
                            description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"

                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )
            }
    )
    public ResponseEntity<Object> editSocksToStore(@RequestBody Socks socks) {
        if (socksService.editSocksList ( socks )) {
            return ResponseEntity.ok ( socks );
        } else
            return new ResponseEntity<> ( "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат" , HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/get")
    @Operation(
            summary = "Проверяем наличие товара",
            description = "Заполните параметры для поиска товара на складе"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Товар найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema =
                                            @Schema(implementation = Socks.class))
                                    )
                            }
                    ) ,
                    @ApiResponse(
                            responseCode = "404",
                            description = "Товара нет на складе или параметры запроса имеют некорректный формат"

                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )

            }
    )
    public ResponseEntity<Integer> getAvailabilitySokcsToParameter(@RequestParam("Минимальное содержание хлопка")@Parameter(description = "Введите число от 0 до 100") int cottonMin ,
                                                                   @RequestParam("Максимальное содержание хлопка")@Parameter(description = "Введите число от 0 до 100") int cottonMax ,
                                                                   @RequestParam("Цвет носков")@Parameter(description = "Выберете цвет из списка") ColorSocks colorSocks ,
                                                                   @RequestParam("Размер носков")@Parameter(description = "Выберете размер из списка") SizeSocks sizeSocks) {
        int availability = socksService.getSocksAvailability ( cottonMin , cottonMax , colorSocks , sizeSocks );
        if (availability > 0) {
            return ResponseEntity.ok ( availability );
        } else return ResponseEntity.status ( HttpStatus.NOT_FOUND ).build ();
    }
@DeleteMapping("/delete")
@Operation(
        summary = "Удаляем бракованный товар со склада",
        description = "Удаляем необходимое  количество бракованного товара"
)
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Запрос выполнен, товар списан со склада"
                ) ,
                @ApiResponse(
                        responseCode = "400",
                        description = "Параметры запроса отсутствуют или имеют некорректный формат"

                ) ,
                @ApiResponse(
                        responseCode = "500",
                        description = "Во время выполнения запроса произошла ошибка на сервере"
                )
        }
)
public ResponseEntity<Object> deleteSocksToStore(@RequestBody Socks socks) {
    if (socksService.deleteDefectiveSocks ( socks )) {
        return ResponseEntity.ok ( socks );
    } else
        return new ResponseEntity<> ( "Параметры запроса отсутствуют или имеют некорректный формат" , HttpStatus.BAD_REQUEST );
}

}
