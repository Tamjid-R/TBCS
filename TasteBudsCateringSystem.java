public class TasteBudsCateringSystem {
    private OrderService orderService;
    private KitchenService kitchenService;
    private DeliveryService deliveryService;
    private FeedbackService feedbackService;
    private DriverCheckoutService driverCheckoutService;
    
    public TasteBudsCateringSystem() {
        this.orderService = new OrderService();
        this.kitchenService = new KitchenService();
        this.deliveryService = new DeliveryService();
        this.feedbackService = new FeedbackService();
        this.driverCheckoutService = new DriverCheckoutService();
    }
    
    // Getters for services
    public OrderService getOrderService() { 
        return orderService; 
    }
    
    public KitchenService getKitchenService() { 
        return kitchenService; 
    }
    
    public DeliveryService getDeliveryService() { 
        return deliveryService; 
    }
    
    public FeedbackService getFeedbackService() { 
        return feedbackService; 
    }
    
    public DriverCheckoutService getDriverCheckoutService() { 
        return driverCheckoutService; 
    }
    
    // Demo method
    public static void main(String[] args) {
        System.out.println("===== TasteBuds Catering System (TBCS) =====");
        System.out.println("System initialized successfully!");
        
        TasteBudsCateringSystem system = new TasteBudsCateringSystem();
        
        // 1. Create a customer
        Customer customer = new Customer("C001", "Ahmed Hassan", "ahmed@email.com", "01712345678", true);
        System.out.println("\n1. PLACING AN ORDER");
        System.out.println("   Customer: " + customer.getName());
        System.out.println("   Registered: " + customer.isRegistered());
        
        // 2. Place order
        Order order = system.getOrderService().placeOrder("ORD001", customer);
        MenuItem biryani = new MenuItem("M001", "Chicken Biryani", 250.0, "Fragrant rice");
        MenuItem dessert = new MenuItem("M002", "Kheer", 50.0, "Rice pudding");
        
        order.addItem(new OrderItem(biryani, 1));
        order.addItem(new OrderItem(dessert, 1));
        
        System.out.println("   Order ID: " + order.getOrderId());
        System.out.println("   Queue Number: " + order.getQueueNumber());
        System.out.println("   Total Amount: " + order.getTotalAmount());
        System.out.println("   Current Serving: " + system.getOrderService().getCurrentServingQueueNumber());
        
        // 3. Kitchen Preparation
        System.out.println("\n2. KITCHEN PREPARATION (Head Chef)");
        Chef chef1 = new Chef("CH001", "Hassan Ali", 8);
        Chef chef2 = new Chef("CH002", "Fatima Begum", 6);
        system.getKitchenService().addChef(chef1);
        system.getKitchenService().addChef(chef2);
        
        system.getKitchenService().assignChefsToOrder(order, java.util.Arrays.asList(chef1, chef2));
        system.getKitchenService().prepareOrder(order);
        long estimatedTime = system.getKitchenService().estimatePrepTime(order);
        
        System.out.println("   Chefs Assigned: " + chef1.getName() + ", " + chef2.getName());
        System.out.println("   Estimated Prep Time: " + estimatedTime + " minutes");
        System.out.println("   Order Priority: " + order.getPriority());
        
        system.getKitchenService().markOrderReady(order);
        System.out.println("   Order Status: " + order.getStatus());
        
        // 4. Delivery Assignment
        System.out.println("\n3. DELIVERY ASSIGNMENT");
        Driver driver = new Driver("D001", "Karim Khan", "DL-123456789");
        Vehicle vehicle = new Vehicle("V001", "Bike", "DHK-2024-001");
        
        system.getDeliveryService().addDriver(driver);
        system.getDeliveryService().addVehicle(vehicle);
        
        Delivery delivery = system.getDeliveryService().assignDelivery("DEL001", order);
        System.out.println("   Driver: " + delivery.getDriver().getName());
        System.out.println("   Vehicle: " + delivery.getVehicle().getType() + " (" + delivery.getVehicle().getRegistrationNumber() + ")");
        System.out.println("   Order Status: " + order.getStatus());
        
        // 5. Driver Checkout
        System.out.println("\n4. DRIVER CHECKOUT");
        boolean checkoutResult = system.getDriverCheckoutService()
            .checkoutDelivery("DEL001", "DL-123456789", system.getDeliveryService());
        System.out.println("   Checkout Successful: " + checkoutResult);
        System.out.println("   Is Checked Out: " + system.getDriverCheckoutService().isCheckedOut("DEL001"));
        
        // 6. Mark Delivery Complete
        System.out.println("\n5. DELIVERY COMPLETE");
        system.getDeliveryService().markDeliveryDelivered("DEL001");
        System.out.println("   Delivery Marked as Delivered");
        System.out.println("   Order Status: " + order.getStatus());
        System.out.println("   Driver Available: " + driver.isAvailable());
        System.out.println("   Vehicle Available: " + vehicle.isAvailable());
        
        // 7. Customer Feedback
        System.out.println("\n6. CUSTOMER FEEDBACK");
        Feedback feedback = system.getFeedbackService()
            .submitFeedback("FB001", order, 5, "Excellent service and delicious food!");
        System.out.println("   Rating: " + feedback.getRating() + "/5");
        System.out.println("   Comments: " + feedback.getComments());
        System.out.println("   Average Rating: " + system.getFeedbackService().getAverageRating());
    }
}