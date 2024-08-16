package org.artem.projects.mailregistry.mappers;

import org.artem.projects.mailregistry.dtos.RequestAuthDTO;
import org.artem.projects.mailregistry.kafka.models.ConfirmationCodeMsg;
import org.artem.projects.mailregistry.kafka.models.UserCredentialsMsg;
import org.artem.projects.mailregistry.models.CacheUser;
import org.artem.projects.mailregistry.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCredentialsMsg convertAuthRequestToUserCredentialsMsg(RequestAuthDTO userDTO);

    @Mapping(target = "id", ignore = true)
    Account convertCacheUserToUser(CacheUser cacheUser);

    CacheUser convertKafkaRequestToCacheUser(ConfirmationCodeMsg user);
}
