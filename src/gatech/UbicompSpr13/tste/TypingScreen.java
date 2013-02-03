package gatech.UbicompSpr13.tste;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TypingScreen extends Activity {

	StudyStringData stringList = new StudyStringData();
	int warmUpTextCount = 0;
	TextView text;
	int warmUpTrials = 2;
	long trialDuration = 1200000;
	private Timer timer;
	TextView timeRemaining;
	Random randomGenerator = new Random();
	int phrasesCount;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Called by the system when the activity is loaded. 
	 * 1. Initializes the reference to the text view and displays them to the user.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.typing_screen);
		if(text == null)
			text = (TextView) findViewById(R.id.presentedText);
		
		if(timeRemaining == null)
			timeRemaining = (TextView) findViewById(R.id.timeRemaining);
		
		text.setText(stringList.warmUpStrings[warmUpTextCount]);
		warmUpTextCount++;
		timer = new Timer();
		phrasesCount = stringList.phrasesArray.length;
	}
	
	/*
	 * Method is called when the Submit button is hit. 
	 * 1. Displays as many warm up trials as necessary.
	 * 2. Starts the timer for a period of 20 minutes.
	 * 3. After each Submit is clicked, it *logs data* and presents the next one till the timer runs out.
	 */
	public void submitText(View view)
	{
		TextView text = (TextView) findViewById(R.id.presentedText);
		if(warmUpTextCount < warmUpTrials)
		{
			text.setText(stringList.warmUpStrings[warmUpTextCount]);
			warmUpTextCount++;
			
			if(warmUpTextCount == warmUpTrials)
			{
				new CountDownTimer(trialDuration, 1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						long secondsRemaining = millisUntilFinished / 1000;
						int minutesRemaining = (int)(secondsRemaining / 60);
						int secondsPastMinute = (int)(secondsRemaining % 60);
						timeRemaining.setText(String.valueOf(minutesRemaining) + ":" + String.valueOf(secondsPastMinute));
					}
					
					@Override
					public void onFinish() {
						//redirectToMainScreen();
					}
				}.start();
			}
		}
		else
		{
			int chosenPosition = randomGenerator.nextInt();
			text.setText(stringList.phrasesArray[chosenPosition]);
		}
	}
	
	private void redirectToMainScreen()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
