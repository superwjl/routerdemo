package com.tik.member;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tik.annotation.Route;

/**
 *
 **/
@Route("member/member")
public class MemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_member);
    }
}
