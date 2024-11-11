package ma.zar.dreamshops.service.user;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.dtos.UserDto;
import ma.zar.dreamshops.exceptions.ResourceAlreadyExistException;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.User;
import ma.zar.dreamshops.repository.UserRepository;
import ma.zar.dreamshops.request.CreateUserRequest;
import ma.zar.dreamshops.request.UserUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new ResourceAlreadyExistException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) throws ResourceNotFoundException {
        return  userRepository.findById(Math.toIntExact(userId)).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(Math.toIntExact(userId)).ifPresentOrElse(userRepository :: delete, () ->{
            try {
                throw new ResourceNotFoundException("User not found!");
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
