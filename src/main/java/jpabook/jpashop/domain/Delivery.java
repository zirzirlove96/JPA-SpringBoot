package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy="delivery",fetch = FetchType.LAZY)//연관관계에서 거울 역할
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //enum타입의 객체를 사용할 때 사용해줘야 한다.
    //ORDINAL은 숫자타입으로 숫자로 지정해주면 지정한 타입외에 다른 enum타입이 들어갈 경우
    //오류가 발생할 확률이 크기 때문에 STRING으로 꼭 지정해 준다.
    private DeliveryStatus status;
}
