package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class) //테스트를 위해 두개가 필요로 하다.
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void join() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();//영속성이 쿼리에 반영된다. 이전에는 @Transactional때문에 안됨
        assertEquals(member, memberRepository.findOne(savedId));//저장된 것이 같은 지 확인
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);
//        exception이 발생할 경우 expected=IllegalStateException.class가 예외를 잡아서 처리해 준다.
        /*
        try{
            memberService.join(member2);//같은 이름이 들어감
        }catch (IllegalStateException e){
            return ;
        }
        */


        //then
        fail("예외가 발생해야 한다.");//중복예외가 발생하지 않고 밖으로 exception이 나갈때


    }

}