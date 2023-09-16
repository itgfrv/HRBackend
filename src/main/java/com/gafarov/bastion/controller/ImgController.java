package com.gafarov.bastion.controller;

import com.gafarov.bastion.service.impl.ImgService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/img")
@AllArgsConstructor
@CrossOrigin(maxAge = 360000)
public class ImgController {
    @Autowired
    private ImgService imgService;
    @CrossOrigin
    @GetMapping(value = "/{file}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] downloadImg(@PathVariable String file) throws Exception {
        return imgService.getImageObject(file);
    }
}
