package com.cfckata.sample.sales;

import com.cfckata.common.ApiTest;
import com.cfckata.exception.ErrorResponse;
import com.cfckata.sample.sales.domain.OrderStatus;
import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.request.ChangeOrderRequest;
import com.cfckata.sample.sales.request.CheckoutRequest;
import com.cfckata.sample.sales.request.CreateOrderRequest;
import com.cfckata.sample.sales.request.OrderItemRequest;
import com.cfckata.sample.sales.response.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class SalesOrderControllerTest extends ApiTest {

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_query_order() {
        String customerId = "TEST_USER_ID";
        String orderId = "TEST_ORDER";

        ResponseEntity<OrderResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/orders/" + orderId, OrderResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        OrderResponse order = responseEntity.getBody();
        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getItems()).hasSize(2);
    }

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_support_create_order() {
        //Given
        String customerId = "TEST_USER_ID";

        ArrayList<OrderItemRequest> items = new ArrayList<>();
        items.add(new OrderItemRequest("PROD1", BigDecimal.ONE));
        items.add(new OrderItemRequest("PROD2", BigDecimal.ONE));

        CreateOrderRequest request = new CreateOrderRequest(new Date(), customerId, items);

        //When
        ResponseEntity<OrderResponse> responseEntity = this.restTemplate.postForEntity(baseUrl + "/orders", request, OrderResponse.class);

        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        OrderResponse order = responseEntity.getBody();
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getItems()).hasSize(2);
    }

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_support_update_order() {
        //Given
        String orderId = "TEST_ORDER";
        String newCustomerId = "NEW_TEST_USER_ID";

        ArrayList<OrderItemRequest> items = new ArrayList<>();
        items.add(new OrderItemRequest("PROD1", new BigDecimal("1.00")));
        items.add(new OrderItemRequest("PROD2", BigDecimal.TEN));

        ChangeOrderRequest request = new ChangeOrderRequest(orderId, newCustomerId, items);

        //When
        this.restTemplate.put(baseUrl + "/orders/"+ orderId, request);

        //Then
        ResponseEntity<OrderResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/orders/" + orderId, OrderResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        OrderResponse order = responseEntity.getBody();
        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(order.getCustomerId()).isEqualTo(newCustomerId);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getItems().get(0).getAmount()).isEqualTo("1");
        assertThat(order.getItems().get(1).getAmount()).isEqualTo("10");
    }

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_support_new_order_item() {
        //Given
        String orderId = "TEST_ORDER";
        String newCustomerId = "TEST_USER_ID";

        ArrayList<OrderItemRequest> items = new ArrayList<>();
        items.add(new OrderItemRequest("PROD1", new BigDecimal("1.00")));
        items.add(new OrderItemRequest("PROD2", BigDecimal.TEN));
        items.add(new OrderItemRequest("PROD3", BigDecimal.TEN));

        ChangeOrderRequest request = new ChangeOrderRequest(orderId, newCustomerId, items);

        //When
        this.restTemplate.put(baseUrl + "/orders/"+ orderId, request);

        //Then
        ResponseEntity<OrderResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/orders/" + orderId, OrderResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        OrderResponse order = responseEntity.getBody();
        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(order.getCustomerId()).isEqualTo(newCustomerId);
        assertThat(order.getItems()).hasSize(3);
        assertThat(order.getItems().get(0).getAmount()).isEqualTo("1");;
        assertThat(order.getItems().get(1).getAmount()).isEqualTo("10");
        assertThat(order.getItems().get(2).getAmount()).isEqualTo("10");
    }

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_support_remove_orderItems() {
        //Given
        String orderId = "TEST_ORDER";

        //When
        this.restTemplate.delete(baseUrl + "/orders/"+ orderId);

        //Then
        ResponseEntity<ErrorResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/orders/" + orderId, ErrorResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql(scripts = "classpath:sql/order-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/order-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_checkout_order() {
        //Given
        String orderId = "TEST_ORDER";
        BigDecimal amount = new BigDecimal("9000");
        CheckoutRequest request = new CheckoutRequest("CASH", amount);

        //When
        this.restTemplate.postForLocation(baseUrl + "/orders/"+orderId+"/payment", request);

        //Then
        ResponseEntity<OrderResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/orders/" + orderId, OrderResponse.class);
        assertThat(responseEntity.getBody().getTotalPayment()).isEqualTo("￥9,000.00");
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(OrderStatus.PAID.getValue());
    }

}
