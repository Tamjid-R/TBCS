public class Delivery {
    private String deliveryId;
    private Order order;
    private Driver driver;
    private Vehicle vehicle;
    private long assignedTime;
    private long deliveredTime;
    private boolean delivered;
    
    public Delivery(String deliveryId, Order order, Driver driver, Vehicle vehicle) {
        this.deliveryId = deliveryId;
        this.order = order;
        this.driver = driver;
        this.vehicle = vehicle;
        this.assignedTime = System.currentTimeMillis();
        this.delivered = false;
    }
    
    public void markDelivered() {
        this.delivered = true;
        this.deliveredTime = System.currentTimeMillis();
    }
    
    public String getDeliveryId() { 
        return deliveryId; 
    }
    
    public Order getOrder() { 
        return order; 
    }
    
    public Driver getDriver() { 
        return driver; 
    }
    
    public Vehicle getVehicle() { 
        return vehicle; 
    }
    
    public boolean isDelivered() { 
        return delivered; 
    }
    
    public long getAssignedTime() { 
        return assignedTime; 
    }
    
    public long getDeliveredTime() { 
        return deliveredTime; 
    }
}