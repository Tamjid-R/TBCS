import java.io.File;
import java.util.Arrays;

public class TasteBudsCateringSystemMainXML {
    public static void main(String[] args) {
        System.out.println("===== TasteBuds Catering System with XML Storage =====\n");
        
        // Initialize system with data directory
        String dataDirectory = "tbcs_data";
        createDataDirectory(dataDirectory);
        
        TasteBudsCateringSystemXML system = new TasteBudsCateringSystemXML(dataDirectory);
        
        // 1. CREATE AND SAVE CUSTOMER
        System.out.println("1. SAVING CUSTOMER TO XML");
        Customer customer = new Customer("C001", "Ahmed Hassan", "ahmed@email.com", "01712345678", true);
        system.saveCustomer(customer);
        System.out.println("   Customer saved: " + customer.getName());
        
        // 2. RETRIEVE CUSTOMER FROM XML
        System.out.println("\n2. RETRIEVING CUSTOMER FROM XML");
        Customer retrievedCustomer = system.getCustomer("C001");
        if (retrievedCustomer != null) {
            System.out.println("   Retrieved: " + retrievedCustomer.getName() + " (" + retrievedCustomer.getCustomerId() + ")");
        }
        
        // 3. PLACE AND SAVE ORDER
        System.out.println("\n3. PLACING AND SAVING ORDER TO XML");
        MenuItem biryani = new MenuItem("M001", "Chicken Biryani", 250.0, "Fragrant rice");
        MenuItem dessert = new MenuItem("M002", "Kheer", 50.0, "Rice pudding");
        
        Order order = system.getOrderService().placeOrder("ORD001", customer);
        order.addItem(new OrderItem(biryani, 1));
        order.addItem(new OrderItem(dessert, 1));
        system.saveOrder(order);
        
        System.out.println("   Order placed and saved: " + order.getOrderId());
        System.out.println("   Queue Number: " + order.getQueueNumber());
        System.out.println("   Total Amount: " + order.getTotalAmount());
        
        // 4. RETRIEVE ORDER FROM XML
        System.out.println("\n4. RETRIEVING ORDER FROM XML");
        Order retrievedOrder = system.getOrder("ORD001");
        if (retrievedOrder != null) {
            System.out.println("   Retrieved Order ID: " + retrievedOrder.getOrderId());
            System.out.println("   Queue Number: " + retrievedOrder.getQueueNumber());
            System.out.println("   Items Count: " + retrievedOrder.getItems().size());
        }
        
        // 5. KITCHEN PREPARATION
        System.out.println("\n5. KITCHEN PREPARATION (Head Chef)");
        Chef chef1 = new Chef("CH001", "Hassan Ali", 8);
        Chef chef2 = new Chef("CH002", "Fatima Begum", 6);
        system.getKitchenService().addChef(chef1);
        system.getKitchenService().addChef(chef2);
        
        system.getKitchenService().assignChefsToOrder(order, Arrays.asList(chef1, chef2));
        system.getKitchenService().prepareOrder(order);
        long estimatedTime = system.getKitchenService().estimatePrepTime(order);
        
        System.out.println("   Chefs Assigned: " + chef1.getName() + ", " + chef2.getName());
        System.out.println("   Estimated Prep Time: " + estimatedTime + " minutes");
        System.out.println("   Order Priority: " + order.getPriority());
        
        system.getKitchenService().markOrderReady(order);
        system.saveOrder(order);
        System.out.println("   Order Status: " + order.getStatus());
        
        // 6. DELIVERY ASSIGNMENT
        System.out.println("\n6. DELIVERY ASSIGNMENT");
        Driver driver = new Driver("D001", "Karim Khan", "DL-123456789");
        Vehicle vehicle = new Vehicle("V001", "Bike", "DHK-2024-001");
        
        system.getDeliveryService().addDriver(driver);
        system.getDeliveryService().addVehicle(vehicle);
        
        Delivery delivery = system.getDeliveryService().assignDelivery("DEL001", order);
        system.saveDelivery(delivery);
        
        System.out.println("   Driver: " + delivery.getDriver().getName());
        System.out.println("   Vehicle: " + delivery.getVehicle().getType());
        System.out.println("   Order Status: " + order.getStatus());
        
        // 7. DRIVER CHECKOUT
        System.out.println("\n7. DRIVER CHECKOUT");
        boolean checkoutResult = system.getDriverCheckoutService()
            .checkoutDelivery("DEL001", "DL-123456789", system.getDeliveryService());
        System.out.println("   Checkout Successful: " + checkoutResult);
        System.out.println("   Is Checked Out: " + system.getDriverCheckoutService().isCheckedOut("DEL001"));
        
        // 8. DELIVERY COMPLETE
        System.out.println("\n8. DELIVERY COMPLETE");
        system.getDeliveryService().markDeliveryDelivered("DEL001");
        system.saveDelivery(delivery);
        System.out.println("   Delivery Marked as Delivered");
        System.out.println("   Order Status: " + order.getStatus());
        
        // 9. CUSTOMER FEEDBACK AND SAVE
        System.out.println("\n9. CUSTOMER FEEDBACK (Saving to XML)");
        Feedback feedback = system.getFeedbackService()
            .submitFeedback("FB001", order, 5, "Excellent service and delicious food!");
        system.saveFeedback(feedback);
        
        System.out.println("   Rating: " + feedback.getRating() + "/5");
        System.out.println("   Comments: " + feedback.getComments());
        System.out.println("   Feedback saved to XML");
        
        // 10. RETRIEVE FEEDBACK FROM XML
        System.out.println("\n10. RETRIEVING FEEDBACK FROM XML");
        Feedback retrievedFeedback = system.getFeedback("FB001");
        if (retrievedFeedback != null) {
            System.out.println("   Retrieved Feedback ID: " + retrievedFeedback.getFeedbackId());
            System.out.println("   Rating: " + retrievedFeedback.getRating() + "/5");
            System.out.println("   Comments: " + retrievedFeedback.getComments());
        }
        
        // 11. DISPLAY XML FILES
        System.out.println("\n11. XML FILES CREATED:");
        displayXMLFiles(dataDirectory);
        
        System.out.println("\n===== System Execution Complete =====");
    }
    
    private static void createDataDirectory(String dataDirectory) {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Data directory created: " + dataDirectory);
        }
    }
    
    private static void displayXMLFiles(String dataDirectory) {
        File dir = new File(dataDirectory);
        File[] files = dir.listFiles();
        
        if (files != null) {
            for (File file : files) {
                System.out.println("   - " + file.getName() + " (" + file.length() + " bytes)");
            }
        }
    }
}