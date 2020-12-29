package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    //1. 상품 주문 2. 주문 취소 3. 재고 수량 초과
    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        //when 책 2권을 주문한다.
        Long orderId=orderService.order(member.getId(),book.getId(),2);

        //then 검증
        Order getOrder = orderRepository.findOne(orderId);

        //message, expected, actual
        assertEquals("상품 주문 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격*수량이다",10000*2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8,book.getStockQuantity());
    }



    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);//ctrl+alt+p

        Long orderId=orderService.order(member.getId(),item.getId(),1);

        //when
        orderService.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);//저장된 값을 가져온다.

        assertEquals("주문 취소시 상태는 CANCLE",OrderStatus.CANCLE,getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 재고가 증가해야 한다.",10,item.getStockQuantity());

        
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
   //예외처리
        //given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);//ctrl+alt+p

        int orderCount=11;

        //when
        orderService.order(member.getId(), item.getId(),orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    //ctrl+alt+m
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("김지영");
        member.setAddress(new Address("부천","중동로","123-123"));
        em.persist(member);
        return member;
    }

}