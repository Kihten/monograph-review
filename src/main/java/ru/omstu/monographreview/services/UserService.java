package ru.omstu.monographreview.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.omstu.monographreview.dto.UserDTO;
import ru.omstu.monographreview.models.Author;
import ru.omstu.monographreview.models.Role;
import ru.omstu.monographreview.models.User;
import ru.omstu.monographreview.repositories.AuthorRepository;
import ru.omstu.monographreview.repositories.RoleRepository;
import ru.omstu.monographreview.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не был найден");
        }
        return user.get();
    }

    @Transactional
    public void saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Role role = roleRepository.findByName("ROLE_USER");

        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).get();
    }

    public Author getCurrentAuthor(){
        Long id = getCurrentUser().getId();
        return authorRepository.findById(id).get();
    }
}
