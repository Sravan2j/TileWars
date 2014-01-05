package com.example.tilewars;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "demo.layout.transition.MainActivity";
	private View viewvar= null;
	LinearLayout frm_linear_00 = null;
	TextView t1 = null;
	TextView t2 = null;
	TextView t3 = null;
	TextView t4 = null;
	
	View l1b1=null;
	View l1b1e=null;
	View l1b4=null;
	View l2b1=null;
	View l2b1e=null;
	View l2b4=null;
	
	int tvWidth = 0;
	int tvHeight = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOnClickListenerForChild(R.id.frm_linear_00, true, true);
		setOnClickListenerForChild(R.id.frm_linear_01, true, true);
		setOnClickListenerForChild(R.id.frm_linear_02, true, true);
/*		setOnClickListenerForChild(R.id.frm_linear_03, true, true);
		setOnClickListenerForChild(R.id.frm_linear_04, true, true);*/

		LayoutTransitionUtil.sAnimatorListener = new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
								
				
			}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				ObjectAnimator objectAnimator = (ObjectAnimator) animation;
				if (objectAnimator.getTarget() instanceof TextView) {
					TextView textView = (TextView) objectAnimator.getTarget();
					textView.setTextColor(Color.GREEN);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {}
			
			
		};
		
		/*TextView t1= (TextView) findViewById(R.id.l1b2);
		
		LinearLayout frm_linear_00 = (LinearLayout) findViewById(R.id.frm_linear_00);
		frm_linear_00.setLayoutParams(new LayoutParams(t1.getWidth()*2, t1.getHeight()*2));
		frm_linear_00.setVisibility(View.VISIBLE);
		View b1=(View) findViewById(R.id.l1b1);
		//b1.setLayoutParams(new LayoutParams(t1.getWidth()*2, t1.getHeight()*2));
		b1.setVisibility(View.VISIBLE);
		
		View b2=(View) findViewById(R.id.l2b1);
		//b1.setLayoutParams(new LayoutParams(t1.getWidth()*2, t1.getHeight()*2));
		b2.setVisibility(View.VISIBLE);

		t1.setWidth(t1.getWidth()*2);
		t1.setHeight(t1.getHeight()*2);*/
		/*
		TextView t1= (TextView) findViewById(R.id.l1b2);
		onClick(t1);
		onClick(t1);*/
		
		
		t1 = (TextView) findViewById(R.id.l1b2);
		t2 = (TextView) findViewById(R.id.l1b3);
		t3 = (TextView) findViewById(R.id.l2b2);
		t4 = (TextView) findViewById(R.id.l2b3);
		
		frm_linear_00 = (LinearLayout) findViewById(R.id.frm_linear_00);
		
		l1b1=(View) findViewById(R.id.l1b1);
		l1b1e=(View) findViewById(R.id.l1b1e);
		l1b4=(View) findViewById(R.id.l1b4);
		
		l2b1=(View) findViewById(R.id.l2b1);
		l2b1e=(View) findViewById(R.id.l2b1e);
		l2b4=(View) findViewById(R.id.l2b4);
		
	}

	private void setOnClickListenerForChild(int id, boolean enable, boolean needScroll) {
		ViewGroup viewGroup = (ViewGroup)findViewById(id);

		if(enable){
			if(needScroll){
				LayoutTransitionUtil.enableChangeTransition(viewGroup.getLayoutTransition());
			}else{
				LayoutTransitionUtil.setupChangeAnimationOneTime(viewGroup);
			}

		}
		for(int i = 0;i<viewGroup.getChildCount();i++){
			viewGroup.getChildAt(i).setOnClickListener(this);
		}
	}
	@Override
	public void onClick(View view) {
		if ((view instanceof TextView) && (!view.equals(viewvar)))  {
			final TextView textView = (TextView) view;
			
			//LinearLayout frm_linear_01 = (LinearLayout) findViewById(R.id.frm_linear_01);
			//if(textView.getText().equals("")){
				if (viewvar!=null)
				{
					
					TextView textViewVar = (TextView) viewvar;					
					textViewVar.setWidth(textViewVar.getWidth()/2);
					textViewVar.setHeight(textViewVar.getHeight()/2);
					
				}
				viewvar=view;
				
				if (textView==t1)
				{					
					frm_linear_00.setVisibility(View.VISIBLE);					
					l1b1.setVisibility(View.VISIBLE);										
					l2b1e.setVisibility(View.VISIBLE);		
					l2b1.setVisibility(View.GONE);										
					l2b4.setVisibility(View.GONE);					
					l1b1e.setVisibility(View.GONE);					
					l1b4.setVisibility(View.GONE);
					
					textView.setWidth(textView.getWidth()*2);
					textView.setHeight(textView.getHeight()*2);
					
				}
				
				if (textView==t2)
				{				
					frm_linear_00.setVisibility(View.VISIBLE);
					l1b1.setVisibility(View.GONE);					
					l2b1.setVisibility(View.GONE);					
					l2b1e.setVisibility(View.GONE);										
					l2b4.setVisibility(View.VISIBLE);
					l1b4.setVisibility(View.GONE);
					l1b1e.setVisibility(View.GONE);										
					
					
					textView.setWidth(textView.getWidth()*2);
					textView.setHeight(textView.getHeight()*2);
				}
				
				
				if (textView==t3)
				{				
					frm_linear_00.setVisibility(View.GONE);
																								
					l1b1e.setVisibility(View.VISIBLE);
					//l1b4.setVisibility(View.GONE);
					l2b1.setVisibility(View.VISIBLE);					
					l2b1e.setVisibility(View.GONE);
					l1b1.setVisibility(View.GONE);
					//l2b4.setVisibility(View.GONE);																	
					textView.setWidth(textView.getWidth()*2);
					textView.setHeight(textView.getHeight()*2);
					
				}
				
				if (textView==t4)
				{				
					frm_linear_00.setVisibility(View.GONE);
					l1b1.setVisibility(View.GONE);
					l1b1e.setVisibility(View.GONE);	
					
					l2b1.setVisibility(View.GONE);					
					l2b1e.setVisibility(View.GONE);
					l1b4.setVisibility(View.VISIBLE);
					
					l2b4.setVisibility(View.GONE);													
					
					textView.setWidth(textView.getWidth()*2);
					textView.setHeight(textView.getHeight()*2);
					
				}
				
				
				//textView.setText("long long long text");
				
				/*LayoutParams lp = new LayoutParams(textView.getWidth(), textView.getHeight());
				textView.setLayoutParams(lp);*/
				
				/*View b1=(View) findViewById(R.id.b1);
				b1.setLayoutParams(new LayoutParams(textView.getWidth()*2, textView.getHeight()*2));
				b1.setVisibility(View.VISIBLE);*/
				
				/*LinearLayout frm_linear_00 = (LinearLayout) findViewById(R.id.frm_linear_00);
				frm_linear_00.setLayoutParams(new LayoutParams(textView.getWidth()*2, textView.getHeight()*2));
				frm_linear_00.setVisibility(View.VISIBLE);*/
				
				/*Animation animation = new TranslateAnimation(0,textView.getWidth(), 0,textView.getHeight());
				animation.setDuration(1000);
				animation.setZAdjustment(Animation.ZORDER_TOP);
				textView.startAnimation(animation);*/
				
				
				
				//TextView t1= (TextView) findViewById(R.id.l1b2);
				
				
				
				/*PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", textView.getWidth());
				PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", textView.getHeight());									
				ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(textView, pvhX, pvhY).setDuration(2000);
				changeIn.addListener(new AnimatorListenerAdapter() {
					public void onAnimationEnd(Animator anim) {
						textView.setWidth(textView.getWidth()*2);
						textView.setHeight(textView.getHeight()*2);
					}
				});

				changeIn.start();
				*/

				

			/*}else{
				textView.setText("");
			}*/
			//textView.setTextColor(Color.BLUE);
		};
		//LayoutTransitionUtil.setupChangeAnimation((ViewGroup)findViewById(R.id.frm_linear_04));
	}
}
