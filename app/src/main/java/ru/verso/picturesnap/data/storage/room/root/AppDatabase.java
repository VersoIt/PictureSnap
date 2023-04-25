package ru.verso.picturesnap.data.storage.room.root;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.verso.picturesnap.data.storage.room.dao.BankCardClientDAO;
import ru.verso.picturesnap.data.storage.room.dao.BankCardPhotographDAO;
import ru.verso.picturesnap.data.storage.room.dao.ClientDAO;
import ru.verso.picturesnap.data.storage.room.dao.FavoritesDAO;
import ru.verso.picturesnap.data.storage.room.dao.FeedbackDAO;
import ru.verso.picturesnap.data.storage.room.dao.PhotographDAO;
import ru.verso.picturesnap.data.storage.room.dao.PhotographServiceDAO;
import ru.verso.picturesnap.data.storage.room.dao.WorkingDaysDAO;
import ru.verso.picturesnap.data.storage.room.entity.BankCardClientEntity;
import ru.verso.picturesnap.data.storage.room.entity.BankCardPhotographEntity;
import ru.verso.picturesnap.data.storage.room.entity.ClientEntity;
import ru.verso.picturesnap.data.storage.room.entity.CreateFlag;
import ru.verso.picturesnap.data.storage.room.entity.FavoriteEntity;
import ru.verso.picturesnap.data.storage.room.entity.FeedbackEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographServiceEntity;
import ru.verso.picturesnap.data.storage.room.entity.WorkingDaysEntity;

@Database(entities = {PhotographServiceEntity.class,
        PhotographEntity.class,
        WorkingDaysEntity.class,
        FeedbackEntity.class,
        FavoriteEntity.class,
        ClientEntity.class,
        BankCardPhotographEntity.class,
        BankCardClientEntity.class,
        CreateFlag.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract PhotographServiceDAO photographServiceDAO();

    public abstract PhotographDAO photographDAO();

    public abstract BankCardClientDAO bankCardClientDAO();

    public abstract BankCardPhotographDAO bankCardPhotographDAO();

    public abstract ClientDAO clientDAO();

    public abstract FavoritesDAO favoritesDAO();

    public abstract FeedbackDAO feedbackDAO();

    public abstract WorkingDaysDAO workingDaysDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "picturesnap_database")
                            .addCallback(callback)
                            .build();

                    context.deleteDatabase("picturesnap_database");
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);

            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("children_photo_session", "child")));
            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("advertising_photo_session", "advertisement")));
            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("family_photo_session", "family")));
            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("portrait_photo_session", "portrait")));
            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("pregnant_woman_photo_session", "pregnant")));
            databaseWriteExecutor.execute(() -> INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("subject_photo_session", "subject")));
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
