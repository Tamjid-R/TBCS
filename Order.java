import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private int queueNumber;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
    private OrderPriority priority;
    private long createdTime;
    private long preparedTime;
    private long estimatedPrepTime; // in minutes
    
    public Order(String orderId, int queueNumber, Customer customer) {
        this.orderId = orderId;
        this.queueNumber = queueNumber;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED;
        this.priority = OrderPriority.NORMAL;
        this.createdTime = System.currentTimeMillis();
        this.totalAmount = 0;
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }
    
    private void calculateTotal() {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getSubtotal();
        }
        double discount = customer.getDiscount();
        double discountAmount = (subtotal * discount) / 100;
        this.totalAmount = subtotal - discountAmount;
    }
    
    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }
    
    public void setEstimatedPrepTime(long minutes) {
        this.estimatedPrepTime = minutes;
    }
    
    public void markPrepared() {
        this.status = OrderStatus.PREPARED;
        this.preparedTime = System.currentTimeMillis();
    }
    
    public void startDelivery() {
        this.status = OrderStatus.IN_DELIVERY;
    }
    
    public void markDelivered() {
        this.status = OrderStatus.DELIVERED;
    }
    
    public void cancelOrder() {
        this.status = OrderStatus.CANCELLED;
    }
    
    // Getters
    public String getOrderId() { 
        return orderId; 
    }
    
    public int getQueueNumber() { 
        return queueNumber; 
    }
    
    public Customer getCustomer() { 
        return customer; 
    }
    
    public List<OrderItem> getItems() { 
        return new ArrayList<>(items); 
    }
    
    public double getTotalAmount() { 
        return totalAmount; 
    }
    
    public OrderStatus getStatus() { 
        return status; 
    }
    
    public OrderPriority getPriority() { 
        return priority; 
    }
    
    public long getCreatedTime() { 
        return createdTime; 
    }
    
    public long getPreparedTime() { 
        return preparedTime; 
    }
    
    public long getEstimatedPrepTime() { 
        return estimatedPrepTime; 
    }
}