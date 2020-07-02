package com.tik.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tik.annotation.Route;
import com.tik.arouter.ARouter;

/**
 *
 **/
@Route("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
    }

    public void toMember(View view) {
        ARouter.getInstance().jumpActivity("member/member", null);
    }
}
