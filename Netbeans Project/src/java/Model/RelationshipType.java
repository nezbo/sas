package Model;

public class RelationshipType {
    private final int id;
    private final String type;

    public RelationshipType(int id, String type) {
        this.id = id;
        this.type = type;
    }
    
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    
    
}
