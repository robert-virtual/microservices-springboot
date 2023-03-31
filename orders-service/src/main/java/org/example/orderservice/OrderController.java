package org.example.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController{
    private final OrderService orderService;
    @GetMapping("all")
    public List<Order> all(){
        return orderService.findAll();
    }
    @PostMapping("create")
    public Order create(@RequestBody Order order) throws Exception {
       return orderService.save(order);
    }
}
