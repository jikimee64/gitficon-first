package order.system.db.core.user;

public abstract class User {

    protected Long id;

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
