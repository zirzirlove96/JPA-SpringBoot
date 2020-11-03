package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //Order입장에서 Member는 다대일 관계
    @JoinColumn(name="member_id")//fk를 정해 주는 것 외래키는 다에서 정해준다.
    //연관관계의 주인이라고 표시한 것이다. member의 값이 변경될 경우 fk값이 변경된다.
    private Member member;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)//OrderItem과의 매핑관계를 나타내고 있다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="delibery_id")//일대일 관계에서 외래키를 지정할 때는 더 많이
    //접근하고 사용할 것 같은 테이블에 설정하는 것이 좋다.
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문 상태[order,cancle]

    //==연관관계 메서드
    public void setMember(Member member){
        this.member=member;
        member.getOrderList().add(this);
    }

    public void addOrderItems(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //주문을 생성하는 메서드
    //주문을 생성하는 것을 바꿀 때 이 메서드만 바꿔주면 되므로 생성시키는 것이 좋다
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem:orderItems){
            order.addOrderItems(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직 중에서 주문 취소
    public void cancle() {
        if (delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송된 상품을 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCLE);//그 외에 주문 상품 취소
        for(OrderItem orderItem:orderItems){
            orderItem.cancle();//orderItem에서도 취소를 해줘야 한다.
        }
    }

    //비즈니스 로직중에서 조직 로직 전체 주문 가격을 조회
    public int getTotalPrice() {
        int totalPrice=0;
        for(OrderItem orderItem:orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
