/*package ma.zar.dreamshops.data;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.model.User;
import ma.zar.dreamshops.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        for(int i=0;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("default user "+i+"is created awdi arachid");

        }
    }
}*/
