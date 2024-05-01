package org.example.multitenanttest.service;

import lombok.RequiredArgsConstructor;
import org.example.multitenanttest.data.entity.User;
import org.example.multitenanttest.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;


    public User getUserById(Long id){
        return repository.findById(id).orElse(null);
    }

    public User saveUser(User user){
        return repository.save(user);
    }
}
