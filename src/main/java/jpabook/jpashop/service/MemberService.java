package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //service부분에서 데이터를 사용하는 구간이여서 꼭 필요로 하다.
public class MemberService {

//    필드 injection
    //@Autowired -> final이 대신 해 준다.
    private final MemberRepository memberRepository;

    //생성자에서 injection이 이루어 진다. 중간에 바꿀 수 없고, 테스트 케이스를 사용할 때
    // 직접 주입이기 때문에 명확하게 사용할 것을 알 수 있다.
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }


    @Transactional
    public Long join(Member member){
        validateDuplcated(member);//id중복 검사
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplcated(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){//예외처리를 해준다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
