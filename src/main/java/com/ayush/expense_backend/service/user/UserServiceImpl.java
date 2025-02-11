package com.ayush.expense_backend.service.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.UserDto;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.AlreadyExistsException;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.request.AddNewUserRequest;
import com.ayush.expense_backend.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User registerUser(AddNewUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setUsername(req.getUsername());
                    user.setPassword(req.getPassword());
                    user.setEmail(req.getEmail());
                    user.setCreatedAt(LocalDateTime.now());
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(
                        "User with email :" + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long user_id) {
        return userRepository.findById(user_id).map(existingUser -> {
            existingUser.setUsername(request.getUsername());
            existingUser.setPassword(request.getPassword());
            existingUser.setEmail(request.getEmail());
            existingUser.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new NoDataFoundException("User with id :" + user_id + " not found!"));
    }

    @Override
    public void deleteUser(Long user_id) {
        userRepository.findById(user_id).ifPresentOrElse(userRepository :: delete, () ->{
            throw new NoDataFoundException("User with id :" + user_id + " not found!");
        });
    }
        

    @Override
    public User getUserById(Long user_id) {
       return userRepository.findById(user_id).orElseThrow( () ->{
        throw new NoDataFoundException("User with id :" + user_id + " not found!");
       });
    }

}
