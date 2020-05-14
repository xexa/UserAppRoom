package com.example.userapproom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase  extends RoomDatabase{

    private static UserDatabase instance;
    public abstract UserDao userDao();


    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;
        public  PopulateDBAsyncTask(UserDatabase db){
            userDao = db.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            userDao.insert(new User("Arber Fazlija", "+38349676882", "arberi.fazlija@gmail.com"));
            userDao.insert(new User("Arber Fazlija", "+38349676882", "arberi.fazlija@gmail.com"));
            userDao.insert(new User("Arber Fazlija", "+38349676882", "arberi.fazlija@gmail.com"));
            return null;
        }
    }
}
