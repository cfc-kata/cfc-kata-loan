package com.cfckata.order;

import com.cfckata.common.JsonComparator;
import com.cfckata.common.RepositoryTest;
import com.cfckata.customer.Customer;
import com.cfckata.order.domain.Order;
import com.cfckata.order.domain.OrderItem;
import com.cfckata.order.domain.OrderStatus;
import com.cfckata.product.Product;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class OrderRepositoryTest extends RepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void should_save_order() {
        String orderid = "ORDERID";
        Order order = createNormalTestOrder(orderid);
        orderRepository.save(AggregateFactory.createAggregate(order));
        Aggregate<Order> orderAggregate = orderRepository.findById(orderid);

        order.setVersion(1);
        JsonComparator.assertEqualsObjects(order, orderAggregate.getRoot());
    }

    private Order createNormalTestOrder(String orderid) {
        BigDecimal amount = new BigDecimal("2.00");
        Order order = new Order();
        order.setId(orderid);
        order.setCreateTime(new Date());
        order.setCustomer(new Customer("NEW_TEST_USER_ID", "NEW_TEST_USER_NAME"));
        order.setStatus(OrderStatus.NEW);
        order.setTotalPayment(new BigDecimal("18000.00"));
        ArrayList<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(100L, new Product("PROD1", "Computer", new BigDecimal("8000.00")), amount));
        items.add(new OrderItem(200L, new Product("PROD2", "Keyboard", new BigDecimal("1000.00")), amount));
        order.setItems(items);

        return order;
    }
}
