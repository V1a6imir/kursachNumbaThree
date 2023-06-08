package kur3.server.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customer_id;
    private String name;
    private String address;
    private String legalAddress;

    public Customer() {
    }

    public Customer(Long customer_id, String name, String address, String legalAddress) {
        this.customer_id = customer_id;
        this.name = name;
        this.address = address;
        this.legalAddress = legalAddress;
    }

    public Long getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id + ", name=" + name +
                ", address=" + address + ", legalAddress=" +
                legalAddress +
                '}';
    }
}
