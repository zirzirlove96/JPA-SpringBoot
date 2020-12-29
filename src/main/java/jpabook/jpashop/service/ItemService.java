package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //변경 감지
    @Transactional //Transactional이 변경감지를 하여 수정된 정보를 update query문에 넣어준다.
    public void updateItem(Long ItemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(ItemId);//영속성 컨텍스트 findItem

        findItem.setName(name);//변경 감지
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        //영속성이므로 save해 줄 필요 없다.
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
