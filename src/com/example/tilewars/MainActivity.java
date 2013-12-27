package com.example.tilewars;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;

@SuppressLint({ "ShowToast", "NewApi" })
public class MainActivity extends Activity implements FlipCompleteListener{
	private Button startButton;
	private Button pauseButton;

	private TextView timerValue;

	private long startTime = 0L;

	private Handler customHandler = new Handler();

	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	
	int[] TextViewids = { R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15,
			R.id.tv21, R.id.tv22, R.id.tv23, R.id.tv24, R.id.tv25,
			R.id.tv31, R.id.tv32, R.id.tv33, R.id.tv34, R.id.tv35,
			R.id.tv41, R.id.tv42, R.id.tv43, R.id.tv44, R.id.tv45,
			R.id.tv51, R.id.tv52, R.id.tv53, R.id.tv54, R.id.tv55};;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnimationFactory.setFlipCompleteListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			setContentView(R.layout.apilt11_main);
		}
		else
		{
			//setContentView(R.layout.apilt11_main);
			setContentView(R.layout.activity_main);
		}
		
		/*final ViewFlipper vf11 = (ViewFlipper)findViewById(R.id.vf11);
		final ViewFlipper vf12 = (ViewFlipper)findViewById(R.id.vf12);
		final ViewFlipper vf13 = (ViewFlipper)findViewById(R.id.vf13);
		final ViewFlipper vf14 = (ViewFlipper)findViewById(R.id.vf14);
		final ViewFlipper vf15 = (ViewFlipper)findViewById(R.id.vf15);
		final ViewFlipper vf21 = (ViewFlipper)findViewById(R.id.vf21);
		final ViewFlipper vf22 = (ViewFlipper)findViewById(R.id.vf22);
		final ViewFlipper vf23 = (ViewFlipper)findViewById(R.id.vf23);
		final ViewFlipper vf24 = (ViewFlipper)findViewById(R.id.vf24);
		final ViewFlipper vf25 = (ViewFlipper)findViewById(R.id.vf25);
		final ViewFlipper vf31 = (ViewFlipper)findViewById(R.id.vf31);
		final ViewFlipper vf32 = (ViewFlipper)findViewById(R.id.vf32);
		final ViewFlipper vf33 = (ViewFlipper)findViewById(R.id.vf33);
		final ViewFlipper vf34 = (ViewFlipper)findViewById(R.id.vf34);
		final ViewFlipper vf35 = (ViewFlipper)findViewById(R.id.vf35);
		final ViewFlipper vf41 = (ViewFlipper)findViewById(R.id.vf41);
		final ViewFlipper vf42 = (ViewFlipper)findViewById(R.id.vf42);
		final ViewFlipper vf43 = (ViewFlipper)findViewById(R.id.vf43);
		final ViewFlipper vf44 = (ViewFlipper)findViewById(R.id.vf44);
		final ViewFlipper vf45 = (ViewFlipper)findViewById(R.id.vf45);
		final ViewFlipper vf51 = (ViewFlipper)findViewById(R.id.vf51);
		final ViewFlipper vf52 = (ViewFlipper)findViewById(R.id.vf52);
		final ViewFlipper vf53 = (ViewFlipper)findViewById(R.id.vf53);
		final ViewFlipper vf54 = (ViewFlipper)findViewById(R.id.vf54);
		final ViewFlipper vf55 = (ViewFlipper)findViewById(R.id.vf55);*/

		


		int[] TextViewbids = { R.id.tvb11, R.id.tvb12, R.id.tvb13, R.id.tvb14, R.id.tvb15,
				R.id.tvb21, R.id.tvb22, R.id.tvb23, R.id.tvb24, R.id.tvb25,
				R.id.tvb31, R.id.tvb32, R.id.tvb33, R.id.tvb34, R.id.tvb35,
				R.id.tvb41, R.id.tvb42, R.id.tvb43, R.id.tvb44, R.id.tvb45,
				R.id.tvb51, R.id.tvb52, R.id.tvb53, R.id.tvb54, R.id.tvb55};

		/*for (int i = 0; i < TextViewids.length; i++) {

			TextView t1= (TextView)(findViewById(TextViewids[i]));
			t1.setHeight(t1.getWidth());
		}
		for (int i = 0; i < TextViewbids.length; i++) {

			TextView t1= (TextView)(findViewById(TextViewbids[i]));
			t1.setHeight(t1.getWidth());
		}*/

		final int[] ViewFlipperids = { R.id.vf11, R.id.vf12, R.id.vf13, R.id.vf14, R.id.vf15,
				R.id.vf21, R.id.vf22, R.id.vf23, R.id.vf24, R.id.vf25,
				R.id.vf31, R.id.vf32, R.id.vf33, R.id.vf34, R.id.vf35,
				R.id.vf41, R.id.vf42, R.id.vf43, R.id.vf44, R.id.vf45,
				R.id.vf51, R.id.vf52, R.id.vf53, R.id.vf54, R.id.vf55};
		for (int i = 0; i < TextViewids.length; i++) {
			final int choiceIndex = i;
			findViewById(TextViewids[i]).setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {

					AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[choiceIndex]), FlipDirection.LEFT_RIGHT);
					int[] location = new int[2];
					((TextView)findViewById(TextViewids[choiceIndex])).getLocationInWindow(location);
					//Toast.makeText(MainActivity.this, location[0]+""+location[1] +"", Toast.LENGTH_SHORT).show();
					//Toast.makeText(MainActivity.this, ((TextView)findViewById(TextViewids[choiceIndex])).getX()+"", Toast.LENGTH_SHORT).show();
					/*Animation animation = new TranslateAnimation(0,-location[0], 0,-location[1]);
					animation.setDuration(1000);

					animation.setZAdjustment(Animation.ZORDER_TOP);
					animation.setFillAfter(true);
					((TextView)findViewById(TextViewids[choiceIndex])).startAnimation(animation);*/

					/*ObjectAnimator transAnimation= ObjectAnimator.ofFloat((TextView)findViewById(TextViewids[choiceIndex]), "translationX", 0, -100);

					transAnimation.setDuration(3000);//set duration


					//findViewById(TextViewids[choiceIndex]).animate().x(-location[0]).y(-location[1]).setDuration(2000);
					transAnimation.start();//start animation
					 */					
					/*PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", -location[0]-2);
				    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", -location[1]-2);
				    ObjectAnimator.ofPropertyValuesHolder(findViewById(TextViewids[choiceIndex]), pvhX, pvhY).setDuration(2000).start();*/


				}
			});
			findViewById(TextViewbids[i]).setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {					
					AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[choiceIndex]), FlipDirection.LEFT_RIGHT);

				}
			});
		}



		Button okButton = (Button) findViewById(R.id.okButton);

		okButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[15]), FlipDirection.LEFT_RIGHT);

				int[] location = new int[2];
				((TextView)findViewById(TextViewids[15])).getLocationInWindow(location);

				int[] location1 = new int[2];
				((TextView)findViewById(TextViewids[3])).getLocationInWindow(location1);

				Animation animation = new TranslateAnimation(0,location[0]-location1[0], 0,location[1]-location1[1]);
				animation.setDuration(1000);
				animation.setZAdjustment(Animation.ZORDER_TOP);
				((TextView)findViewById(TextViewids[3])).startAnimation(animation);


				Animation animation1 = new TranslateAnimation(0,-location[0]+location1[0], 0,-location[1]+location1[1]);
				animation1.setDuration(1000);
				((TextView)findViewById(TextViewids[15])).startAnimation(animation1);

			}
		});



		timerValue = (TextView) findViewById(R.id.timerValue);
		startButton = (Button) findViewById(R.id.startButton);
		pauseButton = (Button) findViewById(R.id.pauseButton);

		pauseButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startTime = SystemClock.uptimeMillis();
				customHandler.postDelayed(updateTimerThread, 0);

			}
		});



		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);

			}
		});

	}







	private Runnable updateTimerThread = new Runnable() {

		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int milliseconds = (int) (updatedTime % 1000);
			timerValue.setText("" + mins + ":"
					+ String.format("%02d", secs) + ":"
					+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);
		}

	};



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public void flipOutComplete() {
		// TODO Auto-generated method stub
		
/*		int[] location = new int[2];
		((TextView)findViewById(TextViewids[15])).getLocationInWindow(location);

		int[] location1 = new int[2];
		((TextView)findViewById(TextViewids[3])).getLocationInWindow(location1);

		Animation animation = new TranslateAnimation(0,location[0]-location1[0], 0,location[1]-location1[1]);
		animation.setDuration(1000);
		animation.setZAdjustment(Animation.ZORDER_TOP);
		((TextView)findViewById(TextViewids[3])).startAnimation(animation);


		Animation animation1 = new TranslateAnimation(0,-location[0]+location1[0], 0,-location[1]+location1[1]);
		animation1.setDuration(1000);
		((TextView)findViewById(TextViewids[15])).startAnimation(animation1);*/

	}



	@Override
	public void flipInComplete() {
		// TODO Auto-generated method stub

	}
}
