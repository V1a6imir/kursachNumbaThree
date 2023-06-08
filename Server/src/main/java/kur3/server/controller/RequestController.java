package kur3.server.controller;

import kur3.server.entity.Request;
import kur3.server.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/add-request")
    public void addRequest(@RequestBody Request request) {
        requestService.addRequest(request);
    }

    @DeleteMapping("/delete-request/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
    }

    @GetMapping("/data-request")
    public List<Request> getRequestData() {
        return requestService.getAllRequests();
    }

    @PutMapping("/edit-request/{id}")
    public void editRequest(@PathVariable Long id, @RequestBody Request request) {
        Request existingRequest = requestService.getRequestById(id);
        if (existingRequest != null) {
            existingRequest.setArriveDate(request.getArriveDate());
            existingRequest.setContractor(request.getContractor());
            // Update other fields as needed
            requestService.updateRequest(existingRequest);
        }
    }
}
