package org.example.orderservice;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private long orderId;
    private long productId;
    private int amount;
    private int price;

}
