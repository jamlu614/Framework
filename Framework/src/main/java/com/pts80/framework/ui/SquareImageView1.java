package com.pts80.framework.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 正方形ImageView(width=height)
 * 
 * @author VichanHo
 *
 */
public class SquareImageView1 extends ImageView {

	public SquareImageView1(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SquareImageView1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareImageView1(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, heightMeasureSpec);
	}

}
