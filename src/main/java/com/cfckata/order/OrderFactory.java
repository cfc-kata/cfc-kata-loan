package com.cfckata.order;

import com.cfckata.customer.CustomerRepository;
import com.cfckata.order.domain.Order;
import com.cfckata.order.domain.OrderItem;
import com.cfckata.order.domain.OrderStatus;
import com.cfckata.order.request.ChangeOrderRequest;
import com.cfckata.order.request.CreateOrderRequest;
import com.cfckata.order.request.OrderItemRequest;
import com.cfckata.product.Product;
import com.cfckata.product.ProductRepository;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderFactory {
    private OrderIdGenerator idGenerator;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public OrderFactory(OrderIdGenerator idGenerator, CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.idGenerator = idGenerator;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Aggregate<Order> createOrder(CreateOrderRequest request) {
        if (customerRepository.findById(request.getCustomerId()) == null) {
            throw new IllegalArgumentException("Customer not exists.");
        }

        Order order = new Order();

        order.setId(idGenerator.generateId());
        order.setCreateTime(request.getCreateTime());
        order.setCustomerId(request.getCustomerId());
        order.setItems(getNewOrderItems(request.getItems()));
        order.setVersion(Versionable.NEW_VERSION);
        order.setStatus(OrderStatus.NEW);

        return AggregateFactory.createAggregate(order);
    }

    public Aggregate<Order> getOrder(ChangeOrderRequest request) {
        Aggregate<Order> aggregate = orderRepository.findById(request.getOrderId());
        Order order = aggregate.getRoot();
        order.setCustomerId(request.getCustomerId());
        order.setItems(getUpdatedOrderItems(order.getItems(), request));

        return aggregate;
    }

    private List<OrderItem> getUpdatedOrderItems(List<OrderItem> items, ChangeOrderRequest request) {
        List<String> productIds = request.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList());
        Map<String, Product> productMap = productRepository.getProductMapByIds(productIds);

        List<OrderItem> results = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productMap.get(itemRequest.getProductId());
            Long itemId = getOrderItemId(items, itemRequest.getProductId());

            results.add(new OrderItem(itemId,
                    itemRequest.getAmount(),
                    product.getPrice(), product.getId(), product.getName())
            );
        }

        return results;
    }

    private Long getOrderItemId(List<OrderItem> items, String productId) {
        for (OrderItem item : items) {
            if (item.getProductId().equalsIgnoreCase(productId)) {
                return item.getId();
            }
        }

        return null;
    }

    private List<OrderItem> getNewOrderItems(List<OrderItemRequest> items) {
        List<String> productIds = items.stream().map(item -> item.getProductId()).collect(Collectors.toList());
        Map<String, Product> productMap = productRepository.getProductMapByIds(productIds);

        return items.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    return new OrderItem(null, item.getAmount(), product.getPrice(), product.getId(), product.getName());
                })
                .collect(Collectors.toList());
    }
}
