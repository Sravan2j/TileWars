package com.games.tilewars;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


@SuppressLint("NewApi")
public class SquareLayout extends LinearLayout {

	public SquareLayout(Context context) {
	    this(context, null);
	}

	public SquareLayout(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	}
	
	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    // real work here
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
     
        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);
        
            // or you can use this if you want the square to use height as it basis
            // super.onMeasure(heightMeasureSpec, heightMeasureSpec); 
    }
}