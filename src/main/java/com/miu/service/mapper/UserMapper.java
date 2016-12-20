package com.miu.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.miu.domain.Authority;
import com.miu.domain.User;
import com.miu.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

	default Set<Authority> authoritiesFromStrings(Set<String> strings) {
		return strings.stream().map(string -> {
			Authority auth = new Authority();
			auth.setName(string);
			return auth;
		}).collect(Collectors.toSet());
	}

	default Set<String> stringsFromAuthorities(Set<Authority> authorities) {
		return authorities.stream().map(Authority::getName).collect(Collectors.toSet());
	}

	List<User> userDTOsToUsers(List<UserDTO> userDTOs);

	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	@Mapping(target = "persistentTokens", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "activationKey", ignore = true)
	@Mapping(target = "resetKey", ignore = true)
	@Mapping(target = "resetDate", ignore = true)
	@Mapping(target = "password", ignore = true)
	User userDTOToUser(UserDTO userDTO);

	default User userFromId(Long id) {
		if (id == null) {
			return null;
		}

		User user = new User();
		user.setId(id);
		return user;
	}

	List<UserDTO> usersToUserDTOs(List<User> users);

	UserDTO userToUserDTO(User user);
}
