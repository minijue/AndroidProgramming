package com.example.wangjue.imageswitcher;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    private ImageSwitcher imgSwitcher;
    private Button btnView1, btnView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSwitcher = findViewById(R.id.imageSwitcher);
        imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        btnView1 = findViewById(R.id.button2);
        btnView2 = findViewById(R.id.button);

        imgSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        btnView1.setOnClickListener((v) -> {
            Toast.makeText(getApplicationContext(), "View Picture1", Toast.LENGTH_SHORT).show();
            imgSwitcher.setImageResource(R.mipmap.img100);
        });

        btnView2.setOnClickListener((v) -> {
            Toast.makeText(getApplicationContext(), "View Picture2", Toast.LENGTH_SHORT).show();
            imgSwitcher.setImageResource(R.mipmap.img200);
        });
    }
}
