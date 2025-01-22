package com.giunne.itemservice.domain.item.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.Profile.domain.Profile;
import com.giunne.itemservice.domain.cart.domain.Cart;
import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.item.domain.type.ItemName;
import com.giunne.itemservice.domain.item.domain.type.NeedLevel;
import com.giunne.itemservice.domain.item.domain.type.Price;
import com.giunne.itemservice.domain.item.domain.type.SortSeq;
import com.giunne.itemservice.domain.orders.domain.Order;
import com.giunne.itemservice.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Long id; // 회원 번호

    @Embedded
    private ItemName itemName; // 아이템명

    @Embedded
    private Price price; // 기격

    @Embedded
    private NeedLevel needLevel; // 착용가능 레벨

    @Embedded
    private SortSeq sortSeq; // 순서번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no")
    private Store store; // 상점

    @Embedded
    private Active isActive = Active.from(true);

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Profile> profiles = new HashSet<>();  // 프로필

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>(); // 주문

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>(); // 장바구니

}

