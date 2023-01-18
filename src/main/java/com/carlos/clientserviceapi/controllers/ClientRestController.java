package com.carlos.clientserviceapi.controllers;

import com.carlos.clientserviceapi.models.entity.Client;
import com.carlos.clientserviceapi.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getAllClients(){
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id){
        Client client = null;
        Map<String, Object> response = new HashMap<>();
        try {
            client = clientService.findById(id);
        } catch (DataAccessException e){
            response.put("message", "Error getting data from database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null){
            response.put("message", "Client with ID: ".concat(id.toString()) +  " not found");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result){
        Client newClient = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                            .stream()
                            .map(fieldError ->
                                    "The field '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newClient = clientService.save(client);
            newClient.setCreateAt(new Date());
            clientService.save(newClient);
        } catch (DataAccessException e){
            response.put("message", "Error inserting data into database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Client has been successfully created");
        response.put("client", newClient);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteClient(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            clientService.delete(id);
        }catch (DataAccessException e){
            response.put("message", "Error deleting client from database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Client has been successfully deleted");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult result, @PathVariable Long id){

        Client clientDb = clientService.findById(id);
        Client clientUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(fieldError ->
                            "The field '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (clientDb == null){
            response.put("message", "Error, client could not be edited, client with ID: ".concat(id.toString()) +  " not found");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientDb.setName(client.getName());
            clientDb.setLastName(client.getLastName());
            clientDb.setEmail(client.getEmail());
            clientDb.setCreateAt(client.getCreateAt());

            clientUpdated = clientService.save(clientDb);

        }catch (DataAccessException e){
            response.put("message", "Error updating data on database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Client has been successfully updated");
        response.put("client", clientUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
