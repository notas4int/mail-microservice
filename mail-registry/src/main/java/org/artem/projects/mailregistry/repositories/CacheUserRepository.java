package org.artem.projects.mailregistry.repositories;

import org.artem.projects.mailregistry.models.CacheUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CacheUserRepository extends CrudRepository<CacheUser, String> {
    Optional<CacheUser> findCacheUserByEmail(String email);

    boolean existsCacheUserByEmail(String email);
}
