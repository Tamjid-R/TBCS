public enum OrderPriority {
    NORMAL("Standard delivery time"),
    PRIORITY("Priority delivery within 10 minutes of order ready");
    
    private String description;
    
    OrderPriority(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}