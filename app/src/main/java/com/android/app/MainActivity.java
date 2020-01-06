package com.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.app.contract.IUser;
import com.android.app.data.UserSingleton;
import com.android.app.data.model.BaseUser;
import com.android.app.presenter.login.UserPresenter;
import com.android.app.utils.PermissionUtils;
import com.android.app.view.chat.ChatsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        new UserPresenter(new IUser.View() {
            @Override
            public void onResult(@Nullable BaseUser user) {

            }
        }).updateOnline();

        PermissionUtils.INSTANCE.requestPermissionWriteStorage(this);
        //startActivity(new Intent(getApplicationContext(), ChatsActivity.class));
    }

}
