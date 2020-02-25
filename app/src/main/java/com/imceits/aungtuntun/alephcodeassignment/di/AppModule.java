package com.imceits.aungtuntun.alephcodeassignment.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.imceits.aungtuntun.alephcodeassignment.R;
import com.imceits.aungtuntun.alephcodeassignment.data.ToolDatabase;
import com.imceits.aungtuntun.alephcodeassignment.data.ToolRepository;
import com.imceits.aungtuntun.alephcodeassignment.data.ToolsRentDao;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Friends;
import com.imceits.aungtuntun.alephcodeassignment.data.entity.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class})
class AppModule {
    private ToolDatabase toolDatabase;
    @Singleton
    @Provides
    ToolDatabase provideToolDatabase(Application application) {
        toolDatabase = Room.databaseBuilder(application, ToolDatabase.class, "tools.db")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        int NUMBER_OF_THREADS = 4;
                        ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
                        executors.execute(() -> {
                            ToolsRentDao toolsRentDao = toolDatabase.toolsRentDao();
                            // insert default data, friends
                            List<Friends> friendsList = getFriendList();
                            Friends[] friends = new Friends[friendsList.size()];
                            friendsList.toArray(friends);
                            toolsRentDao.insertFriends( friends);
                            // insert default data, Tools
                            List<Tools> toolsList = getToolsList();
                            Tools[] tools = new Tools[toolsList.size()];
                            toolsList.toArray(tools);
                            toolsRentDao.insert(tools);
                        });

                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .build();
        return toolDatabase;
    }

    @Singleton
    @Provides
    ToolsRentDao provideToolsRentDao(ToolDatabase db) {
        return db.toolsRentDao();
    }

    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(4);
    }

    @Singleton
    @Provides
    ToolRepository provideToolRepository(ToolsRentDao toolsRentDao, ExecutorService executorService) {
        return new ToolRepository(toolsRentDao, executorService);
    }

    private List<Friends> getFriendList() {
        String[] names = new String[]{"Brian", "Luke", "Letty", "Shaw", "Parker"};
        List<Friends> friendsList = new ArrayList<>();
        for(String name : names) {
            Friends newFriend = new Friends(name);
            friendsList.add(newFriend);
        }
        return friendsList;
    }

    private List<Tools> getToolsList() {
        String[] names = new String[]{"Wrench", "Cutter", "Pliers", "Screwdriver", "Welding machine",
                "Welding glasses", "Hammer", "Measuring Tape", "Alan key set", "Air compressor"};
        int[] counts = new int[] {6, 15, 12, 13, 3, 7, 4, 9, 4, 2};
        int[] resIds = new int[] {R.drawable.ic_wrench, R.drawable.ic_cutter, R.drawable.ic_plier,
                R.drawable.ic_screw_driver, R.drawable.ic_welding_machine, R.drawable.ic_welding_glass,
                R.drawable.ic_hammer, R.drawable.ic_measuring_tape, R.drawable.ic_allen_key_set, R.drawable.ic_air_compressor};
        List<Tools> toolsList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Tools tools = new Tools(names[i], String.valueOf(resIds[i]), counts[i]);
            toolsList.add(tools);
        }
        return toolsList;
    }
}
