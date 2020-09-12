package com.cfckata.sample.sales;

import com.cfckata.common.JsonComparator;
import com.cfckata.common.RepositoryTest;
import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.domain.OrderItem;
import com.cfckata.sample.sales.domain.OrderStatus;
import com.cfckata.sample.product.Product;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class SalesOrderRepositoryTest extends RepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void should_save_order() {
        String orderid = "ORDERID";
        SalesOrder salesOrder = createNormalTestOrder(orderid);
        orderRepository.save(AggregateFactory.createAggregate(salesOrder));
        Aggregate<SalesOrder> orderAggregate = orderRepository.findById(orderid);

        salesOrder.setVersion(1);
        JsonComparator.assertEqualsObjects(salesOrder, orderAggregate.getRoot());
    }

    private SalesOrder createNormalTestOrder(String orderid) {
        BigDecimal amount = new BigDecimal("2.00");
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(orderid);
        salesOrder.setCreateTime(new Date());
        salesOrder.setCustomerId("NEW_TEST_USER_ID");
        salesOrder.setStatus(OrderStatus.NEW);
        salesOrder.setTotalPayment(new BigDecimal("18000.00"));
        ArrayList<OrderItem> items = new ArrayList<>();
        final Product product = new Product("PROD1", "Computer", new BigDecimal("8000.00"));
        items.add(new OrderItem(100L, amount, product.getPrice(), product.getId(), product.getName()));
        final Product product1 = new Product("PROD2", "Keyboard", new BigDecimal("1000.00"));
        items.add(new OrderItem(200L, amount, product1.getPrice(), product1.getId(), product1.getName()));
        salesOrder.setItems(items);

        return salesOrder;
    }
}
