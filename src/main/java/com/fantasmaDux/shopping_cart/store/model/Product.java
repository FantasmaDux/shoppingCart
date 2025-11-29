package com.fantasmaDux.shopping_cart.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String brand;
    private BigDecimal price; // Точность операций
    private int inventory; // Количество на складе, чтобы не путать с заказанным количеством
    private String description;

    // Категория и продукт не зависят друг от друга
    // Пишем all, так как это отношение должно удалиться при удалении
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // когда продукт будет удален, все связанные с ним image тоже будут удалены
    // CascadeType.REMOVE удаляет дочерние entity только когда удаляют родительскую
    // orphanRemoval = true удаляет дочерние entity когда их убирают из коллекции
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;


    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
