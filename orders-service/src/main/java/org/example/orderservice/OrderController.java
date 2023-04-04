package org.example.orderservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController{
    private final OrderService orderService;
    @GetMapping("all")
    public List<Order> all(){
        return orderService.findAll();
    }
    @PostMapping("create/retry")
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory") // retry 3 times and wait 5s (configured at application.yml)
    public Order createRetry(@RequestBody Order order) throws Exception {
        return orderService.save(order);
    }
    @PostMapping("create")
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    public Order createSync(@RequestBody Order order) throws Exception {
        return orderService.save(order);
    }
    @PostMapping("create/async")
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory") // waits 3 seconds (configured at application.yml) otherwise throws a timeout exception
    public CompletableFuture<Order> createAsync(@RequestBody Order order) {
        // runs in another thread
       return CompletableFuture.supplyAsync(()-> {
           try {
               return orderService.save(order);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       });
    }
    public CompletableFuture<Order> fallbackMethod(Order order, Exception e){
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Ups something went wrong");
    }
}
