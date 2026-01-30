public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private boolean registered;
    private int monthlyOrderCount;
    
    public Customer(String customerId, String name, String email, String phone, boolean registered) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registered = registered;
        this.monthlyOrderCount = 0;
    }
    
    public double getDiscount() {
        if (!registered) return 0;
        if (monthlyOrderCount >= 10) return 15;
        if (monthlyOrderCount >= 5) return 10;
        if (monthlyOrderCount >= 2) return 5;
        return 0;
    }
    
    public void incrementOrderCount() {
        this.monthlyOrderCount++;
    }
    
    // Getters and setters
    public String getCustomerId() { 
        return customerId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getPhone() { 
        return phone; 
    }
    
    public boolean isRegistered() { 
        return registered; 
    }
    
    public int getMonthlyOrderCount() { 
        return monthlyOrderCount; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
}