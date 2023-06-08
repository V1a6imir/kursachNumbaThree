package kur3.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contractorId;
    private String contractorName;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_data_name")
    private ObjectData objectData;

    public Contractor() {
    }

    public Contractor(Long contractorId, String contractorName, Long objectDataId) {
        this.contractorId = contractorId;
        this.contractorName = contractorName;
        this.objectData = new ObjectData(objectDataId, null);
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
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
                "contractorId=" + contractorId +
                ", contractorName=" + contractorName +
                ", objectData=" + objectData +
                '}';
    }
}
