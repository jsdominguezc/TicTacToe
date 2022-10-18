package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiPlayerGameSelection extends AppCompatActivity {

    private Button onlineBtn;
    private Button offlineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_game_selection);

        onlineBtn = findViewById(R.id.idBtnOnline);
        offlineBtn = findViewById(R.id.idBtnOffline);

        //gameplay
        onlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OnlineCodeGeneratorActivity.class);
                startActivity(intent);
            }
        });

        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}