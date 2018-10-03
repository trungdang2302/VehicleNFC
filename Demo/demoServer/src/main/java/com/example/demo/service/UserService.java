package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
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

    public void updateUser(User user) {
        Optional<User> userDB = userRepository.findById(user.getId());
        if (userDB.isPresent()) {
            User existedUser = userDB.get();
            user.setPassword(existedUser.getPassword());
            userRepository.save(user);
        }
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhoneNumber(phone);

    }

    public Page<User> getAllUser(Integer page, Integer numOfRows) {
        return userRepository.findAll(new PageRequest(page, numOfRows));
    }

    public void deleteUser(Integer id) {
         userRepository.deleteById(id);
    }
    public String hashID(Integer id) {
        ByteBuffer b = ByteBuffer.allocate(4);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(id);
        byte[] result = b.array();
        return Base64.getEncoder().withoutPadding().encodeToString(result);
    }

}
