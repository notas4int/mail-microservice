package org.artem.projects.mailregistry.services;

import org.artem.projects.mailregistry.dtos.RequestAuthDTO;
import org.artem.projects.mailregistry.dtos.RequestConfirmationCodeDTO;

public interface AuthService {
    String auth(RequestAuthDTO requestAuthDTO);

    void register(RequestAuthDTO requestAuthDTO);

    String confirmCode(RequestConfirmationCodeDTO codeObj);
}
