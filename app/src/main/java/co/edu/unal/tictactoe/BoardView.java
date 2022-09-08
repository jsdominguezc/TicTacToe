package co.edu.unal.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {
public static final int GRID_WIDTH = 8;
private Bitmap mHumanBitmap;
private Bitmap mComputerBitmap;
private Paint mPaint;

    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs ,int defStyle){
        super(context, attrs, defStyle);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs){
        super(context, attrs);
        initialize();
    }

public void initialize(){
        mHumanBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ticx);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tico);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
}

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
// Determine the width and height of the View
        int boardWidth = getWidth();
        int boardHeight = getHeight();
// Make thick, light gray lines
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_WIDTH);
// Draw the two vertical board lines
        int cellWidth = boardWidth / 3;
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);
        // horizontal
        int cellHeight = boardHeight / 3;
        canvas.drawLine(0, cellHeight, boardHeight, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);

        // Draw all the X and O images
        for (int i = 0; i < TicTacToeGame.BOARD_SIZE; i++) {
            int col = i % 3;
            int row = i / 3;
// Define the boundaries of a destination rectangle for the image


            //int top = (int) Math.floor(i/3)*cellWidth+((int) Math.floor(i/3)*GRID_WIDTH);
            //int bottom = (cellWidth* ((int) Math.floor(i/3)+1))+((int) Math.floor(i/3)*GRID_WIDTH) ;
            int top = 0;
            int bottom = 0;
            int left = 0;
            int right = 0;

            cellWidth = cellWidth-4;
            if (i==0||i==1||i==2){
                top = 0;
                bottom = top+cellWidth;
            } else if (i==3||i==4||i==5){
                top = cellWidth +GRID_WIDTH;
                bottom = top+cellWidth-4;
            } else {
                top = ((cellWidth+GRID_WIDTH)*2)-3;
                bottom = top+cellWidth;
            }

            if (i==0||i==3||i==6){
                left = 0;
                right = left+cellWidth;
            } else if (i==1||i==4||i==7){
                left = cellWidth +GRID_WIDTH;
                right = left+cellWidth-4;
            } else {
                left = ((cellWidth+GRID_WIDTH)*2)-3;
                right = left+cellWidth;
            }
            cellWidth = cellWidth+4;

            if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER) {
                canvas.drawBitmap(mHumanBitmap,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        null);

            }
            else if (mGame != null && mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER) {
                canvas.drawBitmap(mComputerBitmap,
                        null, // src
                        new Rect(left, top, right, bottom), // dest
                        null);

            }

        }


    }

    private TicTacToeGame mGame;
    public void setGame(TicTacToeGame game) {
        mGame = game;
    }

    public int getBoardCellWidth() {
        return getWidth() / 3;
    }
    public int getBoardCellHeight() {
        return getHeight() / 3;
    }

}

