package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.DTO.Mapper.OrderItemMapper;
import com.ecommerce.ecommerce.DTO.Mapper.OrderMapper;
import com.ecommerce.ecommerce.DTO.Request.OrderItemRequestDTO;
import com.ecommerce.ecommerce.DTO.Request.OrderRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.OrderResponseDTO;
import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.OrderItem;
import com.ecommerce.ecommerce.Models.Product;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repositories.OrderRepository;
import com.ecommerce.ecommerce.Repositories.ProductRepository;
import com.ecommerce.ecommerce.Repositories.UserRepository;
import com.ecommerce.ecommerce.Services.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(Integer userId, OrderRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User id: " + userId + " not found."));
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;
        for (OrderItemRequestDTO item : dto.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new RuntimeException("Product with id: " + item.getProductId() + " not found."));
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // deduct stock
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

            // build order item
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .priceAtPurchase(product.getPrice() * item.getQuantity())
                    .build();
            orderItems.add(orderItem);
            totalPrice += orderItem.getPriceAtPurchase();
        }

        Order order = Order.builder()
                .user(user)
                .status(Order.orderStatus.PENDING)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional
    public void cancelUnpaidOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order id: " + orderId + " not found."));
        if(!order.getStatus().equals(Order.orderStatus.PENDING)){
            throw new RuntimeException("Can not cancel order with status: " + order.getStatus());
        }
        //  Restore stock
        for(OrderItem item : order.getOrderItems()){
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        order.setStatus(Order.orderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void autoCancelUnpaidOrderTransactional() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        List<Order> unpaidOrder = orderRepository.findUnpaidOrders(thirtyMinutesAgo);
        for(Order order : unpaidOrder){
            //Restore stock
            for(OrderItem item : order.getOrderItems()){
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
            order.setStatus(Order.orderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    @Scheduled(fixedRate = 1000) // Runs every 1 minute
    public void autoCancelUnpaidOrder() {
//        autoCancelUnpaidOrderTransactional();
        System.out.println("Background worker");
    }

    @Override
    public List<OrderResponseDTO> listOrder(Pageable pageable) {
        return orderRepository.findAll(pageable).stream().map(orderMapper::toDTO).toList();
    }
}
