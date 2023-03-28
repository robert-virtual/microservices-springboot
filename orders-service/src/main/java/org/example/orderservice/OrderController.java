package org.example.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController{
    private final OrderRepository orderRepository;
    @GetMapping("all")
    public List<Order> all(){
        return orderRepository.findAll();
    }
    @PostMapping("create")
    public Order create(@RequestBody Order order){
       return orderRepository.save(order);
    }
}
