package org.artem.projects.mailregistry.dtos;

import lombok.Data;

@Data
public class RequestConfirmationCodeDTO {
    private String email;
    private String code;
}
