package com.example.hp.malakproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText FirstNameText;
    private EditText LastNameText;
    private SharedPreferences sharedPreferences;
    private Button RegisterButton;
    private EditText EmailText;
    private EditText PasswordText;
    private EditText ConfirmPasswordText;
    private CheckBox ShowPasswordButton;

    private Pattern NamePattern = Pattern.compile("^" +
            "[a-zA-Z]*" +
            "$");


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{7,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EmailText = findViewById(R.id.Email);
        PasswordText = findViewById(R.id.Password);
        RegisterButton = findViewById(R.id.Register);
        FirstNameText = findViewById(R.id.FirstName);
        LastNameText = findViewById(R.id.LastName);
        ConfirmPasswordText = findViewById(R.id.ConfirmPassword);
        ShowPasswordButton = findViewById(R.id.showpasswords);

        EmailText.addTextChangedListener(RegisterTextWatcher);
        PasswordText.addTextChangedListener(RegisterTextWatcher);
        ConfirmPasswordText.addTextChangedListener(RegisterTextWatcher);

        FirstNameText.addTextChangedListener(RegisterTextWatcher);
        LastNameText.addTextChangedListener(RegisterTextWatcher);
        ShowPasswordButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    PasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ConfirmPasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


                } else {
                    PasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ConfirmPasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("UserInformation", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("FirstNameKey", FirstNameText.getText().toString());
                editor.putString("LastNameKey", LastNameText.getText().toString());
                editor.putString("EmailKey", EmailText.getText().toString());
                editor.putString("PasswordKey", PasswordText.getText().toString());
                editor.putBoolean("RememberMeKey", false);

                editor.commit();
                Toast.makeText(getApplicationContext(), "Successful SignUp!", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("Email", EmailText.getText().toString());
                i.putExtras(b);
                b.putString("Password", PasswordText.getText().toString());
                i.putExtras(b);

                startActivity(i);

            }
        });

    }

    private TextWatcher RegisterTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String EmailInput = EmailText.getText().toString().trim();
            String PasswordInput = PasswordText.getText().toString().trim();
            String FirstNameInput = FirstNameText.getText().toString().trim();
            String LastNameInput = LastNameText.getText().toString().trim();

            String ConfirmPasswordInput = ConfirmPasswordText.getText().toString().trim();
            boolean FirstNameCheckFlag = IsValidName(FirstNameInput, "First");
            boolean LastNameCheckFlag = IsValidName(LastNameInput, "Last");
            boolean EmailCheckFlag = IsValidEmail(EmailInput);
            boolean PasswordCheckFlag = IsValidPassword(PasswordInput, ConfirmPasswordInput);


            RegisterButton.setEnabled(FirstNameCheckFlag && LastNameCheckFlag && EmailCheckFlag && PasswordCheckFlag

            );


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

    private boolean IsValidName(String Name, String Title) {
        EditText Text;
        if (Title.equals("First")) {
            Text = findViewById(R.id.FirstName);
        } else {
            Text = findViewById(R.id.LastName);

        }
        if (Name.isEmpty()) {
            Text.setError("Name Can't be empty");
            return false;
        } else {
            if (NamePattern.matcher(Name).matches()) {
                if (Name.length() <= 2) {
                    Text.setError("Name should be more than 2 characters");
                    return false;

                }
                Text.setError(null);
                return true;

            } else {
                Text.setError("Numbers aren't allowed");

                return false;
            }

        }


    }

    private boolean IsValidPassword(String Password, String ConfirmPassword) {


        if (Password.isEmpty()) {
            PasswordText.setError("Password Can't be empty");
            return false;
        } else {
            if (!PASSWORD_PATTERN.matcher(Password).matches()) {
                PasswordText.setError("Password too Weak");


                return false;


            } else {

                if (Password.equals(ConfirmPassword)) {
                    PasswordText.setError(null);
                } else {
                    ConfirmPasswordText.setError("Mismatch passwords");
                    return false;
                }
                return true;

            }
        }
    }

}





