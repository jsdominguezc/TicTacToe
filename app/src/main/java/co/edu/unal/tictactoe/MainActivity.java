package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Random mRand;

    // Buttons making up the board
    private Button mBoardButtons[];
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        mInfoTextView = (TextView) findViewById(R.id.information);
        mInfoTextView1 = (TextView) findViewById(R.id.textView);
        mInfoTextView2 = (TextView) findViewById(R.id.textView2);
        mInfoTextView3 = (TextView) findViewById(R.id.textView3);
        mGame = new TicTacToeGame();

        startNewGame();
    }

    private void startNewGame(){

        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText(" ");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));

        }
        // who goes first
        if (cont % 2 == 0) {
            int move = getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);}
        else{
            mInfoTextView.setText(R.string.first_human);}
        cont++;

        //mInfoTextView1.setText(R.string.human + win);
        mInfoTextView1.setText("Win: "+win);
        mInfoTextView2.setText("Tie: "+tie);
        mInfoTextView3.setText("Android:"+ lose);
        // End of startNewGame

    }

    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        public void onClick(View view) {


            if (mBoardButtons[location].isEnabled()) {

                setMove(TicTacToeGame.HUMAN_PLAYER, location);

// If no winner yet, let the computer make a move
                int winner = checkForWinner();
                mCount(winner);
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = checkForWinner();
                }
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1){
                    mInfoTextView.setText(R.string.result_tie);
                tie++;}
                else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
                        mBoardButtons[i].setEnabled(false);
                    }
                }
                else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    lose++;
                    for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
                        mBoardButtons[i].setEnabled(false);
                    }
                }
            }
        }
    }

    private void setMove(char player, int location) {
        //mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }


    private int checkForWinner() {

        //Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoardButtons[i].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i + 1].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i + 2].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER) {
                return 2;
            }
            if (mBoardButtons[i].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER  &&
                    mBoardButtons[i+1].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER  &&
                    mBoardButtons[i+2].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER){
                return 3;
                }
        }

        //Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoardButtons[i].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i + 3].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i + 6].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER) {
                return 2;
            }
            if (mBoardButtons[i].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER  &&
                    mBoardButtons[i+3].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER  &&
                    mBoardButtons[i+6].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER){
                return 3;
            }
        }

        // Check for diagonal wins
        if ((mBoardButtons[0].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER &&
                mBoardButtons[4].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER &&
                mBoardButtons[8].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER) ||
                (mBoardButtons[2].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER &&
                        mBoardButtons[4].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER &&
                        mBoardButtons[6].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER))
            return 3;
        if ((mBoardButtons[0].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                mBoardButtons[4].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                mBoardButtons[8].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER) ||
                (mBoardButtons[2].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                        mBoardButtons[4].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER &&
                        mBoardButtons[6].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER))
            return 2;

        // Check for tie
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoardButtons[i].getText().charAt(0) != TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i].getText().charAt(0) != TicTacToeGame.COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;

        }


    public int getComputerMove(){
        mRand = new Random();
        int a;


        if (mDifficultyLevel == DifficultyLevel.Expert) {

            // First see if there's a move O can make to win
            for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
                if (mBoardButtons[i].getText().charAt(0) != TicTacToeGame.HUMAN_PLAYER &&
                        mBoardButtons[i].getText().charAt(0) != TicTacToeGame.COMPUTER_PLAYER) {
                    mBoardButtons[i].setText("O");

                    if (checkForWinner() == 3) {
                        mBoardButtons[i].setText(" ");
                        return i;
                    } else
                        mBoardButtons[i].setText(" ");
                }
            }
        }

        if (mDifficultyLevel == DifficultyLevel.Expert || mDifficultyLevel == DifficultyLevel.Harder) {


            // See if there's a move O can make to block X from winning
            for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
                if (mBoardButtons[i].getText().charAt(0) != TicTacToeGame.HUMAN_PLAYER &&
                        mBoardButtons[i].getText().charAt(0) != TicTacToeGame.COMPUTER_PLAYER) {
                    mBoardButtons[i].setText("X");
                    if (checkForWinner() == 2) {
                        mBoardButtons[i].setText(" ");
                        return i;
                    } else
                        mBoardButtons[i].setText(" ");
                }
            }
        }


        // Generate random move
        do {
            a= mRand.nextInt(9);
        }while (mBoardButtons[a].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER ||
                mBoardButtons[a].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER);

        return a;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("New Game");
        return true;
    }*/

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

public void mCount (int winner) {
        if (winner == 1 )
            tie++;
        else if (winner == 2)
            win++;
        else if (winner == 3)
            lose++;
}


    }

