package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberForm;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value="/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        //createMemberform.html에 MemberFrom()이라는 빈 객체를 model에 memberForm에 넣어서 전달
        return "members/createMemberForm";
    }

    @PostMapping(value="/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
            //@Valid는 MemberForm에서 에노테이션한 @NotEmpty같은 것을 꼭 하게 만든다.
            //BindingResult는 MemberForm에 입력된 값이 잘못 입력되었을 경우 그 정보를 담아 실행되어
            //MemberForm객체의 @NotEmpty가 실행되어 createMEmberForm.html에 나타난다.
        if(result.hasErrors()){
            return "members/createMemberForm";//BidingResult가 이 페이지로 넘긴다.
        }

        //주소값을 생성
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipCode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);//저장

        return "redirect:/";//createMemberForm.html로 넘어간다.
    }

    /*회원 목록*/
    @GetMapping(value="/members")
    public String list(Model model){//model이라는 객체를 사용하여 화면에 나타내 준다.

        List<Member> memberList = memberService.findMembers();

        model.addAttribute("members",memberList);
        return "members/memberList";

    }

}
