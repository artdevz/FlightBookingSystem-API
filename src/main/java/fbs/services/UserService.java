package fbs.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fbs.dto.user.UserRequestDTO;
import fbs.dto.user.UserResponseDTO;
import fbs.models.User;
import fbs.repositories.UserRepository;
import fbs.validators.PasswordValidator;

@Service
public class UserService {
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userR;

    public UserService(UserRepository userR) {
        this.userR = userR;
    }

    public void create(UserRequestDTO data) {

        User user = new User(
            data.name(),
            data.email(),
            passwordEncoder.encode(data.password())
        );

        userR.save(user);

    }

    public List<UserResponseDTO> readAll() {

        return userR.findAll().stream()
            .map(user -> new UserResponseDTO(
                user.getId(),                
                user.getName(),
                user.getEmail()
            ))
            .collect(Collectors.toList());
    }

    public UserResponseDTO readById(UUID id) {

        User user = userR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        
        return new UserResponseDTO(
            user.getId(),            
            user.getName(),
            user.getEmail()
        );
    }

    public void update(UUID id, Map<String, Object> fields) {

        User user = userR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found"));

        PasswordValidator passwordValidator = new PasswordValidator();

        fields.forEach((key, value) -> {
            
            switch (key) {
                case "name":
                    user.setName((String) value);
                    break;
                
                case "password":                    
                    if (!passwordValidator.isValid((String)value, null)) 
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "Invalid Password Format.");

                    user.setPassword(passwordEncoder.encode((String)value));
                    break;

                default:
                    Field field = ReflectionUtils.findField(User.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, user, value);
                    }
                    break;
            
            }

        });

        userR.save(user);

    }

    public void delete(UUID id) {

        if (!userR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found.");
        userR.deleteById(id);        

    }

}
