package org.example.orderservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController{
    private final OrderService orderService;
    @GetMapping("all")
    public List<Order> all(){
        return orderService.findAll();
    }
    @PostMapping("create")
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    public Order create(@RequestBody Order order) throws Exception {
       return orderService.save(order);
    }
    public Order fallbackMethod(Order order, Exception e){
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Ups something went wrong");
    }
}
