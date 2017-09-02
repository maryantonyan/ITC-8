package com.itc.iblog;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ImageView;


/**
 * Created by liana on 8/30/17.
 */

    public class SplashActivity extends AppCompatActivity {
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            Window window = getWindow();
            window.setFormat(PixelFormat.RGBA_8888);
        }

        Thread splashTread;
        ImageView imageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splashscreen);
            StartAnimations();
        }

        private void StartAnimations() {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
            animation.reset();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_lay);
            linearLayout.clearAnimation();
            linearLayout.startAnimation(animation);
            animation = AnimationUtils.loadAnimation(this, R.anim.translate);
            animation.reset();
            ImageView imageView = (ImageView) findViewById(R.id.splash_img);
            imageView.clearAnimation();
            imageView.startAnimation(animation);

            splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        // Splash screen pause time
                        while (waited < 3500) {
                            sleep(100);
                            waited += 100;
                        }
                        Intent intent = new Intent(SplashActivity.this,
                                LoginRegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    } catch (InterruptedException e) {
                        // do nothing
                    } finally {
                        SplashActivity.this.finish();
                    }

                }
            };
            splashTread.start();
        }

    }
