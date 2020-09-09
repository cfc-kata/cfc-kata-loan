package com.cfckata.order;

import com.cfckata.customer.CustomerRepository;
import com.cfckata.order.api.ChangeOrderRequest;
import com.cfckata.order.api.CreateOrderRequest;
import com.cfckata.order.api.OrderItemRequest;
import com.cfckata.order.domain.Order;
import com.cfckata.order.domain.OrderItem;
import com.cfckata.order.domain.OrderStatus;
import com.cfckata.product.Product;
import com.cfckata.product.ProductRepository;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.springframework.stereotype.Service;

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
        Order order = new Order();

        order.setId(idGenerator.generateId());
        order.setCreateTime(request.getCreateTime());
        order.setCustomer(customerRepository.findById(request.getCustomerId()));
        order.setItems(getNewOrderItems(request.getItems()));
        order.setVersion(Versionable.NEW_VERSION);
        order.setStatus(OrderStatus.NEW);

        return AggregateFactory.createAggregate(order);
    }

    public Aggregate<Order> getOrder(ChangeOrderRequest request) {
        Aggregate<Order> aggregate = orderRepository.findById(request.getOrderId());
        Order order = aggregate.getRoot();

        order.setCustomer(customerRepository.findById(request.getCustomerId()));
        order.setItems(getUpdatedOrderItems(order.getItems(), request));

        return aggregate;
    }

    private List<OrderItem> getUpdatedOrderItems(List<OrderItem> items, ChangeOrderRequest request) {
        List<String> productIds = request.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList());
        Map<String, Product> productMap = productRepository.getProductMapByIds(productIds);

        return request.getItems().stream()
                .map(
                        item -> new OrderItem(getItemId(items, item.getProductId()), productMap.get(item.getProductId()), item.getAmount()))
                .collect(Collectors.toList());
    }

    private Long getItemId(List<OrderItem> items, String productId) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equalsIgnoreCase(productId)) {
                return item.getId();
            }
        }

        return null;
    }

    private List<OrderItem> getNewOrderItems(List<OrderItemRequest> items) {
        List<String> productIds = items.stream().map(item -> item.getProductId()).collect(Collectors.toList());
        Map<String, Product> productMap = productRepository.getProductMapByIds(productIds);

        return items.stream()
                    .map(
                            item -> new OrderItem(null, productMap.get(item.getProductId()), item.getAmount()))
                    .collect(Collectors.toList());
    }
}
