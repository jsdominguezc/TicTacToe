package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Random mRand;

    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;


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
        mGame = new TicTacToeGame();

        startNewGame();
    }

    private void startNewGame(){
        mGame.clearBoard();

        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText(" ");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));

            // Human goes first
            mInfoTextView.setText(R.string.first_human);
         // End of startNewGame
        }

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
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = checkForWinner();
                }
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1)
                    mInfoTextView.setText(R.string.result_tie);
                else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
                        mBoardButtons[i].setEnabled(false);
                    }
                }
                else {
                    mInfoTextView.setText(R.string.result_computer_wins);
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

        // First see if there's a move O can make to win
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            if (mBoardButtons[i].getText().charAt(0) != TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i].getText().charAt(0) != TicTacToeGame.COMPUTER_PLAYER) {
                 mBoardButtons[i].setText("O");

                if (checkForWinner() == 3) {
                    mBoardButtons[i].setText(" ");
                    return i;
                }
                else
                    mBoardButtons[i].setText(" ");
            }

        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            if (mBoardButtons[i].getText().charAt(0) != TicTacToeGame.HUMAN_PLAYER &&
                    mBoardButtons[i].getText().charAt(0) != TicTacToeGame.COMPUTER_PLAYER) {
                mBoardButtons[i].setText("X");
                if (checkForWinner() == 2) {
                    mBoardButtons[i].setText(" ");
                    return i;
                }
                else
                    mBoardButtons[i].setText(" ");
            }
        }



        // Generate random move
        do {
            a= mRand.nextInt(9);
        }while (mBoardButtons[a].getText().charAt(0) == TicTacToeGame.HUMAN_PLAYER ||
                mBoardButtons[a].getText().charAt(0) == TicTacToeGame.COMPUTER_PLAYER);

        return a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }

    }

