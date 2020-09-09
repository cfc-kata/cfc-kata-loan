package com.cfckata.sample.sales;

import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.domain.Payment;
import com.cfckata.sample.sales.domain.PaymentType;
import com.cfckata.sample.sales.proxy.PayProxy;
import com.cfckata.sample.sales.request.ChangeOrderRequest;
import com.cfckata.sample.sales.request.CheckoutRequest;
import com.cfckata.sample.sales.request.CreateOrderRequest;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderFactory factory;
    private PayProxy payProxy;

    public OrderService(OrderRepository orderRepository, OrderFactory factory, PayProxy payProxy) {
        this.orderRepository = orderRepository;
        this.factory = factory;
        this.payProxy = payProxy;
    }

    public SalesOrder findById(String id) {
        Aggregate<SalesOrder> orderAggregate = orderRepository.findById(id);

        return orderAggregate.getRoot();
    }

    @Transactional
    public SalesOrder createOrder(CreateOrderRequest request) {
        Aggregate<SalesOrder> aggregate = factory.createOrder(request);

        orderRepository.save(aggregate);

        Aggregate<SalesOrder> orderAggregate = orderRepository.findById(aggregate.getRoot().getId());
        return orderAggregate.getRoot();
    }

    @Transactional
    public SalesOrder updateOrder(ChangeOrderRequest request) {
        Aggregate<SalesOrder> aggregate = factory.getOrder(request);

        orderRepository.save(aggregate);

        Aggregate<SalesOrder> orderAggregate = orderRepository.findById(request.getOrderId());
        return orderAggregate.getRoot();
    }

    @Transactional
    public void discardOrder(String orderId) {
        Aggregate<SalesOrder> aggregate = orderRepository.findById(orderId);
        aggregate.getRoot().discard();

        orderRepository.remove(aggregate);
    }

    @Transactional
    public void checkout(String orderId, CheckoutRequest request) {
        Aggregate<SalesOrder> aggregate = orderRepository.findById(orderId);
        SalesOrder salesOrder = aggregate.getRoot();

        Payment payment = new Payment(PaymentType.from(request.getPaymentType()), request.getAmount());
        salesOrder.checkout(payment);
        payProxy.pay(orderId, request.getAmount());

        orderRepository.save(aggregate);
    }
}
