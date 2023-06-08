package kur3.server.service;


import kur3.server.entity.Contractor;
import kur3.server.entity.Customer;
import kur3.server.repository.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService {
    private ContractorRepository contractorRepository;

    @Autowired
    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public void addContractor(Contractor contractor) {
        contractorRepository.save(contractor);
    }

    public void removeContractor(Long contractorId) {
        contractorRepository.deleteById(contractorId);
    }

    public Contractor updateContractor(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    public List<Contractor> getAllContractors() {
        List<Contractor> contractor = contractorRepository.findAll();
        return contractor;
    }

    public Contractor getContractorById(Long contractorId) {
        return contractorRepository.findById(contractorId).orElse(null);
    }
}
