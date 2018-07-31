package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private List<CategoryAttribute> attributes;

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CategoryAttribute> attributes) {
        this.attributes = attributes;
    }
}
