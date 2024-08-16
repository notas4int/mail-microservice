package org.artem.projects.mailregistry.repositories;

import org.artem.projects.mailregistry.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
            select t from Token t inner join Account us\s
            on t.account.id = us.id\s
            where us.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByAccount(Long id);

    Optional<Token> findByToken(String token);
}
