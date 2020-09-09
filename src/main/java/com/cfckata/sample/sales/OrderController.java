package com.cfckata.sample.sales;

import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.request.ChangeOrderRequest;
import com.cfckata.sample.sales.request.CheckoutRequest;
import com.cfckata.sample.sales.request.CreateOrderRequest;
import com.cfckata.sample.sales.response.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderResponse findOrder(@PathVariable String id) {
        SalesOrder salesOrder = orderService.findById(id);

        return new OrderResponse(salesOrder);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        SalesOrder salesOrder = orderService.createOrder(request);
        return new OrderResponse(salesOrder);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrder(@PathVariable String id, @RequestBody ChangeOrderRequest request) {
        if (id == null || !id.equalsIgnoreCase(request.getOrderId())) {
            throw new IllegalArgumentException("id should equals request.orderId");
        }
        SalesOrder salesOrder = orderService.updateOrder(request);
        return new OrderResponse(salesOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void discardOrder(@PathVariable String id) {
        orderService.discardOrder(id);
    }

    @PostMapping("/{id}/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public void checkout(@PathVariable String id, @RequestBody CheckoutRequest request) {
        orderService.checkout(id, request);
    }
}
