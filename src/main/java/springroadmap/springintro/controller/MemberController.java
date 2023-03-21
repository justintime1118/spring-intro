package springroadmap.springintro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springroadmap.springintro.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("mappedUrl") //url과 메소드를 매핑시켜줌
    public String hello(Model model) {
        // Model 객체에 값을 담아서 HTML로 그 값을 넘겨준다
        model.addAttribute("dynamicallyGeneratedAttribute", "attributeValue");
        return "templateFileName"; // return value + .html 파일을 찾아서 화면을 띄워준다
    }

    @GetMapping("urlWithParameter")
    // http request로 받아온 값을 httpParam이라는 매개변수에 담아서 받아온 뒤 Model에 반영해서 View로 넘겨준다
    // ex) http://localhost:8080/urlWithParameter?requestParam=fella!
    public String helloMvc(@RequestParam("requestParam") String receivedParameter, Model model) {
        //key name: dynamicallyGeneratedAttribute, value: httpParameter
        model.addAttribute("dynamicallyGeneratedAttribute", receivedParameter);
        return "hello-mvc";
    }

    @GetMapping("apiUrl")
    @ResponseBody // 템플릿 html 파일을 찾지 않고, httpMessageConverter에 return 값을 넘겨서 http body에 해당 값을 담아준다
    public CustomObject helloApi(@RequestParam("requestParam") String receivedParameter) {
        CustomObject customObject = new CustomObject();
        customObject.setJsonKey(receivedParameter);
        return customObject; // return value의 datatype이 객체일 경우, default로 JsonConverter가 해당 객체를 처리
    }

    static class CustomObject {
        String jsonKey;

        public String getJsonKey() {
            return jsonKey;
        }

        public void setJsonKey(String jsonKey) {
            this.jsonKey = jsonKey;
        }
    }
}
