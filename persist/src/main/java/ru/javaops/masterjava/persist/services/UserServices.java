package ru.javaops.masterjava.persist.services;

import java.util.List;
import java.util.stream.Stream;

import ru.javaops.masterjava.persist.model.User;

public interface UserServices {
    void save(List<User> users, int chunkSize);
    Stream<User> getWithLimit(int limit);
}