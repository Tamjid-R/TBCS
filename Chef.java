public class Chef {
    private String chefId;
    private String name;
    private int experience; // in years
    
    public Chef(String chefId, String name, int experience) {
        this.chefId = chefId;
        this.name = name;
        this.experience = experience;
    }
    
    public String getChefId() { 
        return chefId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public int getExperience() { 
        return experience; 
    }
    
    public void setExperience(int experience) { 
        this.experience = experience; 
    }
}