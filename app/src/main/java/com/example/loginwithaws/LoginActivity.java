package com.example.loginwithaws;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginwithaws.api.UserService;
import com.example.loginwithaws.context.GlobalContext;
import com.example.loginwithaws.model.User;
import com.example.loginwithaws.utils.HttpStatus;
import com.example.loginwithaws.utils.MyRunnable;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText email,password;
    private UserService userService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_btn);
        email = findViewById(R.id.login_phone_number_input);
        password = findViewById(R.id.login_password_input);
        userService = new UserService();
        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(email.getText().toString()==null){
                    Toast.makeText(LoginActivity.this, "Please enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.getText().toString()==null){
                    Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Logging you in");
                userService.loginUser(email.getText().toString(),
                        password.getText().toString(),
                        new PostLoginRunnable());
            }
        });

    }

    public class PostLoginRunnable extends MyRunnable {

        @Override
        public void run() {
            if (HttpStatus.SC_OK == statusCode) {
                try {
                    User user = GlobalContext.OBJECT_MAPPER.readValue(jsonResponse, User.class);
                    GlobalContext.currentOnlineUser = user;
                    String welcomeText = "Welcome " + user.getName();
                    Toast.makeText(LoginActivity.this, welcomeText, Toast.LENGTH_SHORT).show();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Login Error. " + jsonResponse, Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }
    }

}
