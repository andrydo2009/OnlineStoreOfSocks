package com.example.courseworkthree.controller;

import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/socks")
@Tag(name = " Носки ", description = " Операции с носками ")
public class SocksController {

    public SocksService socksService;
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }



    @PostMapping( "/add") //добавить носки на склад
    public ResponseEntity<Socks> addSocksToStore(@RequestBody Socks socks) {
        Socks addNewSocks = socksService.addSocksToList ( socks);
        return ResponseEntity.ok ( addNewSocks );
    }

    @GetMapping("/show")//показать весь список носков на складе
    public ResponseEntity<LinkedList<Socks>> showSocksStore() {
        LinkedList<Socks> socksList = socksService.showSocksList ();
        return ResponseEntity.ok ( socksList);
    }

    @PutMapping("/put")///отпускаем носки со склада
    public ResponseEntity<Socks> editSocksToStore(@RequestBody Socks socks) {
        if (socksService.editSocksList ( socks )) {
            return ResponseEntity.ok (  ).build ();
        }
        return ResponseEntity.notFound ().build ();
    }
}
