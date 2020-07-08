package com.example.hp.malakproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText EmailText;
    private EditText PasswordText;
    private CheckBox RememberMe;
    private CheckBox ShowPasswordButton;
    private SharedPreferences sharedPreferences;
    private Button LogInButton;
    private String RedirectedEmail;
    private Bundle bundle;
    private String RedirectedPassword;

    private TextView RegisterationLink;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{7,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailText = findViewById(R.id.email);
        PasswordText = findViewById(R.id.password);
        LogInButton = findViewById(R.id.login);
        ShowPasswordButton = findViewById(R.id.ShowPassword);
        RememberMe = findViewById(R.id.Remember_Me);
        EmailText.addTextChangedListener(loginTextWatcher);
        PasswordText.addTextChangedListener(loginTextWatcher);
        RegisterationLink = findViewById(R.id.Register);
        RegisterationLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bundle = getIntent().getExtras();

        if (bundle != null) {

            RedirectedEmail = bundle.getString("Email");
            RedirectedPassword = bundle.getString("Password");
            EmailText.setText(RedirectedEmail);
            PasswordText.setText(RedirectedPassword);
        }
        sharedPreferences = getApplicationContext().getSharedPreferences("UserInformation", 0);

        boolean RememberMeFlag = sharedPreferences.getBoolean("RememberMeKey", false);
        if (RememberMeFlag == true) {
            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));

        }
        ShowPasswordButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    PasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    PasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        RegisterationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SignUpActivity.class));

            }
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String StoredEmail = sharedPreferences.getString("EmailKey", "");
                String StoredPassword = sharedPreferences.getString("PasswordKey", "");

                if (EmailText.getText().toString().trim().equals(StoredEmail) && PasswordText.getText().toString().trim().equals(StoredPassword)) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("RememberMeKey", RememberMe.isChecked());

                    editor.commit();
                    startActivity(new Intent(MainActivity.this, DashBoardActivity.class));


                    Toast.makeText(getApplicationContext(), "Successful login!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Information,please try again!", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String EmailInput = EmailText.getText().toString().trim();
            String PasswordInput = PasswordText.getText().toString().trim();

            boolean EmailCheckFlag = IsValidEmail(EmailInput);
            boolean PasswordCheckFlag = IsValidPassword(PasswordInput);
            LogInButton.setEnabled(EmailCheckFlag && PasswordCheckFlag);


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean IsValidEmail(String email) {
        if (email.isEmpty()) {
            EmailText.setError("Email Address Can't be empty");
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                EmailText.setError("Invalid email address");


                return false;


            } else {
                EmailText.setError(null);
                return true;

            }
        }
    }

    private boolean IsValidPassword(String Password) {
        if (Password.isEmpty()) {
            PasswordText.setError("Password Can't be empty");
            return false;
        } else {
            if (!PASSWORD_PATTERN.matcher(Password).matches()) {
                PasswordText.setError("Password too Weak");


                return false;


            } else {
                PasswordText.setError(null);
                return true;

            }
        }
    }
}
