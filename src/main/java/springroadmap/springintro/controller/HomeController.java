package springroadmap.springintro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 컨트롤러가 정적 파일보다 우선순위가 높으므로 index.html은 무시되고 이 컨트롤러의 메서드와 매핑된다.
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
