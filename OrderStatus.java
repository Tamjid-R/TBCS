public enum OrderStatus {
    PLACED("Order has been placed"),
    PREPARING("Order is being prepared"),
    PREPARED("Order is ready for delivery"),
    IN_DELIVERY("Order is out for delivery"),
    DELIVERED("Order has been delivered"),
    CANCELLED("Order has been cancelled");
    
    private String description;
    
    OrderStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}