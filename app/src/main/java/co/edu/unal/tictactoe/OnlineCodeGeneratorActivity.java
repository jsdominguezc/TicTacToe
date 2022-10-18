package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OnlineCodeGeneratorActivity extends AppCompatActivity {

    boolean isCodeMaker = true;
    boolean codeFound = false;
    boolean checkTemp = true;

    private TextView headTV;
    private EditText codeEdt;
    private Button createCodeBtn;
    private Button joinCodeBtn;
    private ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_code_generator);
        headTV = findViewById(R.id.idTVHead);
        codeEdt = findViewById(R.id.idEdtCode);
        createCodeBtn = findViewById(R.id.idBtnCreate);
        joinCodeBtn = findViewById(R.id.idBtnJoin);
        loadingPB = findViewById(R.id.idPBLoading);
    }
}