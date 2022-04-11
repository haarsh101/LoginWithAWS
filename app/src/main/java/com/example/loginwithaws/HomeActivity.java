package com.example.loginwithaws;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginwithaws.context.GlobalContext;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView textView = findViewById(R.id.tv);
        String message = String.format("Hello User\n\nYour name is %s.\n\nYour mobile is %s",
                GlobalContext.currentOnlineUser.getName(),
                GlobalContext.currentOnlineUser.getId());
        textView.setText(message);
    }
}
