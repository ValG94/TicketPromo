package co.simplon.springticketapi.model;

import co.simplon.springticketapi.SpringTicketApiApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private LocalDate date;
    private String description;
    private String student;
    private Boolean resolved;
    private String promoName;

    public Ticket(Long id, LocalDate date,String description, String student, Boolean resolved, String promoName) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.student = student;
        this.resolved = resolved;
        this.promoName = promoName;
    }
    public Long getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }

    public String getStudent(){
        return  student;
    }

    public Boolean getResolved(){
        return resolved;
    }

    public String getPromoName(){
        return promoName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", student='" + student + '\'' +
                '}';
    }
}
