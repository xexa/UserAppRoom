package com.example.userapproom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        UserDatabase userDatabase = UserDatabase.getInstance(application);

        userDao = userDatabase.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteAsyncTask(userDao).execute(user);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(userDao).execute();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public InsertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    public class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public UpdateAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    public class DeleteAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public DeleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    public class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private UserDao userDao;

        public DeleteAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteALl();
            return null;
        }
    }
}
