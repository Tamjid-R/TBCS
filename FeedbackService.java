import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackService {
    private Map<String, Feedback> feedbacks;
    
    public FeedbackService() {
        this.feedbacks = new HashMap<>();
    }
    
    public Feedback submitFeedback(String feedbackId, Order order, int rating, 
                                   String comments) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Feedback can only be given for delivered orders");
        }
        
        Feedback feedback = new Feedback(feedbackId, order, rating, comments);
        feedbacks.put(feedbackId, feedback);
        return feedback;
    }
    
    public Feedback getFeedback(String feedbackId) {
        return feedbacks.get(feedbackId);
    }
    
    public List<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbacks.values());
    }
    
    public double getAverageRating() {
        if (feedbacks.isEmpty()) {
            return 0;
        }
        
        double sum = 0;
        for (Feedback feedback : feedbacks.values()) {
            sum += feedback.getRating();
        }
        return sum / feedbacks.size();
    }
    
    public int getTotalFeedback() {
        return feedbacks.size();
    }
    
    public List<Feedback> getFeedbackByRating(int rating) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback feedback : feedbacks.values()) {
            if (feedback.getRating() == rating) {
                result.add(feedback);
            }
        }
        return result;
    }
}