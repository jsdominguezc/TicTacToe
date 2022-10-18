package co.edu.unal.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstMain extends AppCompatActivity {


    //Gameplay
    private Button singlePlayerBtn;
    private Button multiPlayerBtn;
    public boolean singlePlayer = false;  // gameplay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_main);

        singlePlayerBtn = (Button) findViewById(R.id.idBtnSinglePlayer);
        multiPlayerBtn = (Button) findViewById(R.id.idBtnMultiPlayer);

        //gameplay
        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singlePlayer = true;
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        multiPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singlePlayer = false;
                Intent intent = new Intent(view.getContext(), MultiPlayerGameSelection.class);
                startActivity(intent);
            }
        });


    }
}