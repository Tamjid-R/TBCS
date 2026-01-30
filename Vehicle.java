public class Vehicle {
    private String vehicleId;
    private String type;
    private String registrationNumber;
    private boolean available;
    
    public Vehicle(String vehicleId, String type, String registrationNumber) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.available = true;
    }
    
    public String getVehicleId() { 
        return vehicleId; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public String getRegistrationNumber() { 
        return registrationNumber; 
    }
    
    public boolean isAvailable() { 
        return available; 
    }
    
    public void setAvailable(boolean available) { 
        this.available = available; 
    }
}