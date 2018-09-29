package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhoneNumber(phone);

    }

    public String hashID(Integer id) {
        ByteBuffer b = ByteBuffer.allocate(4);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(id);
        byte[] result = b.array();
        return Base64.getEncoder().withoutPadding().encodeToString(result);
    }
}
