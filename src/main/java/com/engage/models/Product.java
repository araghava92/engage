package com.engage.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String UPC;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    protected Product() {
    }

    public Product(String name, String upc, BigDecimal price,
                   Category category) {
        this.name = name;
        this.UPC = upc;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUPC() {
        return this.UPC;
    }

    public String setUPC(String UPC) {
        return this.UPC = UPC;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category geCategory() {
        return this.category;
    }
}


