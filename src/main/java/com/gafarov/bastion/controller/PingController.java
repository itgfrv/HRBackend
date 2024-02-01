package com.gafarov.bastion.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController

public class PingController extends BaseController {
    @GetMapping("/ping")
    public String ping() {
        return "pong pong pong";
    }
}
