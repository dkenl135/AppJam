package com.ironfactory.appjam.controll.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ironfactory.appjam.Global;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.server.RequestInterface;
import com.ironfactory.appjam.server.RequestManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DrawActivity extends BaseActivity {

    private static final String TAG = "DrawActivity";
    private Paint mPaint;
    private Path path;
    private DrawView drawView;
    private PictureDto pictureDto;

    private FrameLayout container;
    private FloatingActionButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        container = (FrameLayout) findViewById(R.id.activity_draw_container);
        saveBtn = (FloatingActionButton) findViewById(R.id.activity_draw_save);
        pictureDto = (PictureDto) getIntent().getSerializableExtra(Global.IMAGE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertImage();
            }
        });
    }


    private void insertImage() {
        drawView.setDrawingCacheEnabled(true);
        Bitmap bitmap = drawView.getDrawingCache(true);
        Log.d(TAG, "bitmap = " + bitmap);

        String imageId = null;
        if (pictureDto != null)
            imageId = pictureDto.imageEntities.get(0).getId();
        else
            imageId = getRandomString();
        File file = new File(getCacheDir().toString());
        Log.d(TAG, "path = " + file.getPath());
        if (!file.exists()) {
            Log.d(TAG,"파일 없소");
            file.mkdir();
        }
        File curFile = new File(getCacheDir(), imageId);
        FileOutputStream outputStream = null;
        try {
            curFile.createNewFile();
            outputStream = new FileOutputStream(curFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            Log.d(TAG, "비트맵 변환 완료");

            RequestManager.insertImage(curFile.getPath(), imageId, Global.userEntity.getId(), "주제", "", new RequestInterface.OnInsertImage() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "비트맵 저장 완료 ");
                }

                @Override
                public void onException() {
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getRandomString() {
        String a = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(a.length());
            sb.append(a.charAt(num));
        }
        return sb.toString();
    }


    @Override
    protected void init(int resId) {
        setContentView(resId);
        setListener();
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void onResume() {
        super.onResume();

        drawView = new DrawView(this);
        container.addView(drawView);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);
    }

    public class DrawView extends View {

        private Canvas canvas;
        private Paint paint;
        private Bitmap bitmap;

        public DrawView(Context context) {
            super(context);
            init();
        }


        private void init() {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            bitmap = Bitmap.createBitmap(metrics.widthPixels,
                    metrics.heightPixels, Bitmap.Config.ARGB_8888);


            canvas = new Canvas(bitmap);
            path = new Path();
            paint = new Paint(Paint.DITHER_FLAG);
            // Matrix matrix;
            // mCanvas.setBitmap(bm);
            // mCanvas.drawBitmap(bm, matrix, 0xFFFFFFFF);
            canvas.drawColor(0xFFFFFFFF); // backgroundcolor
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            canvas.drawPath(path, mPaint);
        }


        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        /**
         */
        private void touch_start(float x, float y) {
            // mPath.reset();
            path.moveTo(x, y);
            mX = x;
            mY = y;
        }

        /**
         */
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        /**
         */
        private void touch_up() {
            path.lineTo(mX, mY);
            canvas.drawPath(path, mPaint);
            // mPath.reset();
        }

        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            float x = event.getX();
            float y = event.getY();
            drawPoint p = new drawPoint(x, y, false);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    break;
            }
            invalidate();
            return true;
        }
    }


    public class drawPoint {
        private float x;
        private float y;
        private boolean draw;

        public drawPoint(float x, float y, boolean d) {
            this.x = x;
            this.y = y;
            draw = d;
        }


        public boolean getDraw() {
            return draw;
        }

        public void setDraw(boolean _draw) {
            draw = _draw;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }
}
