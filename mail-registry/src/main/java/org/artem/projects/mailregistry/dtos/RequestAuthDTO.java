package org.artem.projects.mailregistry.dtos;

import lombok.Data;

@Data
public class RequestAuthDTO {
    private String email;
    private String password;
}
