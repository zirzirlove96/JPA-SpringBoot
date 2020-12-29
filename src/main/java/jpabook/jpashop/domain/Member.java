package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입을 포함시킨다.
    private Address address;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY) //Member입장에서 Order은 일대다 관계이다.
    //mappedBy는 연관관계에서 거울 역할을 하는 것으로 주인인 Order 테이블의 member를 나타낸다.
    //거울이므로 변수에 값이 들어가더라도 값은 변경되지 않는다.
    private List<Order> orderList = new ArrayList<Order>();


}
