package jpabook.jpashop.controller;

import jpabook.jpashop.domain.BookForm;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /*상품 등록*/
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());//빈 객체를 넘김으로써
        //html의 필드를 추적할 수 있다.
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setISBN(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";// @GetMapping("/items")를 수행시킨다.
    }

    /*주문 목록*/
    @GetMapping("/items")
    public String list(Model model){
        List<Item> itemList = itemService.findAll();
        model.addAttribute("items",itemList);
        return "items/itemList";//itemList.html로 객체를 뿌려준다.
    }


    /*주문 수정*/
    @GetMapping("items/{itemsId}/edit")
    public String updateItemForm(@PathVariable("itemsId")Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getISBN());

        model.addAttribute("form",form);//form데이터를 화면에 보여준다.
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemsId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form){
        //updateItemForm.html에서 가져올 객체를 BookForm을 이용하여 사용한다.

        /**
        Book book = new Book();
        book.setISBN(form.getIsbn());
        //html에 수정한 입력값들을 book에 적용시킨다.
        book.setAuthor(form.getAuthor());
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        itemService.saveItem(book);
         **/

        //이게 더 훨씬 나은 코드이다.
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";//주문목록으로 이동
    }

}
