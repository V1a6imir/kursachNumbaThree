package kur3.server.entity;


import jakarta.persistence.*;

@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contractId;
    private String contractName;
    private double price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id")
    private Request request;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Contract() {
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Contract(Long contractId, String contractName, double price, Long requestId, Long customerId) {
        this.contractId = contractId;
        this.contractName = contractName;
        this.price = price;
        this.request = new Request(requestId, null, null);
        this.customer = new Customer(customerId, null, null, null);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", contractName=" + contractName +
                ", price=" + price +
                ", requestId=" + request +
                ", customerId="+ customer +
                '}';
    }
}
