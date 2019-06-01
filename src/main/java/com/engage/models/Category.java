package com.engage.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_PARENT")
    private Category parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @JsonIgnoreProperties("parent")
    private List<Category> children = new ArrayList<>();

    protected Category() {
    }

    public int getId() {
        return this.id;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return this.parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void addChild(Category child) {
        children.add(child);
    }

    public void addChildren(List<Category> child) {
        children.addAll(child);
    }

}