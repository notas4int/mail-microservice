package org.artem.projects.mailregistry.models.exceptions;

public class UserWithSuchEmailAlreadyExists extends RuntimeException {
    public UserWithSuchEmailAlreadyExists(String s) {
        super(s);
    }
}
