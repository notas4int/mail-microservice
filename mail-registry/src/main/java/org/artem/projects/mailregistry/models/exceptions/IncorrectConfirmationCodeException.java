package org.artem.projects.mailregistry.models.exceptions;

public class IncorrectConfirmationCodeException extends RuntimeException {
    public IncorrectConfirmationCodeException(String s) {
        super(s);
    }
}
