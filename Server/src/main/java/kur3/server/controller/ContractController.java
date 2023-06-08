package kur3.server.controller;


import kur3.server.entity.Contract;
import kur3.server.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/contracts")
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/contracts/{id}")
    public Contract getContractById(@PathVariable Long id) {
        return contractService.getContractById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));
    }

    @PostMapping("/contracts-add")
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.createContract(contract);
    }


    @DeleteMapping("/contracts-delete/{id}")
    public void deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
    }
}
