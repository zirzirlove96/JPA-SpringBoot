package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    //상품 주문
    //Item, Member, Order
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;

    //상품 주문
    @GetMapping("/order")
    public String createOrder(Model model){
        List<Member> memberList = memberService.findMembers();
        List<Item> itemList = itemService.findAll();

        model.addAttribute("members", memberList);
        model.addAttribute("items",itemList);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId")
                        Long itemId, @RequestParam("count") int count){
        //orderForm.html에 있는 입력값들을 @RequestParam을 이용하면 바인딩되어 데이터를 가져오게 된다.

        orderService.order(memberId, itemId, count);
        //controller에서 직접 찾는 것보다는 복잡하지 않고, 간단하므로 service에서 찾는 수행을 하게 된다.
        return "redirect:/orders";

    }

    /**주문 목록**/
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch,Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        //model.addAttribute("orderSearch",orderSearch); @ModelAttribute가 대신 해준다.
        //@ModelAttribute는 보낼 수도 있고 담을 수도 있다.
        return "order/orderList";

    }

    /**주문 취소,검색**/
    @PostMapping("/orders/{orderId}/cancel")
    public String cancleOrder(@PathVariable("orderId")Long orderId){
        orderService.cancleOrder(orderId);
        return "redirect:/orders";
    }



}
