package com.diaochan.aop.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.diaochan.aop.login.annotation.ClickBehavior;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "diaochan >>>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 登录点击事件 （用户行为统计）
    @ClickBehavior("登录")
    public void login(View view) {
        Log.d(TAG, "模拟接口请求...验证通过，登录成功~！");
    }

    //用户行为统计
    @ClickBehavior("我的专区")
    public void area(View view) {
        startActivity(new Intent(MainActivity.this, OtherActivity.class));
    }

    //用户行为统计
    @ClickBehavior("我的优惠券")
    public void coupon(View view) {
        startActivity(new Intent(MainActivity.this, OtherActivity.class));
    }

    //用户行为统计
    @ClickBehavior("我的积分")
    public void score(View view) {
        startActivity(new Intent(MainActivity.this, OtherActivity.class));
    }
}
