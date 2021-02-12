package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.UserEntity;
import pl.wroblewski.simplyaccounting.db.repositories.UserRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.security.UserDetailsImp;

@Service("userDetailsService")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    private UserEntity getUserEntityByEmail(String username){
        return userRepository.findByUsernameLike(username)
                .orElseThrow(() -> new ObjectNotFoundException(""));
    }

    private UserDetails getUserDetails(String username) {
        var user = getUserEntityByEmail(username);
        return UserDetailsImp.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetails(username);
    }
}
