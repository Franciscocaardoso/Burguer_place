package br.com.senior.delivery.controller;

import br.com.senior.delivery.domain.order.OrderService;
import br.com.senior.delivery.domain.order.dto.CreateOrderData;
import br.com.senior.delivery.domain.order.dto.OrderData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity listOrders(Pageable pageable) {
        return ResponseEntity.ok(this.orderService.getOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity showOrder(
            @PathVariable
            Long id
    ) {
        Optional<OrderData> orderOptional = this.orderService.showOrder(id);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderOptional.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createOrder(
            @RequestBody
            @Valid
            CreateOrderData orderData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        OrderData order = this.orderService.createOrder(orderData);

        URI uri = uriComponentsBuilder
                .path("/pets/{id}")
                .buildAndExpand(order.id())
                .toUri();

        return ResponseEntity.created(uri).body(order);
    }
////
//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity updateOrder() {}
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity deleteOrder() {}
//
//    @PatchMapping("/{id}")
//    @Transactional
//    public ResponseEntity cancelOrder() {}
}
