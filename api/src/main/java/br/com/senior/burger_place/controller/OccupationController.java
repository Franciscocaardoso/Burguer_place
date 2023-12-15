package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.occupation.OccupationService;
import br.com.senior.burger_place.domain.occupation.OrderItemStatus;
import br.com.senior.burger_place.domain.occupation.dto.CreateOccupationData;
import br.com.senior.burger_place.domain.occupation.dto.OccupationData;
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
@RequestMapping("occupations")
public class OccupationController {
    @Autowired
    private OccupationService occupationService;

    @GetMapping
    public ResponseEntity listOccupations(Pageable pageable) {
        return ResponseEntity.ok(this.occupationService.listOccupations(pageable));
    }

    @GetMapping("/{occupationId}")
    public ResponseEntity showOccupation(
            @PathVariable
            Long occupationId
    ) {
        Optional<OccupationData> orderOptional = this.occupationService.showOccupation(occupationId);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderOptional.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createOccupation(
            @RequestBody
            @Valid
            CreateOccupationData orderData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        OccupationData occupationData = this.occupationService.createOccupation(orderData);

        URI uri = uriComponentsBuilder
                .path("/occupations/{occupationId}")
                .buildAndExpand(occupationData.id())
                .toUri();

        return ResponseEntity.created(uri).body(occupationData);
    }
//
//    @PostMapping("/{orderId}/items")
//    @Transactional
//    public ResponseEntity addOrderItems(
//            @PathVariable
//            Long orderId,
//            @RequestBody
//            @Valid
//            AddNewOrderItemsData orderItemData
//    ) {
//        this.orderService.addOrderItemToOrder(orderId, orderItemData);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{orderId}/items")
//    @Transactional
//    public ResponseEntity removeOrderItems(
//            @PathVariable
//            Long orderId,
//            @RequestBody
//            @Valid
//            RemoveOrderItemsData orderItemData
//    ) {
//        this.orderService.removeOrderItems(orderId, orderItemData);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{orderId}/items/{itemId}")
//    @Transactional
//    public ResponseEntity removeOrderItems(
//            @PathVariable
//            Long orderId,
//            @PathVariable
//            Long itemId
//    ) {
//        this.orderService.removeOrderItemFromOrder(orderId, itemId);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{orderId}/items/{itemId}")
//    @Transactional
//    public ResponseEntity updateOrder(
//            @PathVariable
//            Long orderId,
//            @PathVariable
//            Long itemId,
//            @RequestBody
//            UpdateOrderItemData orderItemData
//    ) {
//        this.orderService.updateOrderItem(orderId, itemId, orderItemData);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{orderId}")
//    @Transactional
//    public ResponseEntity inactivateOrder(
//            @PathVariable
//            Long orderId
//    ) {
//        this.orderService.inactivateOrder(orderId);
//
//        return ResponseEntity.noContent().build();
//    }
}
