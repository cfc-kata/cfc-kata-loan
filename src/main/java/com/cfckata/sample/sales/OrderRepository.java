package com.cfckata.sample.sales;

import com.cfckata.sample.sales.dao.OrderDO;
import com.cfckata.sample.sales.dao.OrderDOMapper;
import com.cfckata.sample.sales.dao.OrderItemDO;
import com.cfckata.sample.sales.dao.OrderItemDOMapper;
import com.cfckata.sample.sales.domain.SalesOrder;
import com.cfckata.sample.sales.domain.OrderItem;
import com.cfckata.sample.product.Product;
import com.cfckata.sample.product.ProductRepository;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.DataObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private OrderDOMapper orderMapper;
    private OrderItemDOMapper orderItemMapper;
    private ProductRepository productRepository;

    public OrderRepository(OrderDOMapper orderMapper, OrderItemDOMapper orderItemMapper, ProductRepository productRepository) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
    }

    public Aggregate<SalesOrder> findById(String id) {
        OrderDO orderDO = orderMapper.selectByPrimaryKey(id);
        if (orderDO == null) {
            throw new EntityNotFoundException("Order(" + id + ") not found");
        }

        SalesOrder salesOrder = orderDO.toOrder();
        salesOrder.setItems(getOrderItems(id));

        return AggregateFactory.createAggregate(salesOrder);
    }

    public void save(Aggregate<SalesOrder> orderAggregate) {
        if (orderAggregate.isNew()) {
            insertNewAggregate(orderAggregate);
        } else if (orderAggregate.isChanged()) {
            updateAggregateRoot(orderAggregate);
            removeOrderItems(orderAggregate);
            updateOrderItems(orderAggregate);
            insertOrderItems(orderAggregate);
        }
    }

    public void remove(Aggregate<SalesOrder> aggregate) {
        SalesOrder salesOrder = aggregate.getRoot();
        if (orderMapper.delete(new OrderDO(salesOrder)) != 1) {
            throw new OptimisticLockException(String.format("Delete order (%s) error, it's not found or changed by another user", salesOrder.getId()));
        }
        orderItemMapper.deleteByOrderId(salesOrder.getId());
    }

    private List<OrderItem> getOrderItems(String id) {
        List<OrderItemDO> itemDOs = orderItemMapper.selectByOrderId(id);
        List<String> prodIds = itemDOs.stream().map(OrderItemDO::getProdId).collect(Collectors.toList());
        Map<String, Product> productMap = productRepository.getProductMapByIds(prodIds);

        return itemDOs.stream()
                .map(itemDO -> {
                    Product product = productMap.get(itemDO.getProdId());
                    return new OrderItem(itemDO.getId(), itemDO.getAmount(), product.getPrice(), product.getId(), product.getName());
                })
                .collect(Collectors.toList());
    }

    private void insertNewAggregate(Aggregate<SalesOrder> orderAggregate) {
        SalesOrder salesOrder = orderAggregate.getRoot();
        orderMapper.insert(new OrderDO(salesOrder));

        List<OrderItemDO> itemDOs = salesOrder.getItems().stream().map(item -> new OrderItemDO(salesOrder.getId(), item)).collect(Collectors.toList());
        orderItemMapper.insertAll(itemDOs);
    }

    private void updateAggregateRoot(Aggregate<SalesOrder> orderAggregate) {
        //only update changed fields, avoid update all fields
        OrderDO delta = getOrderDODelta(orderAggregate);

        if (orderMapper.updateByPrimaryKeySelective(delta) != 1) {
            throw new OptimisticLockException(String.format("Update order (%s) error, it's not found or changed by another user", orderAggregate.getRoot().getId()));
        }
    }

    private OrderDO getOrderDODelta(Aggregate<SalesOrder> orderAggregate) {
        OrderDO current = new OrderDO(orderAggregate.getRoot());
        OrderDO old = new OrderDO(orderAggregate.getRootSnapshot());

        OrderDO delta = DataObjectUtils.getDelta(old, current);
        delta.setId(current.getId());
        delta.setVersion(current.getVersion());

        return delta;
    }

    private void insertOrderItems(Aggregate<SalesOrder> orderAggregate) {
        Collection<OrderItem> newEntities = orderAggregate.findNewEntities(SalesOrder::getItems, item -> item.getId() == null);
        if (! newEntities.isEmpty()) {
            List<OrderItemDO> itemDOs = newEntities.stream().map(item -> new OrderItemDO(orderAggregate.getRoot().getId(), item)).collect(Collectors.toList());
            orderItemMapper.insertAll(itemDOs);
        }
    }

    private void updateOrderItems(Aggregate<SalesOrder> orderAggregate) {
        Collection<OrderItem> updatedEntities = orderAggregate.findChangedEntities(SalesOrder::getItems, OrderItem::getId);
        updatedEntities.stream().forEach(item -> {
            if (orderItemMapper.updateByPrimaryKey(new OrderItemDO(orderAggregate.getRoot().getId(), item)) != 1) {
                throw new OptimisticLockException(String.format("Update order item (%d) error, it's not found", item.getId()));
            }
        });
    }

    private void removeOrderItems(Aggregate<SalesOrder> orderAggregate) {
        Collection<OrderItem> removedEntities = orderAggregate.findRemovedEntities(SalesOrder::getItems, OrderItem::getId);
        removedEntities.stream().forEach(item -> {
            if (orderItemMapper.deleteByPrimaryKey(item.getId()) != 1) {
                throw new OptimisticLockException(String.format("Delete order item (%d) error, it's not found", item.getId()));
            }
        });
    }
}
