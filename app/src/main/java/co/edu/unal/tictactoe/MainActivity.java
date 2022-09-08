package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TicTacToeGame mGame;

    // Buttons making up the board
    private BoardView mBoardView;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mInfoTextView1;
    private TextView mInfoTextView2;
    private TextView mInfoTextView3;

    public enum DifficultyLevel {Easy, Harder, Expert};
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Easy;

    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    private int cont = 1;
    private int win = 0;
    private int tie = 0;
    private int lose = 0;
    private boolean mGameOver = false;

//raw
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    MediaPlayer mHumanWins;
    MediaPlayer mComputerWins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mInfoTextView1 = (TextView) findViewById(R.id.textView);
        mInfoTextView2 = (TextView) findViewById(R.id.textView2);
        mInfoTextView3 = (TextView) findViewById(R.id.textView3);

        mGame = new TicTacToeGame();

        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);

        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);

        startNewGame();
    }

    private void startNewGame(){

        mGame.clearBoard();
        mGameOver = false;

        // Reset all buttons
        mBoardView.invalidate();


        // who goes first
        if (cont % 2 == 0) {
            int move = mGame.getComputerMove(mDifficultyLevel);
            mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, move);}
        else{
            mInfoTextView.setText(R.string.first_human);}
        cont++;

        //Show the counters


        // End of startNewGame

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    public  DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

public void setDifficultyLevel(DifficultyLevel difficultyLevel){
        mDifficultyLevel = difficultyLevel;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)};
// TODO: Set selected, an integer (0 to n-1), for the Difficulty dialog.
// selected is the radio button that should be selected.
                builder.setSingleChoiceItems(levels, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss(); // Close dialog
// TODO: Set the diff level of mGame based on which item was selected.
                                if (item == 0)
                                setDifficultyLevel(DifficultyLevel.Easy);
                                else if (item == 1 )
                                setDifficultyLevel(DifficultyLevel.Harder);
                                else
                                setDifficultyLevel(DifficultyLevel.Expert);
// Display the selected difficulty level
                                Toast.makeText(getApplicationContext(), levels[item],

                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                dialog = builder.create();
                break;

            case DIALOG_QUIT_ID:
// Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }

        return dialog;
    }


    // Listen for touches on the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
// Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;
            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)){

// If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();

                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove(mDifficultyLevel);
                    mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);

                // End of game
                else if (winner == 1){
                    mInfoTextView.setText(R.string.result_tie);
                    mGameOver = true;
                }
                else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    mGameOver = true;
                }
                else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mGameOver = true;
                }
                mCount(winner);
            }
// So we aren't notified of continued events when finger is moved
            return false;
        }
    };

    public void mCount (int winner) {
        if (winner == 1 ){
            tie++;
            mInfoTextView2.setText("Tie: "+tie);}
        else if (winner == 2){
            win++;
            mInfoTextView1.setText("Win: "+win);
            mHumanWins.start();
        }
        else if (winner == 3){
            lose++;
            mInfoTextView3.setText("Android:"+ lose);
            mComputerWins.start();
        }


    }

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate(); // Redraw the board
            mHumanMediaPlayer.start();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.human);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.comp);
        mHumanWins = MediaPlayer.create(getApplicationContext(), R.raw.win);
        mComputerWins = MediaPlayer.create(getApplicationContext(), R.raw.lose);


    }
    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
        mHumanWins.release();
        mComputerWins.release();
    }


    }

