package com.example.dscatalog.task1.Resources;

import com.example.dscatalog.task1.DTO.ClientDTO;
import com.example.dscatalog.task1.Entities.Client;
import com.example.dscatalog.task1.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value ="/clients")
public class ClientResources {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllpaged(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction){


        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ClientDTO> list = clientService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        ClientDTO response = clientService.findById(id);

       return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insertClient(@RequestBody ClientDTO clientData){
        ClientDTO response = clientService.insertClient(clientData);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value ="{id}")
    public ResponseEntity<ClientDTO>updateClient(@PathVariable Long id, @RequestBody ClientDTO clientData){
        ClientDTO response = clientService.updateClient(id, clientData);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value="{id}")
    public ResponseEntity<ClientDTO>deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
