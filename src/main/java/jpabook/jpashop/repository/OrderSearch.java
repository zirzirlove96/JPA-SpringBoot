package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
public class OrderSearch {

    //query문에 파라미터가 있으면 JPA가 검색조건으로 사용한다.
    private String memberName;
    private OrderStatus orderStatus; //CANCLE, ORDER
}
