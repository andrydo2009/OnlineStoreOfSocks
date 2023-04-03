package com.example.courseworkthree.controller;


import com.example.courseworkthree.model.socks.Socks;
import com.example.courseworkthree.service.SocksFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@RestController
@RequestMapping("/file/socks")
@Tag(name = " Работа с файлами ", description = " Операции с файлами носки ")
public class SocksFileController {

    private final SocksFileService socksFileService;


    public SocksFileController(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;

    }

    @GetMapping("/export")//экспорт данных в текущем состоянии
    @Operation(
            summary = "Скачивание файла",
            description = "Скачиваем из приложения список всех носков в JSON формате"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Загрузка файла прошла успешно",
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
                            description = "Загрузка файла не удалась"
                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )

            }
    )
    public ResponseEntity<InputStreamResource> downloadSocksFile() throws FileNotFoundException {
        File loadFile = socksFileService.getDataSocksFile ();
        if (loadFile.exists ())//проверяем что файл существует
        {
            InputStreamResource resource = new InputStreamResource ( new FileInputStream ( loadFile ) );
            // FileInputStream открываем поток
            return ResponseEntity.ok ()
                    //.contentType(MediaType.APPLICATION_OCTET_STREAM)//указываем что передаем поток байт
                    .contentType ( MediaType.APPLICATION_JSON )//указываем что передаем поток байт
                    .contentLength ( loadFile.length () )//указываем длину файла
                    .header ( HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"socks.json\"" )
                    .body ( resource );//возвращаем в теле ответа файл
        } else {
            return ResponseEntity.noContent ().build ();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//импорт данных
    @Operation(
            summary = "Загрузка файла",
            description = "Загружаем в приложение список всех носков в JSON формате"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Загрузка файла прошла успешно",
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
                            description = "Загрузка файла не удалась"
                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )

            }
    )
    public ResponseEntity<Void> uploadSocksFile(@RequestParam MultipartFile file) {
        socksFileService.cleanOperationDataFile ();
        File uploadFile = socksFileService.getOperationDataSocksFile ();
        try (FileOutputStream fos = new FileOutputStream ( uploadFile )) {
            IOUtils.copy ( file.getInputStream () , fos );
            return ResponseEntity.ok ().build ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR ).build ();
    }


    @GetMapping("/report")
    @Operation(
            summary = "Скачивание отчета",
            description = "Скачиваем отчет об операциях с товаром"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Загрузка отчета прошла успешно",
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
                            description = "Загрузка отчета не удалась"
                    ) ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Во время выполнения запроса произошла ошибка на сервере"
                    )

            }
    )
    public ResponseEntity<InputStreamResource> downloadOperationsFile() throws FileNotFoundException { // импорт отчета об операциях
        File loadFile = socksFileService.getOperationDataSocksFile ();
        if (loadFile.exists ()) {
            InputStreamResource resource = new InputStreamResource ( new FileInputStream ( loadFile ) );
            return ResponseEntity.ok ()
                    .contentType ( MediaType.asMediaType ( MediaType.TEXT_PLAIN ) )
                    .contentLength ( loadFile.length () )//указываем длину файла
                    .header ( HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"operations.txt\"" )
                    .body ( resource );//возвращаем в теле ответа файл
        } else {
            return ResponseEntity.noContent ().build ();
        }
    }


}
