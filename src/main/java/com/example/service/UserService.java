package com.example.service;

import com.example.database.DBCheck;
import com.example.entity.User;
import com.example.entity.UserMapper;
import com.example.exceptions.DBException;
import com.example.exceptions.UserDataException;
import com.example.repository.impl.UserRepository;
import com.example.utils.Constants;
import com.example.utils.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = new UserMapper();
    private final UserValidator userValidator = new UserValidator();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public String create(Map<String, String> data){
        try {
            checkDBExists();
        }catch (DBException e){
            LOGGER.error(Constants.LOG_DB_ABSENT_MSG);
            return e.getMessage();
        }
        Map<String, String> errors = userValidator.validateUserData(data);
        if (!errors.isEmpty()){
            try {
                throw new UserDataException(Constants.INCORRECT_INPUT_MSG);
            } catch (UserDataException e) {
                LOGGER.info(Constants.LOG_DATA_INPUTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        return userRepository.create(userMapper.mapData(data));
    }

    public String read(){
        try {
            checkDBExists();
        }catch (DBException e){
            LOGGER.error(Constants.LOG_DB_ABSENT_MSG);
            return e.getMessage();
        }
        Optional<List<User>> usersOptional = userRepository.read();

        if (usersOptional.isPresent()){
            List<User> users = usersOptional.get();

            if (!users.isEmpty()){
                AtomicInteger counter = new AtomicInteger();
                StringBuilder builder = new StringBuilder();
                users.forEach(user -> builder.append(counter.incrementAndGet())
                        .append(") ")
                        .append(user.toString())
                        .append("\n")
                );
                return builder.toString();
            }
            LOGGER.info(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
        LOGGER.info(Constants.LOG_DATA_ABSENT_MSG);
        return Constants.DATA_ABSENT_MSG;
    }

    public String readById(Map<String, String> data){
        try {
            checkDBExists();
        }catch (DBException e){
            LOGGER.error(Constants.LOG_DB_ABSENT_MSG);
            return e.getMessage();
        }

        Map<String, String> errors = userValidator.validateUserData(data);
        if (!errors.isEmpty()){
            try{
                throw new UserDataException(Constants.INCORRECT_INPUT_MSG);
            } catch (UserDataException e) {
                LOGGER.info(Constants.LOG_DATA_INPUTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        Optional<User> userOptional = userRepository.readById(userMapper.mapData(data).getId());
        if (userOptional.isEmpty()) {
            LOGGER.info(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
        return userOptional.get().toString();
    }

    public String update(Map<String, String> data){
        try {
            checkDBExists();
        }catch (DBException e){
            LOGGER.error(Constants.LOG_DB_ABSENT_MSG);
            return e.getMessage();
        }

        Map<String, String> errors = userValidator.validateUserData(data);
        if (!errors.isEmpty()){
            try {
                throw new UserDataException(Constants.INCORRECT_INPUT_MSG);
            } catch (UserDataException e) {
                LOGGER.info(Constants.LOG_DATA_INPUTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        User user = userMapper.mapData(data);
        if (userRepository.isIdNotExist(user.getId())){
            LOGGER.info(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
        return userRepository.update(user);
    }

    public String delete(Map<String, String> data){
        try {
            checkDBExists();
        }catch (DBException e){
            LOGGER.error(Constants.LOG_DB_ABSENT_MSG);
            return e.getMessage();
        }

        Map<String, String> errors = userValidator.validateUserData(data);
        if (!errors.isEmpty()){
            try{
                throw new UserDataException(Constants.INCORRECT_INPUT_MSG);
            } catch (UserDataException e) {
                LOGGER.info(Constants.LOG_DATA_INPUTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        User user = userMapper.mapData(data);

        if (userRepository.isIdNotExist(user.getId())){
            LOGGER.info(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
        return userRepository.delete(user.getId());
    }

    private void checkDBExists() throws DBException {
        if (DBCheck.isDBExists()) {
            throw new DBException(Constants.DB_ABSENT_MSG);
        }
    }
}
