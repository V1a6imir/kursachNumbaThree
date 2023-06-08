package kur3.server.service;


import kur3.server.entity.Contract;
import kur3.server.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> getContractById(Long contractId) {
        return contractRepository.findById(contractId);
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }



    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }
}
