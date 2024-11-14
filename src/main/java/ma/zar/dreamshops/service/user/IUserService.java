package ma.zar.dreamshops.service.user;


import ma.zar.dreamshops.dtos.UserDto;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.User;
import ma.zar.dreamshops.request.CreateUserRequest;
import ma.zar.dreamshops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId) throws ResourceNotFoundException;
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId) throws ResourceNotFoundException;
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}

