import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class OrderService {
    private Queue<Order> orderQueue;
    private Map<String, Order> orders;
    private int queueCounter;
    
    public OrderService() {
        this.orderQueue = new LinkedList<>();
        this.orders = new HashMap<>();
        this.queueCounter = 0;
    }
    
    public Order placeOrder(String orderId, Customer customer) {
        customer.incrementOrderCount();
        int queueNumber = ++queueCounter;
        Order order = new Order(orderId, queueNumber, customer);
        orderQueue.add(order);
        orders.put(orderId, order);
        return order;
    }
    
    public int getCurrentServingQueueNumber() {
        if (orderQueue.isEmpty()) {
            return -1;
        }
        return orderQueue.peek().getQueueNumber();
    }
    
    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
    
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
    
    public void removeOrderFromQueue(String orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            orderQueue.remove(order);
        }
    }
    
    public int getTotalOrders() {
        return orders.size();
    }
}