package org.example.orderservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final WebClient.Builder webClientBuilder;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) throws Exception {
        // call inventory service and place order if product is in stock
        List<String> notInInventory = new ArrayList<>();
        String allInStock = webClientBuilder.build()
                .post()
                .uri("http://inventory/inventory/check")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(order.getDetails()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if (allInStock != null && !allInStock.isEmpty()){
            log.info(allInStock);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "there is not inventory of: " + String.join(",", notInInventory),
                    new Exception("there is not inventory of: " + String.join(",", notInInventory))
            );
        }
        List<OrderDetail> details = order.getDetails();
        order.setDetails(null);
        long id = orderRepository.save(order).getId();
        details = details.stream().peek(d-> d.setOrderId(id)).collect(Collectors.toList());
        orderDetailRepository.saveAll(details) ;
        return order;
    }
}
