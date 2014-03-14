package com.games.tilewars;

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
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;

@SuppressLint({ "ShowToast", "NewApi" })
public class SecondGame extends Activity implements FlipCompleteListener {
	private static final int ABOUT_MENUOPTION_ID = Menu.FIRST + 11;
	private TextView nextButton;
	private TextView rulesButton;

	private TextView levelValue;
	private TextView maxLevelTV;

	private Handler flipHandler = new Handler();

	private Handler swapHandler = new Handler();
	private Handler gameOverHandler = new Handler();

	int maxLevelValue = 0;
	int level = 0;
	int swaps = 0;
	int flipscount = 0;	
	int maxCells = 1;
	int userClick = 0;
	boolean computer = true;
	boolean gameover = false;
	boolean gameovercalled = false;
	Animation animation1=null;
	Animation animation2=null;
	boolean switchmp=false;
	MediaPlayer mediaPlayer;
	MediaPlayer secondarymediaPlayer;	
	MediaPlayer mplayer;
	boolean sound=true;

	int[] TextViewids = { R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14,
			R.id.tv15, R.id.tv16, R.id.tv21, R.id.tv22, R.id.tv23, R.id.tv24,
			R.id.tv25, R.id.tv26, R.id.tv31, R.id.tv32, R.id.tv33, R.id.tv34,
			R.id.tv35, R.id.tv36, R.id.tv41, R.id.tv42, R.id.tv43, R.id.tv44,
			R.id.tv45, R.id.tv46, R.id.tv51, R.id.tv52, R.id.tv53, R.id.tv54,
			R.id.tv55, R.id.tv56, R.id.tv61, R.id.tv62, R.id.tv63, R.id.tv64,
			R.id.tv65, R.id.tv66 };

	boolean[] isFlipped = { false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false };

	boolean[] isUserFlipped = { false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false };

	int[] location1 = new int[2];
	int[] location2 = new int[2];

	final int[] ViewFlipperids = { R.id.vf11, R.id.vf12, R.id.vf13, R.id.vf14,
			R.id.vf15, R.id.vf16, R.id.vf21, R.id.vf22, R.id.vf23, R.id.vf24,
			R.id.vf25, R.id.vf26, R.id.vf31, R.id.vf32, R.id.vf33, R.id.vf34,
			R.id.vf35, R.id.vf36, R.id.vf41, R.id.vf42, R.id.vf43, R.id.vf44,
			R.id.vf45, R.id.vf46, R.id.vf51, R.id.vf52, R.id.vf53, R.id.vf54,
			R.id.vf55, R.id.vf56, R.id.vf61, R.id.vf62, R.id.vf63, R.id.vf64,
			R.id.vf65, R.id.vf66 };

	int[] TextViewbids = { R.id.tvb11, R.id.tvb12, R.id.tvb13, R.id.tvb14,
			R.id.tvb15, R.id.tvb16, R.id.tvb21, R.id.tvb22, R.id.tvb23,
			R.id.tvb24, R.id.tvb25, R.id.tvb26, R.id.tvb31, R.id.tvb32,
			R.id.tvb33, R.id.tvb34, R.id.tvb35, R.id.tvb36, R.id.tvb41,
			R.id.tvb42, R.id.tvb43, R.id.tvb44, R.id.tvb45, R.id.tvb46,
			R.id.tvb51, R.id.tvb52, R.id.tvb53, R.id.tvb54, R.id.tvb55,
			R.id.tvb56, R.id.tvb61, R.id.tvb62, R.id.tvb63, R.id.tvb64,
			R.id.tvb65, R.id.tvb66 };

	int remaining = 0;
	int swaps1 = 0;
	int swaps2 = 0;
	Random rand = new Random();
	int greenColor = Color.parseColor("#75DB1B");
	//int greenColor = Color.parseColor("#44B71F");
	int yellowColor = Color.parseColor("#F3D42C");


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
		} else {
			setContentView(R.layout.second_screen);
		}*/
		setContentView(R.layout.second_screen);
		loadSavedPreferences();
		mediaPlayer = MediaPlayer.create(this, R.raw.comedy_slide_whistle_up_001);
		secondarymediaPlayer = MediaPlayer.create(this, R.raw.comedy_slide_whistle_up_001);

		final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
		sound=globalVariable.soundIsOn();
		final TextView soundIcon = (TextView) findViewById(R.id.sound);
		if(sound==false)
		{
			soundIcon.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.volumemuted,0,0);
		}
		soundIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(sound==false) 
				{
					soundIcon.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.volumeon,0,0);
					sound=true;
					globalVariable.turnInSound(true);
				}
				else
				{
					PlaySound(R.raw.menuclick);
					soundIcon.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.volumemuted,0,0);
					sound=false;
					globalVariable.turnInSound(false);
				}

			}
		});

		TextView games = (TextView) findViewById(R.id.games);
		games.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PlaySound(R.raw.menuclick);
				Intent intent = new Intent(SecondGame.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				startActivity(intent);
			}
		});


		for (int i = 0; i < 36; i++) {
			final int choiceIndex = i;
			findViewById(TextViewids[i]).setOnClickListener(
					new View.OnClickListener() {
						public void onClick(View v) {
							if (isFlipped[choiceIndex]) {
								PlaySound(R.raw.comedy_pop_finger_in_mouth_002);
								((TextView) findViewById(TextViewbids[choiceIndex]))
								.setBackgroundColor(yellowColor);
								remaining--;
								flipscount--;
							} else {

								((TextView) findViewById(TextViewbids[choiceIndex]))
								.setBackgroundColor(Color.RED);
								PlaySound(R.raw.comedy_twang_or_spring_1);
								gameover = true;								
								for (int i = 0; i < 36; i++) {
									findViewById(TextViewids[i]).setClickable(false);									
								}
								// isFlipped[choiceIndex]=true;
								// gameOver();
							}
							isUserFlipped[choiceIndex] = true;
							AnimationFactory
							.flipTransition(
									(ViewFlipper) findViewById(ViewFlipperids[choiceIndex]),
									FlipDirection.LEFT_RIGHT);
						}
					});
		}

		for (int i = 0; i < 36; i++) {
			findViewById(TextViewids[i]).setClickable(false);
		}

		maxLevelTV = (TextView) findViewById(R.id.maxLevelValue);
		maxLevelTV.setText("Max Level : " + maxLevelValue);

		levelValue = (TextView) findViewById(R.id.levelValue);
		levelValue.setText("Levels Completed : " + level);
		nextButton = (TextView) findViewById(R.id.nextButton);
		rulesButton = (TextView) findViewById(R.id.rulesButton);

		nextButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				if (gameover) {					
					nextButton.setClickable(false);
					resetGame();
					levelValue.setText("Levels Completed : " + level);
					nextButton.setText("Start");
					nextButton.setClickable(true);
				} else {
					nextButton.setText("Next");
					nextButton.setClickable(false);
					for (int i = 0; i < 36; i++) {
						isFlipped[i] = false;
						isUserFlipped[i] = false;
					}
					Flip();
				}
			}

		});

		rulesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SecondGame.this);
				builder.setMessage("Remember where the tiles were shown and tap them all to advance to the next round. Don't be fooled when the tiles start swapping positions. \n\nInstructions:\n- START button starts the game. After the game starts, START button changes to NEXT button.\n- click NEXT button after tapping all the correct tiles, to advance to next level.\n- GAMES button takes you to the game selection screen.")
				.setTitle("MemoryTile Rules");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						dialog.dismiss();
					}
				});				
				builder.show();
			}

		});

	}

	public void Flip() {
		computer = true;
		int i = 0;
		for (i = 0; i < 36; i++) {
			findViewById(TextViewids[i]).setClickable(false);
		}

		int random = 0;
		int flips = level + 1;
		if (flips > 36)
			flips = 36; // fix
		swaps = level - 1;
		flipscount = flips;
		remaining = flips;

		while (flips > 0) {
			flips--;
			random = rand.nextInt(36);
			while (isFlipped[random]) {
				random = rand.nextInt(36);
			}
			isFlipped[random] = true;
			((TextView) findViewById(TextViewbids[random]))
			.setBackgroundColor(yellowColor);
			AnimationFactory.flipTransition(
					(ViewFlipper) findViewById(ViewFlipperids[random]),
					FlipDirection.LEFT_RIGHT);
		}

	}

	Runnable flipRun = new Runnable() {
		@Override
		public void run() {

			if (computer) {

				int i = 0;
				for (i = 0; i < 36; i++) {
					if (isFlipped[i])
						AnimationFactory.flipTransition(
								(ViewFlipper) findViewById(ViewFlipperids[i]),
								FlipDirection.LEFT_RIGHT);
				}
				if (swaps > 0) {
					// swapAnimation();
					switchmp=false;
					swapHandler.postDelayed(swapRun, 1000);
				} else {
					for (i = 0; i < 36; i++) {
						findViewById(TextViewids[i]).setClickable(true);
					}
					computer = false;
					flipscount = level + 1;
					if (flipscount > 36)
						flipscount = 36; // fix

				}
			} else if (!computer) {

				for (int j = 0; j < 36; j++) {
					if (isFlipped[j])
						AnimationFactory.flipTransition(
								(ViewFlipper) findViewById(ViewFlipperids[j]),
								FlipDirection.LEFT_RIGHT);
				}
				level++;
				levelValue.setText("Levels Completed : " + level);
				if (level > maxLevelValue) {
					maxLevelValue = level;
					maxLevelTV.setText("Max Level : " + maxLevelValue);
				}
				for (int i = 0; i < 36; i++) {
					findViewById(TextViewids[i]).setClickable(false);
				}
				nextButton.setClickable(true);
			}

		}
	};

	Runnable swapRun = new Runnable() {
		@Override
		public void run() {
			swapAnimation();

		}
	};

	Runnable gameOverRun = new Runnable() {
		@Override
		public void run() {
			gameOver();

		}
	};

	public void swapAnimation() {
		if(sound)
		{
			switchmp=!switchmp;
			if (switchmp==true){
				if( mediaPlayer.isPlaying()) {
					mediaPlayer.stop();			
				}
				secondarymediaPlayer.start();
			}
			if (switchmp==false){
				if( secondarymediaPlayer.isPlaying()) {
					secondarymediaPlayer.stop();			
				}
				mediaPlayer.start();
			}
		}
		swaps1 = rand.nextInt(36);
		swaps2 = rand.nextInt(36);
		while (swaps1 == swaps2) {
			swaps2 = rand.nextInt(36);
		}

		((TextView) findViewById(TextViewids[swaps1]))
		.getLocationInWindow(location1);

		((TextView) findViewById(TextViewids[swaps2]))
		.getLocationInWindow(location2);

		animation1 = new TranslateAnimation(0, location1[0]
				- location2[0], 0, location1[1] - location2[1]);
		animation1.setDuration(700);
		animation1.setZAdjustment(Animation.ZORDER_TOP);
		((TextView) findViewById(TextViewids[swaps2]))
		.startAnimation(animation1);

		animation2 = new TranslateAnimation(0, -location1[0]
				+ location2[0], 0, -location1[1] + location2[1]);
		animation2.setDuration(700);
		animation2.setZAdjustment(Animation.ZORDER_BOTTOM);

		animation2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				/*if( mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				mediaPlayer.start();
				 */
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				swaps--;
				boolean extra = isFlipped[swaps1];
				isFlipped[swaps1] = isFlipped[swaps2];
				isFlipped[swaps2] = extra;
				if (swaps > 0) {
					swapAnimation();
				} else if (swaps == 0) {
					for (int i = 0; i < 36; i++) {
						findViewById(TextViewids[i]).setClickable(true);
					}

					computer = false;
					flipscount = level + 1;
					if (flipscount > 36)
						flipscount = 36; // fix
				}

			}
		});



		((TextView) findViewById(TextViewids[swaps1]))
		.startAnimation(animation2);


	}

	public void resetGame() {
		remaining = 0;
		level = 0;
		flipscount = 0;
		swaps = 0;
		computer = true;
		gameover = false;
		gameovercalled = false;

		for (int i = 0; i < 36; i++) {
			final int choiceIndex = i;
			if (isUserFlipped[i]) {
				if ((i > 12 && i < 17) || (i > 18 && i < 23)) {
					((TextView) findViewById(TextViewbids[i])).setText("");
					((TextView) findViewById(TextViewbids[i]))
					.setBackgroundColor(Color.parseColor("#BC0001"));
				}
				AnimationFactory
				.flipTransition(
						(ViewFlipper) findViewById(ViewFlipperids[choiceIndex]),
						FlipDirection.LEFT_RIGHT);
				isUserFlipped[i] = !isUserFlipped[i];
			} else {
				if ((i > 12 && i < 17) || (i > 18 && i < 23)) {
					((TextView) findViewById(TextViewids[i])).setText("");
					((TextView) findViewById(TextViewids[i]))
					.setBackgroundColor(Color.parseColor("#808080"));
				}
			}

		}
	}


	@Override
	public void flipOutComplete() {

		if (gameover == true) {

			ViewFlipper vf;
			for (int i = 0; i < 36; i++) {
				findViewById(TextViewids[i]).setClickable(false);
				vf = (ViewFlipper) findViewById(ViewFlipperids[i]); // to correct the isflipped flags, that are skipped getting 
				// set at the end of game (i.e., when gameover() is called)
				if (vf.getDisplayedChild() == 1)
					isUserFlipped[i] = true;
				else
					isUserFlipped[i] = false;
			}

			if( gameovercalled == false) //To stop the recursive calls to gameover sound. 
										//This scenario happens when user clicks wrong tiles at very high speed. 
			{
				gameovercalled = true;
				gameOverHandler.postDelayed(gameOverRun, 600);
			}			
		}
		if (computer) {
			flipscount--;
		}
		Log.i("flipscount", flipscount + ":" + computer);
		if (flipscount == 0) {
			flipscount--;
			flipHandler.postDelayed(flipRun, 1000);
		}

	}

	@Override
	public void flipInComplete() {
		// TODO Auto-generated method stub

	}

	public void gameOver() {

		PlaySound(R.raw.app_game_interactive_alert_tone_015);

		Float size = (float) ((((TextView) findViewById(TextViewids[1]))
				.getWidth()) * 0.75);
		int reqTextView = 0;
		if (isUserFlipped[13])
			reqTextView = TextViewbids[13];
		else
			reqTextView = TextViewids[13];
		((TextView) findViewById(reqTextView)).setText("G");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[14])
			reqTextView = TextViewbids[14];
		else
			reqTextView = TextViewids[14];

		((TextView) findViewById(reqTextView)).setText("A");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[15])
			reqTextView = TextViewbids[15];
		else
			reqTextView = TextViewids[15];

		((TextView) findViewById(reqTextView)).setText("M");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[16])
			reqTextView = TextViewbids[16];
		else
			reqTextView = TextViewids[16];

		((TextView) findViewById(reqTextView)).setText("E");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[19])
			reqTextView = TextViewbids[19];
		else
			reqTextView = TextViewids[19];

		((TextView) findViewById(reqTextView)).setText("O");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[20])
			reqTextView = TextViewbids[20];
		else
			reqTextView = TextViewids[20];

		((TextView) findViewById(reqTextView)).setText("V");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[21])
			reqTextView = TextViewbids[21];
		else
			reqTextView = TextViewids[21];

		((TextView) findViewById(reqTextView)).setText("E");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);

		if (isUserFlipped[22])
			reqTextView = TextViewbids[22];
		else
			reqTextView = TextViewids[22];

		((TextView) findViewById(reqTextView)).setText("R");
		((TextView) findViewById(reqTextView)).setBackgroundColor(greenColor);
		((TextView) findViewById(reqTextView)).setTextSize(
				TypedValue.COMPLEX_UNIT_PX, size);
		nextButton.setText("Reset");
		nextButton.setClickable(true);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		updateSavedPreferences();
		if( mediaPlayer!=null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();			
		}
		if( secondarymediaPlayer!=null && secondarymediaPlayer.isPlaying()) {
			secondarymediaPlayer.stop();			
		}
		/*if( mplayer.isPlaying()) {
			mplayer.stop();
			mplayer.release();

		}*/
		mediaPlayer.release();
		secondarymediaPlayer.release();	

	}

	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (sharedPreferences.contains("TilesSecondGame")) {
			maxLevelValue = sharedPreferences.getInt("TilesSecondGame", 0);

		}
	}

	private void updateSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.contains("TilesSecondGame")) {
			editor.remove("TilesSecondGame");
		}
		editor.putInt("TilesSecondGame", maxLevelValue);
		editor.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, ABOUT_MENUOPTION_ID, 0,"About").setIcon(R.drawable.about);
		return true;

	}

	/** Menu Item Click Listener*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case ABOUT_MENUOPTION_ID:
			DialogPrompt.showAppAboutDialog(this);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void PlaySound(int Sound_id) {
		if(sound) {
			mplayer = MediaPlayer.create(SecondGame.this, Sound_id);
			if (mplayer != null) {
				mplayer.start();
			}
			mplayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}

			});
		}
	}
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
