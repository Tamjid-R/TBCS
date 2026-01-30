import java.util.HashMap;
import java.util.Map;

public class DriverCheckoutService {
    private Map<String, String> checkoutRecords; // deliveryId -> drivingLicense
    
    public DriverCheckoutService() {
        this.checkoutRecords = new HashMap<>();
    }
    
    public boolean checkoutDelivery(String deliveryId, String drivingLicense, 
                                     DeliveryService deliveryService) {
        if (deliveryId == null || drivingLicense == null || deliveryService == null) {
            return false;
        }
        
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        if (delivery == null) {
            return false;
        }
        
        if (!delivery.getDriver().getDrivingLicense().equals(drivingLicense)) {
            return false;
        }
        
        checkoutRecords.put(deliveryId, drivingLicense);
        return true;
    }
    
    public boolean isCheckedOut(String deliveryId) {
        return checkoutRecords.containsKey(deliveryId);
    }
    
    public String getCheckoutLicense(String deliveryId) {
        return checkoutRecords.get(deliveryId);
    }
    
    public int getTotalCheckouts() {
        return checkoutRecords.size();
    }
}