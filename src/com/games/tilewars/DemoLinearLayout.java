package com.games.tilewars;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DemoLinearLayout extends LinearLayout {
	
	@SuppressWarnings("unused")
	private static final String TAG = "demo.layout.transition.DemoLinearLayout";

	public DemoLinearLayout(Context context) {
		super(context);
	}

	public DemoLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DemoLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		LayoutTransitionUtil.checkAndSetupChangeAnimationForAllChild(this);
		super.onLayout(changed, l, t, r, b);
	}
}
