package org.artem.projects.mailregistry.models;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash
@Data
public class CacheUser implements Serializable {
    @Id
    private String id;

    @NotNull
    @Indexed
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String code;
}
