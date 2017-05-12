package com.pts80.framework.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 正方形ImageView(height=width)
 * 
 * @author VichanHo
 *
 */
public class SquareImageView2 extends ImageView {

	public SquareImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SquareImageView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareImageView2(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

}
