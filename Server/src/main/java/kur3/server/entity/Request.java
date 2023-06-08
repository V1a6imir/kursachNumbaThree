package kur3.server.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Request {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;
    private Date arriveDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    public Request() {
    }

    public Request(Long requestId, Date arriveDate, Long contractorId) {
        this.requestId = requestId;
        this.arriveDate = arriveDate;
        this.contractor = new Contractor(contractorId, null, null);
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", arriveDate=" + arriveDate +
                ", Contractor=" + contractor +
                '}';
    }
}
