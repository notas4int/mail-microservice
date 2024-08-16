package org.artem.projects.mailregistry.repositories;

import org.artem.projects.mailregistry.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {
    Optional<Account> findUserByEmail(String email);
}
