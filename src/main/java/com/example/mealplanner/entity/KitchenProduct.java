package com.example.mealplanner.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "kitchen_product")
public class KitchenProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "food_product_id")
    private FoodProduct foodProduct;

    @Column(name = "ammount")
    private float ammount;

    @Column(name = "expiration_date")
    private Date expirationDate;


    public KitchenProduct() {
    }

    public KitchenProduct(User user, FoodProduct foodProduct, float ammount, Date expirationDate) {
        this.user = user;
        this.foodProduct = foodProduct;
        this.ammount = ammount;
        this.expirationDate = expirationDate;
    }
}
