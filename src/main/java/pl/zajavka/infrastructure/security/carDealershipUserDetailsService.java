package pl.zajavka.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class carDealershipUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(userName);
        List<SimpleGrantedAuthority> authorities = getAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthority(Set<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .distinct()
                .toList();
    }

    private UserDetails buildUserForAuthentication(UserEntity user, List<SimpleGrantedAuthority> authorities) {
        return new User(
                user.getUserName(), user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities
        );
    }
}
