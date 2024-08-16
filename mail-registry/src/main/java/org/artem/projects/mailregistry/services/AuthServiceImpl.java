package org.artem.projects.mailregistry.services;

import lombok.RequiredArgsConstructor;
import org.artem.projects.mailregistry.dtos.RequestAuthDTO;
import org.artem.projects.mailregistry.dtos.RequestConfirmationCodeDTO;
import org.artem.projects.mailregistry.kafka.models.UserCredentialsMsg;
import org.artem.projects.mailregistry.kafka.producers.UserCredentialsProducer;
import org.artem.projects.mailregistry.mappers.UserMapper;
import org.artem.projects.mailregistry.models.Account;
import org.artem.projects.mailregistry.models.CacheUser;
import org.artem.projects.mailregistry.models.SecurityUserDetails;
import org.artem.projects.mailregistry.models.exceptions.IncorrectConfirmationCodeException;
import org.artem.projects.mailregistry.models.exceptions.UserNotFoundException;
import org.artem.projects.mailregistry.repositories.CacheUserRepository;
import org.artem.projects.mailregistry.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CacheUserRepository cacheUserRepository;
    private final UserMapper userMapper;
    private final UserCredentialsProducer userCredentialsProducer;

    @Override
    public String auth(RequestAuthDTO requestAuthDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestAuthDTO.getEmail(),
                requestAuthDTO.getPassword()));

        var account = userRepository.findUserByEmail(requestAuthDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Bank account with login '" + requestAuthDTO.getEmail()
                        + "' not found"));
        SecurityUserDetails accountDetails = new SecurityUserDetails(account);

        var jwtToken = jwtService.generateToken(accountDetails);
//        var refreshToken = jwtService.generateRefreshToken(accountDetails);

        jwtService.revokeAllAccountTokens(account);
        jwtService.saveClientToken(account, jwtToken);

        return jwtToken;
    }

    @Override
    public void register(RequestAuthDTO requestAuthDTO) {
        UserCredentialsMsg msg = userMapper.convertAuthRequestToUserCredentialsMsg(requestAuthDTO);
        userCredentialsProducer.send("mail.registry.user.credentials", msg);
    }

    @Override
    public String confirmCode(RequestConfirmationCodeDTO codeObj) {
        CacheUser cacheUser = cacheUserRepository.findCacheUserByEmail(codeObj.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email '" + codeObj.getEmail() + "' not found"));
        if (!Objects.equals(cacheUser.getCode(), codeObj.getCode()))
            throw new IncorrectConfirmationCodeException("Incorrect confirmation code");

        Account account = userMapper.convertCacheUserToUser(cacheUser);
        userRepository.save(account);

        SecurityUserDetails accountDetails = new SecurityUserDetails(account);
        var jwtToken = jwtService.generateToken(accountDetails);
        jwtService.saveClientToken(account, jwtToken);

        return jwtToken;
    }
}
