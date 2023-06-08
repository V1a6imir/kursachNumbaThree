package kur3.server.controller;


import kur3.server.entity.Contractor;
import kur3.server.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractorController {
    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @PostMapping("/add-contractor")
    public void addContr(@RequestBody Contractor contractor) {
        contractorService.addContractor(contractor);
    }

    @DeleteMapping("/delete-contractor/{id}")
    public void removeContr(@PathVariable Long id) {
        contractorService.removeContractor(id);
    }

    @GetMapping("/data-contractor")
    public List<Contractor> getContrData() {
        return contractorService.getAllContractors();
    }

    @PutMapping("/edit-contractor/{id}")
    public void editContr(@PathVariable Long id, @RequestBody Contractor contractor) {
        Contractor existingContractor = contractorService.getContractorById(id);
        if (existingContractor != null) {
           existingContractor.setContractorName(contractor.getContractorName());
           existingContractor.setObjectData(contractor.getObjectData());
           // Update other fields as needed
           contractorService.updateContractor(existingContractor);
        }
    }
}

