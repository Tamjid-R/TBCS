import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenService {
    private List<Chef> chefs;
    private Map<String, List<Chef>> assignedChefs;
    
    public KitchenService() {
        this.chefs = new ArrayList<>();
        this.assignedChefs = new HashMap<>();
    }
    
    public void addChef(Chef chef) {
        if (chef != null) {
            chefs.add(chef);
        }
    }
    
    public void assignChefsToOrder(Order order, List<Chef> selectedChefs) {
        if (order != null && selectedChefs != null) {
            assignedChefs.put(order.getOrderId(), new ArrayList<>(selectedChefs));
        }
    }
    
    public long estimatePrepTime(Order order) {
        if (order == null) {
            return 0;
        }
        
        long baseTime = 20; // minutes
        int itemCount = order.getItems().size();
        long additionalTime = itemCount * 5;
        return baseTime + additionalTime;
    }
    
    public void prepareOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        
        if (!assignedChefs.containsKey(order.getOrderId())) {
            throw new IllegalStateException("No chefs assigned to order: " + order.getOrderId());
        }
        
        long estimatedTime = estimatePrepTime(order);
        order.setEstimatedPrepTime(estimatedTime);
        
        // Determine priority based on complexity
        if (order.getItems().size() > 5) {
            order.setPriority(OrderPriority.PRIORITY);
        }
        
        // Set status to PREPARING
        order.setPriority(order.getPriority());
    }
    
    public void markOrderReady(Order order) {
        if (order != null) {
            order.markPrepared();
        }
    }
    
    public List<Chef> getChefsForOrder(String orderId) {
        return assignedChefs.getOrDefault(orderId, new ArrayList<>());
    }
    
    public List<Chef> getAllChefs() {
        return new ArrayList<>(chefs);
    }
    
    public int getTotalChefs() {
        return chefs.size();
    }
}