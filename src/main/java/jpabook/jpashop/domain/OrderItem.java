package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;//주문당시의 가격

    private int count;//주문당시의 수량

    //protected OrderItem() {}


    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);//사는 만큼 재고를 줄어들게 한다.
        return orderItem;
    }

    //비즈니스 로직
    public void cancle() {
        getItem().addStock(count);//item 재고수량을 취소한 만큼 원복해준다.
    }

    //주문가격*수량
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
