package fbs.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fbs.dto.auth.AuthRequestDTO;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {
    
    private final AuthService authS;

    public AuthController(AuthService authS) {
        this.authS = authS;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid AuthRequestDTO request) {
        
        try {
            return ResponseEntity.ok(authS.signIn(request));    
        } 
        
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Unauthorized: Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }

        catch (Exception e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
