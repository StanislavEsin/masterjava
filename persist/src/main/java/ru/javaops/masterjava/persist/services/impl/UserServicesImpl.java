package ru.javaops.masterjava.persist.services.impl;

import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.services.UserServices;
import java.util.List;
import java.util.stream.Stream;

/**
 * UserServicesImpl.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 08.06.2018
 */
public class UserServicesImpl implements UserServices {
    private final UserDao userDao = DBIProvider.getDao(UserDao.class);

    @Override
    public int[] save(List<User> users, int chunkSize) {
        return userDao.insertAll(users, chunkSize);
    }

    @Override
    public Stream<User> getWithLimit(int limit) {
        return userDao.getWithLimit(limit).stream();
    }
}