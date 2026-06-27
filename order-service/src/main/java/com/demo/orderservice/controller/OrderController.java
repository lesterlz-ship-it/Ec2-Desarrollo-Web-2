package com.demo.orderservice.controller;

import com.demo.orderservice.dto.OrderCreateRequest;
import com.demo.orderservice.dto.OrderDTO;
import com.demo.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operaciones sobre órdenes (incluye comunicación con user-service)")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Listar todas las órdenes",
               description = "Obtiene todas las órdenes y las enriquece con datos del usuario vía user-service")
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @Operation(summary = "Obtener una orden por ID",
               description = "Obtiene una orden y la enriquece con datos del usuario vía user-service")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(summary = "Listar órdenes de un usuario",
               description = "Devuelve todas las órdenes asociadas al userId indicado")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> findByUserId(
            @Parameter(description = "ID del usuario") @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @Operation(summary = "Crear una nueva orden",
               description = "Crea una orden validando primero que el usuario exista en user-service")
    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderCreateRequest request) {
        OrderDTO created = orderService.create(request);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Eliminar una orden")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
