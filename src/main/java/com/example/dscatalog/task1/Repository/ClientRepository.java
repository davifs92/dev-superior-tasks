package com.example.dscatalog.task1.Repository;

import com.example.dscatalog.task1.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
