package com.example.chiamaka.tic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Chiamaka on 06/04/2018.
 */

public class PlayingHard extends Activity{

    //declaring global variables
    private TicTacToeBoard mGame;

    //check if the back button is clicked twice
    private int mExitCounter = 0;

    // Buttons making up the board
    private Button mBoardButtons[];
    private Button mNewGame;

    // Various text displayed
    private TextView myScoreText;
    private TextView myAiText;
    private TextView textView;
    private TextView playAgain;
    private TextView mInfoTextView;
    private TextView youText;
    private TextView aiText;

    // Counters for the wins and ties
    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    // Bools needed to see if player one goes first
    // if the gameType is to be single or local multiplayer
    // if it is player one's turn
    // and if the game is over
    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;

    boolean mGameType;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.playinghard);
        mGameType = getIntent().getExtras().getBoolean("gameTypes");

        // Initialize the buttons
        // Log.d("BoardSize", "Board size is initialized"+ mGame.getBOARDS_SIZE());
        // mBoardButtons = new Button[mGame.getBOARDS_SIZE()];
        mBoardButtons = new Button[16];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);

        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);

        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        mBoardButtons[9] = (Button) findViewById(R.id.ten);
        mBoardButtons[10] = (Button) findViewById(R.id.eleven);
        mBoardButtons[11] = (Button) findViewById(R.id.twelve);

        mBoardButtons[12] = (Button) findViewById(R.id.thirteen);
        mBoardButtons[13] = (Button) findViewById(R.id.fourteen);
        mBoardButtons[14] = (Button) findViewById(R.id.fifteen);
        mBoardButtons[15] = (Button) findViewById(R.id.sixteen);
        addListenerOnButton();


        // setup the textviews

        myScoreText = (TextView)findViewById(R.id.myScoreText);
        myAiText = (TextView)findViewById(R.id.myAiText);
        textView = (TextView) findViewById(R.id.dialogue);
        youText = (TextView) findViewById(R.id.you);
        aiText = (TextView) findViewById(R.id.ai);
        playAgain = (Button) findViewById(R.id.playAgain);
        mInfoTextView = (TextView) findViewById(R.id.info);

        // set the initial counter display values
        myScoreText.setText(Integer.toString(mPlayerOneCounter));
        myAiText.setText(Integer.toString(mPlayerTwoCounter));

        // create a new game object
        mGame = new com.example.chiamaka.tic.TicTacToeBoard();

        // start a new game
        startNewGame(mGameType);
        Button increse = (Button)findViewById(R.id.increment);
        increse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayingHard.this, PlayinghardHard.class);
                intent.putExtra("gameType", mGameType);
                startActivity(intent);
            }
        });
    }
    public void addListenerOnButton(){

        mNewGame = (Button) findViewById(R.id.playAgain);

        mNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startNewGame(mIsSinglePlayer);
            }
        });
    }
    public void playAgain(){
        playAgain.setVisibility(View.VISIBLE);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame(mIsSinglePlayer);
                textView.setText("Click a button to start playing");
            }
        });
    }


    private void setMove(char player, int location)
    {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        if (player == mGame.PLAYER_ONE)
            mBoardButtons[location].setText("X");
        else
            mBoardButtons[location].setText("O");
    }

    //start a new game
    // clears the board and resets all buttons / text
    // sets game over to be false
    private void startNewGame(boolean isSingle)
    {

        this.mIsSinglePlayer = isSingle;

        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++)
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new PlayingHard.ButtonClickListener(i));
            //mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
        }


        if (mIsSinglePlayer)
        {
            youText.setText("Human:");
            aiText.setText("Android:");

            if (mPlayerOneFirst)
            {
                mInfoTextView.setText("You Play");
                mPlayerOneFirst = false;
            }
            else
            {
                mInfoTextView.setText("Computer Turn");
                int move = mGame.getComputerMove();
                setMove(mGame.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        }
        else
        {
            youText.setText("P 1:");
            aiText.setText("P 2:");

            if (mPlayerOneFirst)
            {
                mInfoTextView.setText("P 1's Turn");
                mPlayerOneFirst = false;
            }
            else
            {
                mInfoTextView.setText("P 2's Turn");
                mPlayerOneFirst = true;
            }
        }

        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener
    {
        int location;

        public ButtonClickListener(int location)
        {
            this.location = location;
        }

        public void onClick(View view)
        {
            if (!mGameOver)
            {
                if (mBoardButtons[location].isEnabled())
                {
                    if (mIsSinglePlayer)
                    {
                        setMove(mGame.PLAYER_ONE, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0)
                        {
                            mInfoTextView.setText("Computer's Turn");
                            int move = mGame.getComputerMove();
                            setMove(mGame.PLAYER_TWO, move);
                            winner = mGame.checkForWinner();
                        }

                        if (winner == 0)
                            mInfoTextView.setText("Your Turn");
                        else if (winner == 1)
                        {
                            textView.setText("Draw");
                            mTieCounter++;
                            mGameOver = true;
                        }
                        else if (winner == 2)
                        {
                            textView.setText("You Won");
                            mPlayerOneCounter++;
                            myScoreText.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        }
                        else
                        {
                            textView.setText("Computer Won");
                            mPlayerTwoCounter++;
                            myAiText.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    }
                    else
                    {
                        if (mIsPlayerOneTurn)
                            setMove(mGame.PLAYER_ONE, location);
                        else
                            setMove(mGame.PLAYER_TWO, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0)
                        {
                            if (mIsPlayerOneTurn)
                            {
                                mInfoTextView.setText("P 2's Turn");
                                mIsPlayerOneTurn = false;
                            }
                            else
                            {
                                mInfoTextView.setText("P 1's Turn");
                                mIsPlayerOneTurn = true;
                            }
                        }
                        else if (winner == 1)
                        {
                            textView.setText("Draw");
                            playAgain();
                            mTieCounter++;
                            mGameOver = true;
                        }
                        else if (winner == 2)
                        {
                            textView.setText("Player one Wins");
                            mPlayerOneCounter++;
                            myScoreText.setText(Integer.toString(mPlayerOneCounter));
                            playAgain();
                            mGameOver = true;
                            mIsPlayerOneTurn = false;
                        }
                        else
                        {
                            textView.setText("Player two Wins");
                            mPlayerTwoCounter++;
                            myAiText.setText(Integer.toString(mPlayerTwoCounter));
                            playAgain();
                            mGameOver = true;
                            mIsPlayerOneTurn = true;
                        }
                    }
                }
            }
        }
    }

    //disable the back button of the device to avoid cheating
    @Override
    public void onBackPressed() {
        if (mExitCounter == 1) {
            finish();
            System.exit(0);
            Intent intent = new Intent(PlayingHard.this, SecondScreen.class);
            startActivity(intent);
        } else {
            mExitCounter++;
            Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    //setting options menu to reset game
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add("New Game");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame(mIsSinglePlayer);
        return true;
    }
}
