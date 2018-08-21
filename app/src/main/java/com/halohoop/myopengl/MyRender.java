package com.halohoop.myopengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * 点渲染器 绘制螺旋线
 */
public class MyRender implements GLSurfaceView.Renderer {

    private List<Float> coordsList;
    private float ratio;
    private float xrotate;
    private float yrotate;

    public float getXrotate() {
        return xrotate;
    }

    public void setXrotate(float xrotate) {
        this.xrotate = xrotate;
    }

    public float getYrotate() {
        return yrotate;
    }

    public void setYrotate(float yrotate) {
        this.yrotate = yrotate;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //清屏色
        gl10.glClearColor(0f, 0f, 0f, 1f);
        //启用顶点缓冲区数组
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //设置视口
        gl10.glViewport(0, 0, i, i1);
        ratio = (float)i / (float)i1;
        //投影矩阵
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        //加载单位矩阵
        gl10.glLoadIdentity();
        //指定平截头体
        gl10.glFrustumf(-1f, 1f, -ratio, ratio, 3f, 7f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //设置眼球，操作哪一个矩阵
        //清除原色缓冲区
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //设置绘制颜色
        gl10.glColor4f(1f, 0f, 0f, 1f);

        //操作模型视图矩阵
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
        //设置眼球的参数
        GLU.gluLookAt(gl10,
                0f, 0f, 5f,
                0f, 0f, 0f,
                0f, 1f, 0f);


        gl10.glRotatef(xrotate, 1, 0, 0);
        gl10.glRotatef(yrotate, 0, 1, 0);

        float r = 0.5f;//半径
        if (coordsList == null) {
            coordsList = new ArrayList<>();
        } else {
            coordsList.clear();
        }
        float x = 0f, y = 0f, z = 1.5f;
        float zstep = 0.001f;
        for(float alpha = 0f; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI / 256)){
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zstep;
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
            Log.i("Halohoop", "onDrawFrame: x:"+x+" y:" + y + " z:" + z);
        }


        //转换点成为缓冲区
        ByteBuffer ibb = ByteBuffer.allocateDirect(coordsList.size() * 4);//每个浮点数 4 个字节
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();//以浮点缓冲操纵
        for (float f : coordsList) {
            fbb.put(f);
        }
        ibb.position(0);//缓冲区位置定位到0上

        //指定顶点指针
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
        gl10.glDrawArrays(GL10.GL_POINTS, 0, coordsList.size() / 3);




    }


}
