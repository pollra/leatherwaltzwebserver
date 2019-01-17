package com.pollra.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainContorller {
/**
 * 컨트롤러는 무조건 방향 지시만.
 * 기능은 서비스에서 모두 처리.
 */
    public String mainPage(){
        return "index";
    }

    @GetMapping("login")
    public String testPage(){
        return "login";
    }

}
