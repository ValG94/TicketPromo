package co.simplon.springticketapi.model;

public class Learner {
    private Long id;
    private String name;
    private String firstname;

    public Learner(Long id, String name, String firstname) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    @Override
    public String toString() {
        return "Learner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
