package ru.omstu.monographreview.util;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.omstu.monographreview.models.Role;
import ru.omstu.monographreview.models.User;
import ru.omstu.monographreview.repositories.RoleRepository;
import ru.omstu.monographreview.repositories.UserRepository;

@Component
@AllArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role roleUser = createRoleIfNotFound("ROLE_USER", "Пользователь");
        Role roleReviewer = createRoleIfNotFound("ROLE_REVIEWER", "Рецензент");
        Role roleScienceAnalyst = createRoleIfNotFound("ROLE_SCIENCE_ANALYST", "Сотрудник НАО");
        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN", "Админ");

        createUserIfNotFound("User", "Фамилия", "user@example.com", "password", roleUser);
        createUserIfNotFound("Reviewer", "Фамилия", "reviewer@example.com", "password", roleReviewer);
        createUserIfNotFound("Science analyst", "Фамилия", "science@example.com", "password", roleScienceAnalyst);
        createUserIfNotFound("Admin", "Фамилия", "admin@example.com", "password", roleAdmin);
    }

    @Transactional
    Role createRoleIfNotFound(String name, String description) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }

        return role;
    }

    @Transactional
    void createUserIfNotFound(String name, String lastName, String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            return;

        }

        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add(role);

        userRepository.save(user);
    }
}
