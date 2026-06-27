package com.demo.orderservice.service;

import com.demo.orderservice.client.UserServiceClient;
import com.demo.orderservice.dto.OrderCreateRequest;
import com.demo.orderservice.dto.OrderDTO;
import com.demo.orderservice.dto.UserSummaryDTO;
import com.demo.orderservice.entity.Order;
import com.demo.orderservice.exception.ResourceNotFoundException;
import com.demo.orderservice.exception.UserServiceException;
import com.demo.orderservice.mapper.OrderMapper;
import com.demo.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceClient userServiceClient;

    public List<OrderDTO> findAll() {
        log.info("Listando todas las órdenes");
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> dtos = orderMapper.toDTOList(orders);
        dtos.forEach(this::enrichWithUser);
        return dtos;
    }

    public OrderDTO findById(Long id) {
        log.info("Buscando orden {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden", id));
        OrderDTO dto = orderMapper.toDTO(order);
        enrichWithUser(dto);
        return dto;
    }

    public List<OrderDTO> findByUserId(Long userId) {
        log.info("Listando órdenes del usuario {}", userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<OrderDTO> dtos = orderMapper.toDTOList(orders);
        dtos.forEach(this::enrichWithUser);
        return dtos;
    }

    public OrderDTO create(OrderCreateRequest request) {
        log.info("Creando orden para usuario {} - producto {}", request.getUserId(), request.getProduct());

        // 1) Validar que el usuario existe consultando al user-service vía Feign
        verifyUserExists(request.getUserId());

        // 2) Crear la orden localmente
        Order order = orderMapper.toEntity(request);
        Order saved = orderRepository.save(order);

        // 3) Devolver el DTO ya enriquecido con datos del usuario
        OrderDTO dto = orderMapper.toDTO(saved);
        enrichWithUser(dto);
        log.info("Orden {} creada para usuario {}", saved.getId(), saved.getUserId());
        return dto;
    }

    public void delete(Long id) {
        log.info("Eliminando orden {}", id);
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orden", id);
        }
        orderRepository.deleteById(id);
        log.info("Orden {} eliminada", id);
    }

    /**
     * Enriquece el DTO con los datos del usuario obtenidos vía Feign.
     * Si user-service no responde, se usa el fallback que devuelve datos mínimos.
     */
    private void enrichWithUser(OrderDTO dto) {
        try {
            UserSummaryDTO user = userServiceClient.getUserById(dto.getUserId());
            dto.setUser(user);
            log.debug("Orden {} enriquecida con datos del usuario {}", dto.getId(), user.getId());
        } catch (FeignException ex) {
            log.warn("Fallo al obtener usuario {} desde user-service: {}", dto.getUserId(), ex.getMessage());
            // Degradación controlada: el fallback se encarga
        }
    }

    /**
     * Verifica que el usuario exista en user-service.
     * Lanza UserServiceException si no se puede validar.
     */
    private void verifyUserExists(Long userId) {
        try {
            userServiceClient.getUserById(userId);
            log.debug("Usuario {} verificado correctamente", userId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Usuario", userId);
        } catch (FeignException ex) {
            throw new UserServiceException(
                    "No se pudo validar el usuario " + userId + " en user-service", ex);
        }
    }
}
