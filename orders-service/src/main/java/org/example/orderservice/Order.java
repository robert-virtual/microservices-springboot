package org.example.orderservice;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long CustomerId;
    private LocalDateTime createAt;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderDetail> details;

}
