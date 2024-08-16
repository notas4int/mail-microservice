package org.artem.projects.mailregistry.kafka.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationCodeMsg {
    private String email;
    private String password;
    private int code;
}
