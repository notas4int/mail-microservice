package org.artem.projects.mailsender.models;

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
