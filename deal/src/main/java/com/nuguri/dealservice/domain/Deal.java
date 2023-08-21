package com.nuguri.dealservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class Deal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL)
    private List<DealHistory> dealHistoryList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL)
    private List<DealFavorite> dealFavoriteList = new ArrayList<>();


    private Long memberId;

    private Long categoryId;

    private Long localId;

    private String title;

    private String description;

    private int price;

    private int hit;

    private boolean isDeal;

    private String dealImage;

    public void  finishDeal(){
        this.isDeal = true;
    }

    public void increaseHit(){
        this.hit+=1;
    }

    public void registDeal(Long localId, String dealImage){
        this.localId = localId;
        this.dealImage = dealImage;
    }

    public void updateDeal(String title, String description, int price, String dealImage){
        this.title = title;
        this.description = description;
        this.price = price;
        this.dealImage = dealImage;
    }
}
