package jpabook.jpashop.domain.Item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //상속관계에서 Item에 모든 자식들을 집어넣는다.
//싱글테이블
@DiscriminatorColumn(name="dtype") //싱글패턴에서 자식 테이블의 정보를 구분하기 위해 @DiscriminatorValue()과 같이 쓰인다.
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy="itemList")
    private List<Category> categoryList = new ArrayList<Category>();

    //stock 증감소를 하는 역할을 도메인 영역에 넣는 이유 : 데이터를 가지고 있는 곳에 비지니스를 넣는게 낫다.
    /*stock 증가*/
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    //객체지향적으로 해결함.
    /*stock 감소*/
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");//exception처리
        }
        this.stockQuantity=restStock;
    }


}
