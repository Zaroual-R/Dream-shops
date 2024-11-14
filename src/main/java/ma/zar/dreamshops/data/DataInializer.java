package ma.zar.dreamshops.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.model.Role;
import ma.zar.dreamshops.model.User;
import ma.zar.dreamshops.repository.RoleRepository;
import ma.zar.dreamshops.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN","ROLE_USER");
        //createDefaultUserIfNotExist();
        //createDefaultRolesIfNotExist(defaultRoles);
        createDefaultAdminIfNotExist();
    }

    private void createDefaultRolesIfNotExist(Set<String> roles) {
        roles.stream()
                .filter(role ->!roleRepository.existsByName(role))
                .map(Role:: new).forEach(roleRepository::save);
    }

    private void createDefaultUserIfNotExist() {
        Role userRole=roleRepository.findByName("ROLE_USER");
        for(int i=0;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("default user "+i+"is created awdi arachid");

        }
    }
    private void createDefaultAdminIfNotExist() {
        Role admin =roleRepository.findByName("ROLE_ADMIN");
        for(int i=0;i<=2;i++){
            String defaultEmail="admin"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("admin"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(admin));
            userRepository.save(user);
            System.out.println("default admin "+i+"is created awdi arachid");

        }
    }


}
