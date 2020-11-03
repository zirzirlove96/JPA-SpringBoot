package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext//entityManager를 주입해 주는 에노테이션
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);//pk member_id가 자동으로 생성되어 있다.
    }

    public Member findOne(Long id){//단건조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
//        return List<Member> result = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
        return em.createQuery("select m from Member m", Member.class).getResultList();
//        jpql사용해 준다. sql과 기능적으로 비슷하지만 jpql은 엔티티 객체를 대상으로 한다.
    }

    public List<Member> findByName(String name){
//       m.name=:name 파라미터로 들어온 name을 넣어주는 것
//       setParameter로 지정해준다.
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
