package com.gafarov.bastion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController extends BaseController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
