package com.crossasyst.msgproducer.controller;

import com.crossasyst.msgproducer.model.UserModel;
import com.crossasyst.msgproducer.model.UserResponse;
import com.crossasyst.msgproducer.service.MSGService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class Controller {

    @Autowired
    private MSGService msgService;

    @PostMapping(path = "users",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

    private ResponseEntity<UserResponse>createDate(@RequestBody UserModel userModel){
        UserResponse userResponse=msgService.createUser(userModel);
        log.info("UserDetail Posted Successfully with Id "+userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
