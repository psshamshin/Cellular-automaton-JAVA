package com.example.auto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestSurfaceView(this));
    }
}

//-----------------------------------------------------------------------------//

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    int n = 1980;
    Integer state[][] = new Integer[n][n];



    public static Integer func(int a, int b, int c, String shi) {

        StringBuilder str = new StringBuilder();
        if (a==0&&b==0&&c==0){
            str.append(shi.charAt(0));
        }
        else if (a==0&&b==0&&c==1){
            str.append(shi.charAt(1));

        }
        else if (a==0&&b==1&&c==0){
            str.append(shi.charAt(2));

        }
        else if (a==0&&b==1&&c==1){
            str.append(shi.charAt(3));
        }
        else if (a==1&&b==0&&c==0){
            str.append(shi.charAt(4));
        }
        else if (a==1&&b==0&&c==1){
            str.append(shi.charAt(5));

        }
        else if (a==1&&b==1&&c==0){
            str.append(shi.charAt(6));

        }
        else if (a==1&&b==1&&c==1){
            str.append(shi.charAt(7));
        }

        String string = str.toString();
        int t = Integer.parseInt(string);
        return t;
    }


    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        DrawThread thread = new DrawThread(this.getContext(), surfaceHolder);

        for (int i = 0; i < state.length; i++) {
            Arrays.fill(state[i], 0);
        }

        state[0][n/2]=1;

        //=======================================================
        StringBuilder builder = new StringBuilder();
        //150
        int rule = 161;
        int get;
        //=======================================================

        while(rule !=0 ) {
            get = rule%2;
            builder.append(get);
            rule = rule/2;
        }
        if (builder.length() < 9){
            for (int i=0; i <= 9-builder.length(); i++){
                builder.append("0");
            }
        }

        for (int i = 0; i < state.length-1; i++) {
            for (int j = 1; j < state[i].length-1; j++) {
                state[i+1][j] = func(state[i][j-1], state[i][j], state[i][j+1],builder.toString());
            }
        }

        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }




    class DrawThread extends Thread{
        private SurfaceHolder surfaceHolder;
        private volatile boolean running = true;
        Paint paint = new Paint();

        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }
        public void requestStop(){
            running=false;
        }

        @Override
        public void run() {

            while (running){
                Canvas canvas = surfaceHolder.lockCanvas();
                paint.setColor(Color.GREEN);
                if (canvas != null) {


                    for (int i = 0; i < state.length; i++) {
                        for (int j = 0; j < state[i].length; j++) {
                            if(state[i][j]==0){
                                canvas.drawPoint(i,j,paint);
                            }
                            else {

                            }
                        }
                        System.out.println();
                    }





                    getHolder().unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}