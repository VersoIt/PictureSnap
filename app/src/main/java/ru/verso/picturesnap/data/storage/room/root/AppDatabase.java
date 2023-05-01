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
import ru.verso.picturesnap.data.storage.room.dao.PortfolioImageDAO;
import ru.verso.picturesnap.data.storage.room.dao.ServiceProvisionEntityDAO;
import ru.verso.picturesnap.data.storage.room.dao.WorkingDaysDAO;
import ru.verso.picturesnap.data.storage.room.entity.BankCardClientEntity;
import ru.verso.picturesnap.data.storage.room.entity.BankCardPhotographEntity;
import ru.verso.picturesnap.data.storage.room.entity.ClientEntity;
import ru.verso.picturesnap.data.storage.room.entity.CreateFlag;
import ru.verso.picturesnap.data.storage.room.entity.FavoriteEntity;
import ru.verso.picturesnap.data.storage.room.entity.FeedbackEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographServiceEntity;
import ru.verso.picturesnap.data.storage.room.entity.PortfolioImageEntity;
import ru.verso.picturesnap.data.storage.room.entity.ServiceProvisionEntity;
import ru.verso.picturesnap.data.storage.room.entity.WorkingDayEntity;

@Database(entities = {PhotographServiceEntity.class,
        PhotographEntity.class,
        WorkingDayEntity.class,
        FeedbackEntity.class,
        FavoriteEntity.class,
        ClientEntity.class,
        BankCardPhotographEntity.class,
        ServiceProvisionEntity.class,
        BankCardClientEntity.class,
        PortfolioImageEntity.class,
        CreateFlag.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract PhotographServiceDAO photographServiceDAO();

    public abstract ServiceProvisionEntityDAO serviceProvisionDAO();

    public abstract PortfolioImageDAO portfolioImageDAO();

    public abstract PhotographDAO photographDAO();

    public abstract BankCardClientDAO bankCardClientDAO();

    public abstract BankCardPhotographDAO bankCardPhotographDAO();

    public abstract ClientDAO clientDAO();

    public abstract FavoritesDAO favoritesDAO();

    public abstract FeedbackDAO feedbackDAO();

    public abstract WorkingDaysDAO workingDaysDAO();

    private static volatile AppDatabase INSTANCE;

    public static final String DATABASE_NAME = "picturesnap_database";

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .build();

                    //context.deleteDatabase(DATABASE_NAME);
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);

            databaseWriteExecutor.execute(() -> {
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("children_photo_session", "child"));
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("advertising_photo_session", "advertisement"));
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("family_photo_session", "family"));
            });

            databaseWriteExecutor.execute(() -> {
                WorkingDayEntity.Builder workingDay = new WorkingDayEntity.Builder();
                workingDay
                        .setDayId(2)
                        .setMinuteStart(10)
                        .setMinuteEnd(30)
                        .setHourStart(10)
                        .setHourEnd(20)
                        .setPhotographId(1);

                INSTANCE.workingDaysDAO().addWorkingDay(workingDay.create());
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("portrait_photo_session", "portrait"));
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("pregnant_woman_photo_session", "pregnant"));
                INSTANCE.photographServiceDAO().addNewService(new PhotographServiceEntity("subject_photo_session", "subject"));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 1, 1, 1000));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 1, 2, 5000));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 1, 3, 1200));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 2, 4, 1500));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 2, 5, 5000));
                INSTANCE.serviceProvisionDAO().addServiceProvisionEntity(new ServiceProvisionEntity(0, 2, 0, 2000));
            });

            databaseWriteExecutor.execute(() -> {

                PhotographEntity.Builder firstBuilder = new PhotographEntity.Builder();
                PhotographEntity first = firstBuilder.setName("Иван", "Иванов").setDescription("Добро пожаловать!").setLocation(55.661445f, 37.477049f).setEmail("email@email.com").setExperience(1).setBankCardId(312).setPhoneNumber("+7 (777) 777-77-77").setRating(4.5f).create();
                PhotographEntity.Builder secondBuilder = new PhotographEntity.Builder();
                PhotographEntity second = secondBuilder.setName("Ирина", "Иванова").setDescription("Добро пожаловать!").setLocation(55.661445f, 37.477049f).setEmail("email_email@email.com").setExperience(2).setBankCardId(312).setPhoneNumber("+7 (777) 777-77-77").setRating(3.3f).create();

                INSTANCE.photographDAO().addPhotograph(first);
                INSTANCE.photographDAO().addPhotograph(second);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
