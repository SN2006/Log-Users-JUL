package com.example.repository.impl;

import com.example.database.DBConn;
import com.example.entity.User;
import com.example.repository.AppRepository;
import com.example.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AppRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final static String TABLE_USERS = "users";

    @Override
    public String create(User user) {
        String SQL = "INSERT INTO " + TABLE_USERS + " (name, email) VALUES (?, ?)";

        try (PreparedStatement preparedStatement =
                     DBConn.getConnection().prepareStatement(SQL)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());

            preparedStatement.executeUpdate();

            LOGGER.debug(Constants.LOG_DB_INSERT_MSG);
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            LOGGER.error(Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement statement = DBConn.getConnection().createStatement()){
            List<User> users = new ArrayList<>();
            String SQL = "SELECT id, name, email FROM " + TABLE_USERS;

            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }

            LOGGER.debug(Constants.LOG_DB_READ_MSG);
            return Optional.of(users);
        } catch (SQLException e) {
            LOGGER.error(Constants.LOG_DB_ERROR_MSG);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        String SQL = "SELECT id, name, email FROM " + TABLE_USERS + " WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConn.getConnection()
                .prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            User user;
            long userId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");

            if (userId == 0 || name == null || email == null) {
                user = null;
            }else {
                user = new User(userId, name, email);
            }

            LOGGER.debug(Constants.LOG_DB_READ_MSG);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            LOGGER.error(Constants.LOG_DB_ERROR_MSG);
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        String SQL = "UPDATE " + TABLE_USERS + " SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConn.getConnection()
                .prepareStatement(SQL)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setLong(3, user.getId());

            preparedStatement.executeUpdate();

            LOGGER.debug(Constants.LOG_DB_UPDATE_MSG);
            return Constants.DATA_UPDATE_MSG;
        } catch (SQLException e) {
            LOGGER.error(Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    @Override
    public String delete(Long id) {
        String SQL = "DELETE FROM " + TABLE_USERS + " WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConn.getConnection().prepareStatement(SQL)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            LOGGER.debug(Constants.LOG_DB_DELETE_MSG);
            return Constants.DATA_DELETE_MSG;
        } catch (SQLException e) {
            LOGGER.error(Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    public boolean isIdNotExist(Long id) {
        String SQL = "SELECT COUNT(id) FROM "+ TABLE_USERS +" WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConn.getConnection().prepareStatement(SQL)){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return !resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return true;
        }
        return true;
    }
}
