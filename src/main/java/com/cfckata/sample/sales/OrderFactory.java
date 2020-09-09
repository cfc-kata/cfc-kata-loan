package com.cfckata.sample.sales;

import com.cfckata.sample.customer.CustomerRepository;
import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.domain.OrderItem;
import com.cfckata.sample.sales.domain.OrderStatus;
import com.cfckata.sample.sales.request.ChangeOrderRequest;
import com.cfckata.sample.sales.request.CreateOrderRequest;
import com.cfckata.sample.sales.request.OrderItemRequest;
import com.cfckata.sample.product.Product;
import com.cfckata.sample.product.ProductRepository;
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

    public Aggregate<SalesOrder> createOrder(CreateOrderRequest request) {
        if (customerRepository.findById(request.getCustomerId()) == null) {
            throw new IllegalArgumentException("Customer not exists.");
        }

        SalesOrder salesOrder = new SalesOrder();

        salesOrder.setId(idGenerator.generateId());
        salesOrder.setCreateTime(request.getCreateTime());
        salesOrder.setCustomerId(request.getCustomerId());
        salesOrder.setItems(getNewOrderItems(request.getItems()));
        salesOrder.setVersion(Versionable.NEW_VERSION);
        salesOrder.setStatus(OrderStatus.NEW);

        return AggregateFactory.createAggregate(salesOrder);
    }

    public Aggregate<SalesOrder> getOrder(ChangeOrderRequest request) {
        Aggregate<SalesOrder> aggregate = orderRepository.findById(request.getOrderId());
        SalesOrder salesOrder = aggregate.getRoot();
        salesOrder.setCustomerId(request.getCustomerId());
        salesOrder.setItems(getUpdatedOrderItems(salesOrder.getItems(), request));

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
