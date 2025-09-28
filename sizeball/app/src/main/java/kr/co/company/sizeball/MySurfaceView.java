package kr.co.company.sizeball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

class Ball
{
    int x, y;
    int diamerter;

    int color;
    static int WIDTH = 1080, HEIGHT = 1920;

    private static final int MAX_DIAMETER = 100;
    private static final int MIN_DIAMETER = 5;

    boolean growing = false; // true면 커지고, false면 작아짐
    boolean grow = false;

    public Ball(int d)
    {
        this.diamerter = d;

        x = (int)(Math.random() * (WIDTH + d) + 3);
        y = (int)(Math.random() * (HEIGHT + d) + 3);

        Random rand = new Random();
        color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void setGrowing(boolean grow)
    {
        growing = grow;
    }

    public void paint (Canvas g)
    {
        Paint paint = new Paint();

        if (growing) {
            if(grow)
            {
                diamerter++;
                if (diamerter >= MAX_DIAMETER) {
                    grow = false; // 최대 크기에 도달하면 줄어들기 시작
                }
            }
            else
            {
                diamerter--;
                if (diamerter <= MIN_DIAMETER) {
                    grow = true; // 최소 크기에 도달하면 다시 커지기 시작
                }
            }
        }

        paint.setColor(color);
        g.drawCircle(x,y,diamerter,paint);
    }

}
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    int size = 0;
    public Ball basket[] = new Ball[20];
    private MyThread thread;

    public MySurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);

        for(int i = 0; i < 20; i++)
        {
            Random random = new Random();
            size = random.nextInt(20)+5;
            basket[i] = new Ball(size);
        }
    }


    public MyThread getThread()
    {
        return thread;
    }


    public void surfaceCreated(SurfaceHolder holder)
    {
        thread.setRunning(true);

        thread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Surface가 변경될 때 실행할 코드
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;

        thread.setRunning(false);
        while(retry)
        {
            try
            {
                thread.join();
                retry = false;
            }catch (InterruptedException e) { }
        }
    }

    public void growAllBalls(boolean grow)
    {
        for (Ball b : basket)
        {
            b.setGrowing(grow);
        }
    }

    public class MyThread extends Thread
    {
        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder surfaceHolder)
        {
            mSurfaceHolder = surfaceHolder;
        }

        public void run()
        {
            while(mRun)
            {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    c.drawColor(Color.BLACK);
                    synchronized (mSurfaceHolder)
                    {
                        for(Ball b : basket)
                        {
                            b.paint(c);
                        }
                    }
                }finally {
                    if(c!=null)
                    {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
        public void setRunning(boolean b)
        {
            mRun = b;
        }
    }

}