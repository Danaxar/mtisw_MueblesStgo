package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService upload;

    @GetMapping("/datatxt")
    public String upload(){
        return "Upload";
    }

    @GetMapping("/home")
    public String firstPage(){
        return "index"; // index.html
    }

    @PostMapping("/load")
    public String load(@RequestParam("archivos")MultipartFile file, RedirectAttributes ms){
        upload.save(file);
        ms.addAttribute("mensaje", "File saved correctly");
        return "redirect:/";
    }
}
