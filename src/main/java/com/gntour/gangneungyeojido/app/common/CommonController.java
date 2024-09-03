package com.gntour.gangneungyeojido.app.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/common/unauthorized")
    public String errorUnauthorized() {
        return "common/error401";
    }
}
