package com.games.tilewars;


import com.games.tilewars.R;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	GlobalClass globalVariable;
	private static final String TAG = "demo.layout.transition.MainActivity";
	private static final int ABOUT_MENUOPTION_ID = Menu.FIRST + 11;
	private View viewvar= null;
	LinearLayout frm_linear_00 = null;
	TextView soundIcon;
	TextView t1 = null;
	TextView t2 = null;
	TextView t3 = null;
	TextView t4 = null;
	TextView gameId = null;
	MediaPlayer mplayer;

	View l1b1=null;
	View l1b1e=null;
	View l1b4=null;
	View l2b1=null;
	View l2b1e=null;
	View l2b4=null;

	int tvWidth = 0;
	int tvHeight = 0;
	int Gameid=0;
	private Handler animOverHandler = new Handler();
	public static Animator.AnimatorListener sAnimatorListener2;
	boolean sound=true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);       
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		globalVariable = (GlobalClass) getApplicationContext();
		sound=globalVariable.soundIsOn();
		soundIcon = (TextView) findViewById(R.id.sound);
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


		setOnClickListenerForChild(R.id.frm_linear_00, true, true);
		setOnClickListenerForChild(R.id.frm_linear_01, true, true);
		setOnClickListenerForChild(R.id.frm_linear_02, true, true);

		//LayoutTransitionUtil.setupChangeAnimation((ViewGroup)findViewById(R.id.frm_linear_00));
		//LayoutTransitionUtil.setupChangeAnimation((ViewGroup)findViewById(R.id.frm_linear_01));
		//LayoutTransitionUtil.setupChangeAnimation((ViewGroup)findViewById(R.id.frm_linear_02));		

		LayoutTransitionUtil.sAnimatorListener = new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {


			}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				gameId.setText("Game "+Gameid);
			}

			@Override
			public void onAnimationCancel(Animator animation) {}


		};

		TextView play = (TextView) findViewById(R.id.play);
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlaySound(R.raw.menuclick);
				if(Gameid==0){
					//Toast.makeText(MainActivity.this, "Please select a TILE out of those four tiles. Then Click 'Play'.", Toast.LENGTH_SHORT).show();
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Please select a TILE among four tiles. Then Click 'Play'.")
					.setTitle("Select a Game");
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
							dialog.dismiss();
						}
					});				
					builder.show();
				}
				if(Gameid==1){
					Intent intent = new Intent(MainActivity.this, FirstGame.class);                                    
					startActivity(intent);
				}
				if(Gameid==2){
					Intent intent = new Intent(MainActivity.this, SecondGame.class);                                    
					startActivity(intent);
				}
				if(Gameid==3){
					Intent intent = new Intent(MainActivity.this, ThirdGame.class);                                    
					startActivity(intent);
				}
				if(Gameid==4){
					Intent intent = new Intent(MainActivity.this, FourthGame.class);                                    
					startActivity(intent);
				}

			}
		});

		t1 = (TextView) findViewById(R.id.l1b2);
		t2 = (TextView) findViewById(R.id.l1b3);
		t3 = (TextView) findViewById(R.id.l2b2);
		t4 = (TextView) findViewById(R.id.l2b3);
		gameId = (TextView) findViewById(R.id.gameid);
		frm_linear_00 = (LinearLayout) findViewById(R.id.frm_linear_00);

		l1b1=(View) findViewById(R.id.l1b1);
		l1b1e=(View) findViewById(R.id.l1b1e);
		l1b4=(View) findViewById(R.id.l1b4);

		l2b1=(View) findViewById(R.id.l2b1);
		l2b1e=(View) findViewById(R.id.l2b1e);
		l2b4=(View) findViewById(R.id.l2b4);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sound=globalVariable.soundIsOn();
		if(sound==false)
		{
			soundIcon.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.volumemuted,0,0);
		}
		else
		{
			soundIcon.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.volumeon,0,0);
		}

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
				Gameid=1;
				textView.setWidth(textView.getWidth()*2);
				textView.setHeight(textView.getHeight()*2);
				PlaySound(R.raw.tileclick);

			}

			if (textView==t2)
			{				
				frm_linear_00.setVisibility(View.VISIBLE);
				l1b1.setVisibility(View.GONE);					
				l2b1.setVisibility(View.GONE);					
				l2b1e.setVisibility(View.GONE);										
				l2b4.setVisibility(View.VISIBLE);			
				l1b1e.setVisibility(View.GONE);										
				l1b4.setVisibility(View.GONE);
				Gameid=2;
				/*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)t3.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_LEFT, R.id.l1b2);
				t1.setLayoutParams(params); //causes layout update */
				textView.setWidth(textView.getWidth()*2);
				textView.setHeight(textView.getHeight()*2);
				//PlaySound(R.raw.tileclick);
			}


			if (textView==t3)
			{				
				frm_linear_00.setVisibility(View.GONE);

				l1b1e.setVisibility(View.VISIBLE);
				l1b4.setVisibility(View.GONE);
				l2b1.setVisibility(View.VISIBLE);					
				l2b1e.setVisibility(View.GONE);
				l1b1.setVisibility(View.GONE);
				l2b4.setVisibility(View.GONE);
				Gameid=3;
				textView.setWidth(textView.getWidth()*2);
				textView.setHeight(textView.getHeight()*2);
				PlaySound(R.raw.tileclick);
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
				Gameid=4;
				textView.setWidth(textView.getWidth()*2);
				textView.setHeight(textView.getHeight()*2);
				//PlaySound(R.raw.tileclick);
			}
			animOverHandler.postDelayed(animOver, 300);
			//gameId.setText("Game "+Gameid);
		};		
	}

	Runnable animOver = new Runnable() {
		@Override
		public void run() {

			if(Gameid==1)
				gameId.setText("SpeedTile Game");
			if(Gameid==2){
				PlaySound(R.raw.tileclick);		
				gameId.setText("MemoryTile Game");				
			}
			if(Gameid==3)
				gameId.setText("FlipTile Game");						
			if(Gameid==4){				
				PlaySound(R.raw.tileclick);
				gameId.setText("ChainTile Game");
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		if(sound){
			mplayer = MediaPlayer.create(MainActivity.this, Sound_id);
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

}
