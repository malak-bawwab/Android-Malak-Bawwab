package com.example.hp.malakproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DashBoardActivity extends AppCompatActivity {
    private TextView WelcomeMsg;
    private SharedPreferences sharedPreferences;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        WelcomeMsg = findViewById(R.id.WelcomeMsg);
        logoutButton = findViewById(R.id.logout);
        sharedPreferences = getApplicationContext().getSharedPreferences("UserInformation", 0);
        String StoredFirstName = sharedPreferences.getString("FirstNameKey", "");
        String StoredLastName = sharedPreferences.getString("LastNameKey", "");
        WelcomeMsg.setText("Hello " + StoredFirstName + " " + StoredLastName);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.commit();


                startActivity(new Intent(DashBoardActivity.this, MainActivity.class));

            }
        });


    }
}
