package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.repositories.UserRepository;
import pl.wroblewski.simplyaccounting.exceptions.UsernameTakenException;
import pl.wroblewski.simplyaccounting.models.dtos.UserDto;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConverterService converterService;

    public void createUser(UserDto user) {
        checkUsernameUnique(user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(converterService.userDtoToEntity(user));
    }

    private void checkUsernameUnique(String username) {
        if (userRepository.existsByUsernameLike(username)) {
            throw new UsernameTakenException();
        }
    }

}
