package kur3.server.entity;

import jakarta.persistence.*;

@Entity
@Table
public class ObjectCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String user;
    private String type_of_work;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_data_id")
    private ObjectData objectData;

    public ObjectCharacteristics() {
    }

    public ObjectCharacteristics(String address, String user, String type_of_work, Long objectDataId) {
        this.address = address;
        this.user = user;
        this.type_of_work = type_of_work;
        this.objectData = new ObjectData(objectDataId, null);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType_of_work() {
        return type_of_work;
    }

    public void setType_of_work(String type_of_work) {
        this.type_of_work = type_of_work;
    }

    public ObjectData getObjectData() {
        return objectData;
    }

    public void setObjectData(ObjectData objectData) {
        this.objectData = objectData;
    }

    @Override
    public String toString() {
        return "ObjectCharacteristics{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", type_of_work=" +type_of_work +
                ", objectData=" + objectData +
                '}';
    }
}
