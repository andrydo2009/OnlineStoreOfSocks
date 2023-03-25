package com.example.courseworkthree.controller;

import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Map;

@RestController
@RequestMapping("/socks")
@Tag(name = " Носки ", description = " Операции с носками ")
public class SocksController {

    public SocksService socksService;
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }



    @PostMapping( "/add") //добавить носки на склад
    public ResponseEntity<Socks> addSocksToList(@RequestBody Socks socks) {
        Socks addNewSocks = socksService.addSocksToList ( socks);
        return ResponseEntity.ok ( addNewSocks );
    }

    @GetMapping("/show")
    public ResponseEntity<LinkedList<Socks>> showSocksList() {
        LinkedList<Socks> socksList = socksService.showSocksList ();
        return ResponseEntity.ok ( socksList);
    }

    @PutMapping("/put")///отпускаем носки со склада
    public ResponseEntity<Socks> editSocksToList(@RequestBody Socks socks)
    {
        Socks editSocks = socksService.editSocksList ( socks );
        return ResponseEntity.ok ( editSocks );
    }

}
