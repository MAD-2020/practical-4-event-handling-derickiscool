package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    final String TAG = "Whack-A-Mole 1.0";
    private Button leftButton;
    private Button middleButton;
    private Button rightButton;
    private TextView scoreText;
    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftButton = (Button) findViewById(R.id.buttonLeft);
        middleButton = (Button) findViewById(R.id.buttonMiddle);
        rightButton = (Button) findViewById(R.id.buttonRight);
        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(String.valueOf(score));

        Log.v(TAG, "Finished Pre-Initialisation!");


    }
    @Override
    protected void onStart(){
        super.onStart();
        final ArrayList<Button> buttonList = new ArrayList<Button>();
        buttonList.add(leftButton);
        buttonList.add(middleButton);
        buttonList.add(rightButton);
        setNewMole(buttonList);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"Left button clicked!" );
                doCheck(leftButton);
                setNewMole(buttonList);

            }
        });
        Log.v(TAG, "Starting GUI!");
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"Middle button clicked!" );
                doCheck(middleButton);
                setNewMole(buttonList);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"Right button clicked" );
                doCheck(rightButton);
                setNewMole(buttonList);
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText() == "*")
        {
            Log.v(TAG, "Hit, score added! ");
            score+=1;
        }
        else {
            Log.v(TAG, "Missed, point deducted");
            score -=1;
        }
        if (score>=10){
            nextLevelQuery();
        }
        scoreText.setText(String.valueOf(score));

    }

    private void nextLevelQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");

        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel(score);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");


            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Log.v(TAG, "Advanced option given to user!");


    }

    private void nextLevel(int score){

        Intent levelUpTwo =  new Intent(MainActivity.this, Main2Activity.class);
        levelUpTwo.putExtra("score", score);
        startActivity(levelUpTwo);

    }

    private void setNewMole(ArrayList<Button> bList) {
        leftButton.setText("O");
        middleButton.setText("O");
        rightButton.setText("O");
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button selectedButton = bList.get(randomLocation);
        selectedButton.setText("*");

    }
}