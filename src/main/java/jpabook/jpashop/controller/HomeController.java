package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //Log를 찍어보자.
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");//Slf4j 덕분에 log라는 객체를 사용할 수 있다.
        return "home";
    }
}
