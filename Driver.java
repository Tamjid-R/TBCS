public class Driver {
    private String driverId;
    private String name;
    private String drivingLicense;
    private boolean available;
    
    public Driver(String driverId, String name, String drivingLicense) {
        this.driverId = driverId;
        this.name = name;
        this.drivingLicense = drivingLicense;
        this.available = true;
    }
    
    public String getDriverId() { 
        return driverId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getDrivingLicense() { 
        return drivingLicense; 
    }
    
    public boolean isAvailable() { 
        return available; 
    }
    
    public void setAvailable(boolean available) { 
        this.available = available; 
    }
}