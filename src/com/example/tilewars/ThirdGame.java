package com.example.tilewars;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;

@SuppressLint({ "ShowToast", "NewApi" })
public class ThirdGame extends Activity implements FlipCompleteListener {
	private TextView resetButton;
	private TextView rulesButton;

	private TextView redValue;
	private TextView blueValue;


	private Handler switchHandler = new Handler();


	int cnt = 0;
	int oppCnt = 0;
	int redCount = 0;
	int blueCount = 0;
	int chances = 2;
	int player = -1;
	int otherPlayer = 1;
	int tileColor = 0;
	int neighbour1 = -1;
	int neighbour2 = -1;
	int neighbour3 = -1;
	int neighbour4 = -1;
	int neighbour5 = -1;
	int neighbour6 = -1;
	int neighbour7 = -1;
	int neighbour8 = -1;

	int level = 0;
	int swaps = 0;
	int flipscount = 0;

	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	int sleepValue = 600;
	int maxCells = 1;
	int userClick = 0;
	//boolean computer = true;
	boolean gameover = false;

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

	int[] cardColor = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

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
	int count = 0;
	int swaps1 = 0;
	int swaps2 = 0;
	Random rand = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnimationFactory.setFlipCompleteListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);       
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		if (android.os.Build.VERSION.SDK_INT <= 10) {
			setContentView(R.layout.apilt11_main);
		} else {
			setContentView(R.layout.third_screen);
		}

		TextView games = (TextView) findViewById(R.id.games);
		games.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ThirdGame.this, MainActivity.class);
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
							

							for (int k = 0; k < 36; k++) {
								if (cardColor[k]==0)(findViewById(TextViewids[k])).setClickable(false);
							}
							
							cnt = 0;
							oppCnt=0;
							if (player == 1) {
								tileColor = Color.RED;
							} else if (player == -1) {
								tileColor = Color.BLUE;
							}

							if (isFlipped[choiceIndex]) {
								((TextView) findViewById(TextViewids[choiceIndex]))
								.setBackgroundColor(tileColor);
								((TextView) findViewById(TextViewids[choiceIndex]))
								.setClickable(false);
							} else {
								((TextView) findViewById(TextViewbids[choiceIndex]))
								.setBackgroundColor(tileColor);
								((TextView) findViewById(TextViewbids[choiceIndex]))
								.setClickable(false);
							}
							AnimationFactory
							.flipTransition(
									(ViewFlipper) findViewById(ViewFlipperids[choiceIndex]),
									FlipDirection.LEFT_RIGHT);

							isFlipped[choiceIndex] = !isFlipped[choiceIndex];
							cardColor[choiceIndex] = player;
							cnt++;

							otherPlayer = player * -1;

							neighbour4 = choiceIndex - 1;
							if (choiceIndex % 6 != 0) {
								if (cardColor[neighbour4] == otherPlayer) {
									FlipTiles(neighbour4);
								}
								neighbour1 = neighbour4 - 6;
								if (neighbour1 > -1
										&& cardColor[neighbour1] == otherPlayer) {

									FlipTiles(neighbour1);
								}
								neighbour6 = neighbour4 + 6;
								if (neighbour6 < 36
										&& cardColor[neighbour6] == otherPlayer) {

									FlipTiles(neighbour6);
								}

							}

							neighbour5 = choiceIndex + 1;
							if (neighbour5 % 6 != 0) {
								if (cardColor[neighbour5] == otherPlayer) {

									FlipTiles(neighbour5);
								}

								neighbour3 = neighbour5 - 6;

								if (neighbour3 > -1
										&& cardColor[neighbour3] == otherPlayer) {

									FlipTiles(neighbour3);
								}
								neighbour8 = neighbour5 + 6;
								if (neighbour8 < 36
										&& cardColor[neighbour8] == otherPlayer) {

									FlipTiles(neighbour8);
								}
							}

							neighbour2 = choiceIndex - 6;
							if (neighbour2 > -1
									&& cardColor[neighbour2] == otherPlayer) {

								FlipTiles(neighbour2);
							}
							neighbour7 = choiceIndex + 6;
							if (neighbour7 < 36
									&& cardColor[neighbour7] == otherPlayer) {

								FlipTiles(neighbour7);
							}

							if (player == 1) {
								redCount += cnt;
								blueCount +=oppCnt;

							} else if (player == -1) {
								blueCount += cnt;
								redCount +=oppCnt;

							}
							redValue.setText("Red Count : " + redCount);
							blueValue.setText("Blue Count : " + blueCount);
							switchHandler.postDelayed(switchRun, 200);

						}
					});
		}
		
		redValue = (TextView) findViewById(R.id.redValue);
		redValue.setText("Red Score : " + redCount);

		blueValue = (TextView) findViewById(R.id.blueValue);
		blueValue.setText("Blue Score : " + blueCount);
		blueValue.setTextColor(Color.BLUE);
		redValue.setTextColor(Color.GRAY);
		resetButton = (TextView) findViewById(R.id.resetButton);
		rulesButton = (TextView) findViewById(R.id.rulesButton);
		rulesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ThirdGame.this);
				builder.setMessage("Play against a friend! Take two turns each and try to get the most tiles.")
				.setTitle("Flip Tile Rules");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			        	   dialog.dismiss();
			           }
			       });				
				builder.show();
			}

		});
		
		resetButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				resetButton.setClickable(false);
				resetGame();
				redValue.setText("Red Score : " + redCount);
				blueValue.setText("Blue Score : " + blueCount);								
				resetButton.setClickable(true);				
			}

		});

	}

	public void FlipTiles(int index) {
		if (isFlipped[index]) {
			((TextView) findViewById(TextViewids[index]))
			.setBackgroundColor(tileColor);
			((TextView) findViewById(TextViewids[index])).setClickable(false);
		} else {
			((TextView) findViewById(TextViewbids[index]))
			.setBackgroundColor(tileColor);
			((TextView) findViewById(TextViewbids[index])).setClickable(false);
		}
		AnimationFactory.flipTransition(
				(ViewFlipper) findViewById(ViewFlipperids[index]),
				FlipDirection.LEFT_RIGHT);
		isFlipped[index] = !isFlipped[index];
		cardColor[index] = player;
		cnt++;
		oppCnt--;

	}


	Runnable switchRun = new Runnable() {
		@Override
		public void run() {								
			chances--;
			if (chances == 0) {
				chances = 2;
				player = player * -1;
				if (player == 1) {
					redValue.setTextColor(Color.RED);	
					blueValue.setTextColor(Color.GRAY);
				} else if (player == -1) {
					blueValue.setTextColor(Color.BLUE);
					redValue.setTextColor(Color.GRAY);
				}
			}
			for (int k = 0; k < 36; k++) {
				if (cardColor[k]==0)(findViewById(TextViewids[k])).setClickable(true);
			}

		}
	};

	
	public void resetGame() {
		redCount=0;
		blueCount=0;
		chances = 2;
		player = -1;
		otherPlayer = 1;
				
		for (int i = 0;i < 36;i++) {
			//final int choiceIndex = i;
			((TextView) findViewById(TextViewids[i]))
			.setBackgroundColor(Color.parseColor("#808080"));
			
			if (isFlipped[i]) {
				
					((TextView) findViewById(TextViewbids[i]))
					.setBackgroundColor(Color.parseColor("#BC0001"));									
				AnimationFactory
				.flipTransition(
						(ViewFlipper) findViewById(ViewFlipperids[i]),
						FlipDirection.LEFT_RIGHT);
				isFlipped[i] = !isFlipped[i];
			}			
			((TextView) findViewById(TextViewids[i]))
			.setClickable(true);
			cardColor[i]=0;
		}
	}

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

}
