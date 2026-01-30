import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DeliveryService {
    private List<Driver> drivers;
    private List<Vehicle> vehicles;
    private Map<String, Delivery> deliveries;
    
    public DeliveryService() {
        this.drivers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.deliveries = new HashMap<>();
    }
    
    public void addDriver(Driver driver) {
        if (driver != null) {
            drivers.add(driver);
        }
    }
    
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }
    
    public Delivery assignDelivery(String deliveryId, Order order) {
        if (order == null) {
            return null;
        }
        
        Driver driver = findAvailableDriver();
        Vehicle vehicle = findAvailableVehicle();
        
        if (driver == null || vehicle == null) {
            return null; // Cannot assign delivery
        }
        
        driver.setAvailable(false);
        vehicle.setAvailable(false);
        
        Delivery delivery = new Delivery(deliveryId, order, driver, vehicle);
        deliveries.put(deliveryId, delivery);
        order.startDelivery();
        
        return delivery;
    }
    
    private Driver findAvailableDriver() {
        Optional<Driver> driver = drivers.stream()
            .filter(Driver::isAvailable)
            .findFirst();
        return driver.orElse(null);
    }
    
    private Vehicle findAvailableVehicle() {
        Optional<Vehicle> vehicle = vehicles.stream()
            .filter(Vehicle::isAvailable)
            .findFirst();
        return vehicle.orElse(null);
    }
    
    public Delivery getDelivery(String deliveryId) {
        return deliveries.get(deliveryId);
    }
    
    public void markDeliveryDelivered(String deliveryId) {
        Delivery delivery = deliveries.get(deliveryId);
        if (delivery != null) {
            delivery.markDelivered();
            delivery.getOrder().markDelivered();
            delivery.getDriver().setAvailable(true);
            delivery.getVehicle().setAvailable(true);
        }
    }
    
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers);
    }
    
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
    
    public int getAvailableDriversCount() {
        return (int) drivers.stream().filter(Driver::isAvailable).count();
    }
    
    public int getAvailableVehiclesCount() {
        return (int) vehicles.stream().filter(Vehicle::isAvailable).count();
    }
}