public class Feedback {
    private String feedbackId;
    private Order order;
    private int rating; // 1-5
    private String comments;
    private long createdTime;
    
    public Feedback(String feedbackId, Order order, int rating, String comments) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.feedbackId = feedbackId;
        this.order = order;
        this.rating = rating;
        this.comments = comments;
        this.createdTime = System.currentTimeMillis();
    }
    
    public String getFeedbackId() { 
        return feedbackId; 
    }
    
    public Order getOrder() { 
        return order; 
    }
    
    public int getRating() { 
        return rating; 
    }
    
    public String getComments() { 
        return comments; 
    }
    
    public long getCreatedTime() { 
        return createdTime; 
    }
}