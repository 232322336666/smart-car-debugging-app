package com.example.ui_basetestdemo_food;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by 79463 on 2019/6/6.
 */

public class SmallBall extends View {
    public float bitmapX;
    public float bitmapY;

    public SmallBall(Context context) {
        super(context);
        bitmapX=0;
        bitmapY=200;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint =new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.mipmap.smallball1);
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);
        if(bitmap.isRecycled()){
            bitmap.recycle();
        }

    }
}
