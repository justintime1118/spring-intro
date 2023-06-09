package springroadmap.springintro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springroadmap.springintro.domain.Member;
import springroadmap.springintro.domain.MemberForm;
import springroadmap.springintro.service.MemberService;

import java.util.List;

@Controller
// Controller, Service, Repository 어노테이션 모두 @Component를 갖고있기에 모두 컴포넌트 스캔 대상이 되어 스프링 빈으로 등록된다.
// 기본적으로 Singleton으로 동작 (하나의 스프링 빈만 생성된다. 메모리 절약 등 장점이 있다)
public class MemberController {
    @Autowired // 스프링 컨테이너에 들어있는 스프링 빈 중에서 찾아서 자동으로 의존성을 주입해준다 (Dependency Injection)
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

    @GetMapping("/members/new") // form 입력화면으로 연결시켜줌
    public String createForm() {
        return "members/createMemberForm"; // 이 경로에 해당하는 html 파일이 브라우저에 렌더링 됨
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) { // 입력한 form을 이용해서 적합한 서비스를 호출함
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 회원가입 처리를 한 뒤에 초기화면으로 redirect 시킴
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList"; // 여기서부터 본격적으로 템플릿 엔진의 도움을 많이 받게 된다
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
