public class TasteBudsCateringSystemXML {
    private OrderService orderService;
    private KitchenService kitchenService;
    private DeliveryService deliveryService;
    private FeedbackService feedbackService;
    private DriverCheckoutService driverCheckoutService;
    
    // XML DAOs
    private CustomerXMLDAO customerDAO;
    private OrderXMLDAO orderDAO;
    private FeedbackXMLDAO feedbackDAO;
    private DeliveryXMLDAO deliveryDAO;
    
    private String dataDirectory;
    
    public TasteBudsCateringSystemXML(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        
        // Initialize services
        this.orderService = new OrderService();
        this.kitchenService = new KitchenService();
        this.deliveryService = new DeliveryService();
        this.feedbackService = new FeedbackService();
        this.driverCheckoutService = new DriverCheckoutService();
        
        // Initialize XML DAOs
        this.customerDAO = new CustomerXMLDAO(dataDirectory + "/customers.xml");
        this.orderDAO = new OrderXMLDAO(dataDirectory + "/orders.xml");
        this.feedbackDAO = new FeedbackXMLDAO(dataDirectory + "/feedbacks.xml");
        this.deliveryDAO = new DeliveryXMLDAO(dataDirectory + "/deliveries.xml");
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
    
    // Getters for DAOs
    public CustomerXMLDAO getCustomerDAO() { 
        return customerDAO; 
    }
    
    public OrderXMLDAO getOrderDAO() { 
        return orderDAO; 
    }
    
    public FeedbackXMLDAO getFeedbackDAO() { 
        return feedbackDAO; 
    }
    
    public DeliveryXMLDAO getDeliveryDAO() { 
        return deliveryDAO; 
    }
    
    // Helper methods to save data
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }
    
    public Customer getCustomer(String customerId) {
        return customerDAO.getCustomer(customerId);
    }
    
    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }
    
    public Order getOrder(String orderId) {
        return orderDAO.getOrder(orderId);
    }
    
    public void saveFeedback(Feedback feedback) {
        feedbackDAO.saveFeedback(feedback);
    }
    
    public Feedback getFeedback(String feedbackId) {
        return feedbackDAO.getFeedback(feedbackId);
    }
    
    public void saveDelivery(Delivery delivery) {
        deliveryDAO.saveDelivery(delivery);
    }
    
    public Delivery getDelivery(String deliveryId) {
        return deliveryDAO.getDelivery(deliveryId);
    }
}