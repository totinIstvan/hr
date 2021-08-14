package hu.webuni.hr.totinistvan.model.entity;

import javax.persistence.*;

@Entity
public class PositionByCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private int minSalary;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Position position;

    public PositionByCompany() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
