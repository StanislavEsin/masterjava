package ru.javaops.masterjava.services.impl;

import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.services.UserServices;
import java.util.List;

/**
 * UserServicesImpl.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 08.06.2018
 */
public class UserServicesImpl implements UserServices {
    private final UserDao userDao = DBIProvider.getDao(UserDao.class);

    @Override
    public void save(List<User> users, int chunkSize) {
        userDao.insertAll(users, chunkSize);
    }
}