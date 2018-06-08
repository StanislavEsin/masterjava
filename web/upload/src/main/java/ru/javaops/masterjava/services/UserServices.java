package ru.javaops.masterjava.services;

import java.util.List;
import ru.javaops.masterjava.persist.model.User;

public interface UserServices {
    void save(List<User> users, int chunkSize);
}