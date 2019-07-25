package xyz.markpost.clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.clients.model.Client;

@Component
public interface ClientRepository extends JpaRepository<Client, Long> {

}
