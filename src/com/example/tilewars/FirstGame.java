package com.example.tilewars;


import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;


@SuppressLint({ "ShowToast", "NewApi" })
public class FirstGame extends Activity implements FlipCompleteListener{
	private TextView startButton;
	private TextView rulesButton;

	private TextView timerValue;
	private TextView maxTimerTV;

	private long startTime = 0L;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	int sleepValue = 600;
	//int maxCells=1;
	//int userClick=0;
	int count=0;
	String maxTimeValue="";
	String currentGameTimeValue="";
	int maxTimeMillisVal=0;
	int currTimeMillisVal=0;

	private Handler customHandler = new Handler();
	private Handler flipHandler = new Handler();


	int greenColor=Color.parseColor("#75DB1B");
	boolean flag=false;

	int[] TextViewids = { R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15, R.id.tv16,
			R.id.tv21, R.id.tv22, R.id.tv23, R.id.tv24, R.id.tv25, R.id.tv26,
			R.id.tv31, R.id.tv32, R.id.tv33, R.id.tv34, R.id.tv35, R.id.tv36,
			R.id.tv41, R.id.tv42, R.id.tv43, R.id.tv44, R.id.tv45, R.id.tv46,
			R.id.tv51, R.id.tv52, R.id.tv53, R.id.tv54, R.id.tv55, R.id.tv56,
			R.id.tv61, R.id.tv62, R.id.tv63, R.id.tv64, R.id.tv65, R.id.tv66}; 

	boolean[] isFlipped = { false, false, false, false, false, false,
			false, false, false, false, false, false,
			false, false, false, false, false, false,
			false, false, false, false, false, false,
			false, false, false, false, false, false,
			false, false, false, false, false, false};


	final int[] ViewFlipperids = { R.id.vf11, R.id.vf12, R.id.vf13, R.id.vf14, R.id.vf15, R.id.vf16,
			R.id.vf21, R.id.vf22, R.id.vf23, R.id.vf24, R.id.vf25,R.id.vf26,
			R.id.vf31, R.id.vf32, R.id.vf33, R.id.vf34, R.id.vf35,R.id.vf36,
			R.id.vf41, R.id.vf42, R.id.vf43, R.id.vf44, R.id.vf45,R.id.vf46,
			R.id.vf51, R.id.vf52, R.id.vf53, R.id.vf54, R.id.vf55,R.id.vf56,
			R.id.vf61, R.id.vf62, R.id.vf63, R.id.vf64, R.id.vf65,R.id.vf66};

	int[] TextViewbids = { R.id.tvb11, R.id.tvb12, R.id.tvb13, R.id.tvb14, R.id.tvb15, R.id.tvb16,
			R.id.tvb21, R.id.tvb22, R.id.tvb23, R.id.tvb24, R.id.tvb25, R.id.tvb26,
			R.id.tvb31, R.id.tvb32, R.id.tvb33, R.id.tvb34, R.id.tvb35, R.id.tvb36,
			R.id.tvb41, R.id.tvb42, R.id.tvb43, R.id.tvb44, R.id.tvb45, R.id.tvb46,
			R.id.tvb51, R.id.tvb52, R.id.tvb53, R.id.tvb54, R.id.tvb55, R.id.tvb56,
			R.id.tvb61, R.id.tvb62, R.id.tvb63, R.id.tvb64, R.id.tvb65, R.id.tvb66};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnimationFactory.setFlipCompleteListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);       
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/*if (android.os.Build.VERSION.SDK_INT <= 10) {
			setContentView(R.layout.apilt11_main);
		}
		else
		{*/
			setContentView(R.layout.first_screen);
		//}

		loadSavedPreferences();

		TextView games = (TextView) findViewById(R.id.games);
		games.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(FirstGame.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				startActivity(intent);
			}
		});
		
		
		
		for (int i=0;i<36;i++)
		{		
			final int choiceIndex = i;			
			findViewById(TextViewbids[i]).setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {						 
					update(choiceIndex,-1);
					AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[choiceIndex]), FlipDirection.LEFT_RIGHT);					
					//count--;
					/*userClick++;
					if(userClick==50) {maxCells++; userClick=0;}*/

				}
			});			
		}


		timerValue = (TextView) findViewById(R.id.timerValue);
		maxTimerTV = (TextView) findViewById(R.id.maxTimerValue);
		maxTimerTV.setText(maxTimeValue);

		startButton = (TextView) findViewById(R.id.startButton);
		rulesButton = (TextView) findViewById(R.id.rulesButton);

		rulesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(FirstGame.this);
				builder.setMessage("Play against the computer who is flipping tiles faster and faster")
				.setTitle("Speed Tile Rules");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			        	   dialog.dismiss();
			           }
			       });				
				builder.show();
			}

		});
		
		startButton.setOnClickListener(new View.OnClickListener() {


			public void onClick(View view) {
				//new FlipTask().execute();

				if(flag)
				{

					customHandler.removeCallbacks(updateTimerThread);
					flipHandler.removeCallbacks(flipRun);					
					timerValue.setText("00:00:00");
					resetGame();					
					flag=false;
					startButton.setText("Start");

				}
				else
				{
					flag=true;
					startTime = SystemClock.uptimeMillis();
					customHandler.postDelayed(updateTimerThread, 0);
					//resetGame();
					flipHandler.postDelayed(flipRun, sleepValue);
					startButton.setText("Reset");
				}
			}

		});



	}



	Runnable flipRun = new Runnable() {
		@Override
		public void run() {

			Random rand = new Random();
			int random=0;
			//int cells = rand.nextInt(maxCells)+1;
			//Log.i("cells",cells+"");
			//while(cells>0){
				//cells--;

				random=rand.nextInt(36);
				while(isFlipped[random]) {random=rand.nextInt(36);} 
				update(random,1);
				AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[random]), FlipDirection.LEFT_RIGHT);
				if (count==10) gameOver();
			//}
			if (sleepValue>200) sleepValue-=5;
			else if (sleepValue<201 && sleepValue>50) sleepValue-=2;
			Log.i("SleepValue",sleepValue+"");
			if (count<10)
				flipHandler.postDelayed(flipRun, sleepValue);
			else gameOver();

		}
	};


	public void resetGame()
	{

		timeSwapBuff = 0L;				

		//maxCells = 1;
		//userClick = 0;
		sleepValue = 600;
		count = 0;
		int i=0;
		while (i<36)
		{		
			final int choiceIndex = i;
			if(isFlipped[i]){
				if((i>12 && i<17) || (i>18 && i<23))
				{					
					((TextView)findViewById(TextViewbids[i])).setText("");
					((TextView)findViewById(TextViewbids[i])).setBackgroundColor(Color.parseColor("#BC0001"));
				}
				AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[choiceIndex]), FlipDirection.LEFT_RIGHT);
				isFlipped[i]=!isFlipped[i];
			}
			else
			{
				if((i>12 && i<17) || (i>18 && i<23))
				{					
					((TextView)findViewById(TextViewids[i])).setText("");
					((TextView)findViewById(TextViewids[i])).setBackgroundColor(Color.parseColor("#808080"));
				}
			}

			findViewById(TextViewbids[i]).setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {						 
					update(choiceIndex,-1);
					AnimationFactory.flipTransition((ViewFlipper)findViewById(ViewFlipperids[choiceIndex]), FlipDirection.LEFT_RIGHT);					
					//count--;
					/*userClick++;
					if(userClick==50) {maxCells++; userClick=0;}*/

				}
			});
			i++;
		}
	}
	public synchronized void update(int index, int num)
	{
		isFlipped[index] = !isFlipped[index];
		count+=num;
		//if (count==10) gameOver();
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
	}



	@Override
	public void flipInComplete() {
		// TODO Auto-generated method stub

	}

	public void gameOver() {		
		timeSwapBuff += timeInMilliseconds;
		customHandler.removeCallbacks(updateTimerThread);

		for (int i = 0; i < 36; i++) {		
			findViewById(TextViewbids[i]).setOnClickListener(null);
		}

		Float size= (float) ((((TextView)findViewById(TextViewids[1])).getWidth()) * 0.75);
		int reqTextView = 0;
		if(isFlipped[13]) reqTextView = TextViewbids[13]; 
		else
			reqTextView = TextViewids[13];
		((TextView)findViewById(reqTextView)).setText("G");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[14]) reqTextView = TextViewbids[14]; 
		else
			reqTextView = TextViewids[14];

		((TextView)findViewById(reqTextView)).setText("A");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[15]) reqTextView = TextViewbids[15]; 
		else
			reqTextView = TextViewids[15];

		((TextView)findViewById(reqTextView)).setText("M");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[16]) reqTextView = TextViewbids[16]; 
		else
			reqTextView = TextViewids[16];

		((TextView)findViewById(reqTextView)).setText("E");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[19]) reqTextView = TextViewbids[19]; 
		else
			reqTextView = TextViewids[19];

		((TextView)findViewById(reqTextView)).setText("O");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[20]) reqTextView = TextViewbids[20]; 
		else
			reqTextView = TextViewids[20];

		((TextView)findViewById(reqTextView)).setText("V");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[21]) reqTextView = TextViewbids[21]; 
		else
			reqTextView = TextViewids[21];

		((TextView)findViewById(reqTextView)).setText("E");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		if(isFlipped[22]) reqTextView = TextViewbids[22]; 
		else
			reqTextView = TextViewids[22];

		((TextView)findViewById(reqTextView)).setText("R");
		((TextView)findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView)findViewById(reqTextView)).setTextSize(TypedValue.COMPLEX_UNIT_PX,size);

		currentGameTimeValue=(String) timerValue.getText();
		String[] splitValues = currentGameTimeValue.split(":"); 
		currTimeMillisVal=(Integer.parseInt(splitValues[0])*60000) + (Integer.parseInt(splitValues[1])*1000)+Integer.parseInt(splitValues[2]);
		if (maxTimeMillisVal<currTimeMillisVal)
		{
			maxTimeMillisVal=currTimeMillisVal;
			maxTimeValue=currentGameTimeValue;
			maxTimerTV.setText(maxTimeValue);
		}

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		updateSavedPreferences();
	}
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (sharedPreferences.contains("TilesFirstGame")){
			maxTimeValue= sharedPreferences.getString("TilesFirstGame", "");
			if (maxTimeValue!=""){								
				String[] splitValues = maxTimeValue.split(":"); 
				maxTimeMillisVal=(Integer.parseInt(splitValues[0])*60000) + (Integer.parseInt(splitValues[1])*1000)+Integer.parseInt(splitValues[2]);
			}			
		}
	}


	private void updateSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.contains("TilesFirstGame")){
			editor.remove("TilesFirstGame");
		}	
		editor.putString("TilesFirstGame", maxTimeValue);
		editor.commit();
	}

}
