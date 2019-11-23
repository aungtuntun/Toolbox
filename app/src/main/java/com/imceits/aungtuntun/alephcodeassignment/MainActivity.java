package com.imceits.aungtuntun.alephcodeassignment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imceits.aungtuntun.alephcodeassignment.utils.NavigationExtensions;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    private LiveData<NavController> currentNavController = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            setBottomNavigationView();
        }
    }

    private void setBottomNavigationView() {
        // for each own navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        List<Integer> graphs = Arrays.asList(R.navigation.nav_tool, R.navigation.nav_friend);
        LiveData<NavController> controllers = NavigationExtensions.setupWithNavController(bottomNavigationView, graphs, getSupportFragmentManager(),
                R.id.nav_host_fragment, getIntent());
        controllers.observe(this, navController -> NavigationUI.setupActionBarWithNavController(MainActivity.this, navController));
        currentNavController = controllers;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return currentNavController != null && (currentNavController.getValue() != null && currentNavController.getValue().navigateUp());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setBottomNavigationView();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
