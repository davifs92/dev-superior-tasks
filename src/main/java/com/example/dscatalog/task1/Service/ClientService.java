package com.example.dscatalog.task1.Service;

import com.example.dscatalog.task1.DTO.ClientDTO;
import com.example.dscatalog.task1.Entities.Client;
import com.example.dscatalog.task1.Repository.ClientRepository;
import com.example.dscatalog.task1.Service.Exceptions.DataBaseException;
import com.example.dscatalog.task1.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> list = repository.findAll(pageRequest);
        return list.map
                (client -> new ClientDTO(client));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> response = repository.findById(id);
        Client client = response.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insertClient(ClientDTO clientData) {
        Client client = new Client();
        copyDTOtoEntity(clientData, client);
        repository.save(client);
        return new ClientDTO(client);

    }
    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientData) {
        try {
            Client client = repository.getOne(id);
            copyDTOtoEntity(clientData, client);
            repository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException msg){
            throw new ResourceNotFoundException("Client not found");
        }
    }

    public void deleteClient(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found");
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDTOtoEntity(ClientDTO dto, Client entity){
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());

    }
}
