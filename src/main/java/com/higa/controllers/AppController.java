package com.higa.controllers;

import com.higa.models.ReqBody;
import com.higa.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Value("${spring.application.name}")
    private String appName;

    private final AppService appService;

    @Autowired
    public AppController(AppService appService){
        this.appService = appService;
    }

    @GetMapping
    public String isAppAlive(){ return appName + " : I'm alive and running! :)"; }

    @PostMapping
    public ResponseEntity<String> postImageToQueue(@RequestBody ReqBody reqBody){
        return appService.insertImgIntoQueue(reqBody);
    }
}
