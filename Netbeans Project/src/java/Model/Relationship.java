package Model;

public class Relationship {
    private final User fromUser;
    private final User toUser;
    private final RelationshipType relationshipType;

    public Relationship(User fromUser, User toUser, RelationshipType relationshipType) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.relationshipType = relationshipType;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }
    
}
