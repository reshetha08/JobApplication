package com.example.JobApp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    ModelMapper mapper;

    BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret}")
    private String secretKey;

    public Page<UserResponse> getAllUsers(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> users = repo.findAll(pageable);

        List<UserResponse> res = users.getContent().stream()
                .map(u-> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, users.getTotalElements());

    }

    public UserResponse getUser(int id){

        User user = repo.findById(id).orElseThrow(()-> new UserException("user not found"));

        return mapper.map(user, UserResponse.class);
    }

    public UserResponse register(String name, String email, String password, Role role){

             if(repo.existsByEmail(email)){
                 throw new UserException("user already exists");
             }

             User user = User.builder().name(name).email(email).password(pEncoder.encode(password)).role(role).build();

             User saved = repo.save(user);

             return mapper.map(saved, UserResponse.class);
    }

    public AuthResponse login(String email, String password){

        if(repo.findByEmail(email).isEmpty()){
            throw new UserException("user doesn't exists");
        }

        User user = repo.findByEmail(email).get();

        if(!pEncoder.matches(password, user.getPassword())){
            throw new UserException("Invalid Credentials");
        }


        String token = generateToken(user);
        UserResponse u = mapper.map(user, UserResponse.class);
        return AuthResponse.builder().user(u).token(token).build();

    }

    public String generateToken(User user){

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder().setSubject(user.getEmail()).claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(key).compact();
    }

    public boolean deleteUser(int id){

        User user = repo.findById(id).orElseThrow(()-> new UserException("user not found to delete"));

        repo.delete(user);

        return true;

    }


}
