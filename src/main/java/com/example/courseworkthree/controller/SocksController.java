package com.example.courseworkthree.controller;

import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/socks")
@Tag(name = " Носки ", description = " Операции с носками ")
public class SocksController {

    public SocksService socksService;
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }



    @PostMapping( "/add")
    public ResponseEntity<Object> addSocks(@RequestBody Socks socks ) {
        Socks addNewSocks = socksService.addSocksToMap ( socks);
        return ResponseEntity.ok ( addNewSocks );
    }

    @GetMapping("/show")
    public ResponseEntity<Map<Socks, Integer>> showSocksList() {
        Map<Socks, Integer> socksMap = socksService.showSocksList ();
        return ResponseEntity.ok ( socksMap);
    }


}
