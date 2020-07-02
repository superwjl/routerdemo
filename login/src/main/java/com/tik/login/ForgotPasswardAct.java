package com.tik.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tik.annotation.Route;

/**
 *
 **/
@Route("login/forgotpassword")
public class ForgotPasswardAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forgot_password);
    }
}
