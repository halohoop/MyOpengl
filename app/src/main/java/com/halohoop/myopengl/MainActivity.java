package com.halohoop.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private MyRender renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        renderer = new MyRender();
        myView.setRenderer(renderer);

        setContentView(myView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float step = 5f;
        renderer.setXrotate(renderer.getXrotate() - step);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        float step = 5f;

        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            renderer.setXrotate(renderer.getXrotate() - step);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            renderer.setXrotate(renderer.getXrotate() + step);

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            renderer.setYrotate(renderer.getYrotate() - step);

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            renderer.setYrotate(renderer.getYrotate() + step);

        }



        return super.onKeyDown(keyCode, event);
    }

    class MyView extends GLSurfaceView {

        public MyView(Context context) {
            super(context);
        }



    }
}
