package ma.zar.dreamshops.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.request.LoginRequest;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.responce.JwtResponce;
import ma.zar.dreamshops.security.jwt.JwtUtils;
import ma.zar.dreamshops.security.user.ShopUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<ApiResponce> login(@RequestBody @Valid LoginRequest request){
        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtils.generateTokenUser(authentication);
            ShopUserDetails userDetails=(ShopUserDetails)authentication.getPrincipal();
            JwtResponce jwtResponce=new JwtResponce(userDetails.getId(),jwt);
            return ResponseEntity.ok(new ApiResponce("login success",jwtResponce));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponce("login failed",null));
        }
    }

}
