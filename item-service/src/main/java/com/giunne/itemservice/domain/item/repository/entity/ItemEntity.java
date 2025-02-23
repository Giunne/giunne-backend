package com.giunne.itemservice.domain.item.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.commonservice.domain.item.ItemGrade;
import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.item.domain.type.*;
import com.giunne.itemservice.domain.store.repository.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Long id;

    @Embedded
    private ItemName itemName; // 아이템명

    @Embedded
    private ItemDescription itemDescription;

    @Embedded
    private Price price; // 기격

    @Embedded
    private NeedLevel needLevel; // 착용가능 레벨

    @Embedded
    private SortSeq sortSeq; // 순서번호

    @Embedded
    private Stock stock; // 재고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private CategoryEntity category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no")
    private StoreEntity store; // 상점

    @Embedded
    private Active isActive = Active.from(true);

    @Enumerated(EnumType.STRING)
    @Column(name = "item_grad", nullable = false)
    private ItemGrade itemGrade;



//
//    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
//    private Set<Profile> profiles = new HashSet<>();  // 프로필

//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<Order> orders = new HashSet<>(); // 주문

//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<Cart> carts = new HashSet<>(); // 장바구니

    public ItemEntity(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.price = item.getPrice();
        this.needLevel = item.getNeedLevel();
        this.sortSeq = item.getSortSeq();
        this.stock = item.getStock();
        this.category = new CategoryEntity(item.getCategory());
        this.store = new StoreEntity(item.getStore());
        this.itemGrade = item.getItemGrade();
    }

    public Item toItem(){
        return Item.builder()
                .id(id)
                .itemName(itemName)
                .itemDescription(itemDescription)
                .price(price)
                .needLevel(needLevel)
                .sortSeq(sortSeq)
                .stock(stock)
                .category(category.toCategory())
                .store(store.toStore())
                .itemGrade(itemGrade)
                .build();
    }

}

