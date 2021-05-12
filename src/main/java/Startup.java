import com.example.demo.Roles;
import com.example.demo.RolesRepository;
import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Startup {

    private final static Logger LOGGER = Logger.getLogger(Startup.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    void contextRefreshedEvent() {
        LOGGER.info("Load Application started.........");

        Roles roles = new Roles();
        roles.setName("ROLE_USER");
        rolesRepository.save(roles);

        Roles roles1 = new Roles();
        roles1.setName("ROLE_ADMIN");
        rolesRepository.save(roles1);

        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(roles);
        rolesList.add(roles1);

        User user = new User();
        user.setFirstname("sathish");
        user.setLastname("kumar");
        user.setPassword("password");
        user.setRoles(rolesList);
        userRepository.save(user);

        User user1 = new User();
        user1.setFirstname("virat");
        user1.setLastname("kohli");
        user1.setPassword("password");
        user1.setRoles(rolesList);
        userRepository.save(user1);

        LOGGER.info("Load Application completed.........");
    }
}
