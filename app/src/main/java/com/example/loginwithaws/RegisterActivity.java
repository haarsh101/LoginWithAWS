package com.example.loginwithaws;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginwithaws.api.UserService;
import com.example.loginwithaws.context.GlobalContext;
import com.example.loginwithaws.model.User;
import com.example.loginwithaws.utils.HttpStatus;
import com.example.loginwithaws.utils.InputValidator;
import com.example.loginwithaws.utils.MyRunnable;
import com.fasterxml.jackson.core.JsonProcessingException;

public class RegisterActivity extends AppCompatActivity {
    private Button signup;
    private EditText name, mobile, password;
    private InputValidator inputValidator;
    private UserService userService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputValidator = new InputValidator();
        userService = new UserService();
        signup = findViewById(R.id.register_btn);

        progressDialog = new ProgressDialog(this);
        name = findViewById(R.id.register_username_input);
        mobile = findViewById(R.id.register_phone_number_input);
        password = findViewById(R.id.register_password_input);

        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (name.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!inputValidator.isValidMobile(mobile.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Invalid Phone Number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!inputValidator.isValidPassword(password.getText().toString(), name.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Invalid password. Min 8 and max 25 characters" +
                            ". Should not contain your name." +
                            "Atleast 1 digit and 1 special character", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setTitle("Creating account");
                progressDialog.show();
                userService.signupUser(mobile.getText().toString(), name.getText().toString(),
                        password.getText().toString(),
                        new PostRegisterRunnable());
            }
        });

    }

    public class PostRegisterRunnable extends MyRunnable {

        @Override
        public void run() {
            if (HttpStatus.SC_OK == statusCode) {
                try {
                    User user = GlobalContext.OBJECT_MAPPER.readValue(jsonResponse, User.class);
                    GlobalContext.currentOnlineUser = user;
                    String welcomeText = "Welcome " + user.getName();
                    Toast.makeText(RegisterActivity.this, welcomeText, Toast.LENGTH_SHORT).show();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Signup Error. " + jsonResponse, Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }
    }
}
