package kur3.server.entity;


import jakarta.persistence.*;
@Entity
@Table
public class ObjectData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;



    public ObjectData() {
    }

    public ObjectData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ObjectData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
