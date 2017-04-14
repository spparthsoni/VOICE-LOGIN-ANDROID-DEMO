package com.example.pc.voicetospeechdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName,etMobileNo;
    private final int CODE_SPEECH_INPUT_USERNAME = 100;
    private final int CODE_SPEECH_INPUT_MOBILE = 101;
    private FloatingActionButton faVoice;
    String txtUsername,txtMobileNo;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        faVoice = (FloatingActionButton) findViewById(R.id.faVoice);
        etUserName=(EditText)findViewById(R.id.etUsername);
        etMobileNo=(EditText)findViewById(R.id.etPassword);
        btnLogin=(Button)findViewById(R.id.btLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsername=etUserName.getText().toString();
                txtMobileNo=etMobileNo.getText().toString();
                if(txtUsername.length()!=0&&txtMobileNo.length()!=0) {
                    if (txtUsername.equalsIgnoreCase("abc") && txtMobileNo.equalsIgnoreCase("123")) {
                        MainActivity.this.finish();
                        Intent i = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        etUserName.setText("");
                        etMobileNo.setText("");
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();

                }
            }


        });
        faVoice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtUsername=etUserName.getText().toString();
                txtMobileNo=etMobileNo.getText().toString();
                if(txtUsername.length()==0)
                {
                    promptSpeechInputUserName();
                }

            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInputUserName() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_username));
        try {
            startActivityForResult(intent, CODE_SPEECH_INPUT_USERNAME);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void promptSpeechInputMobile() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Enter Mobile No...");
        try {
            startActivityForResult(intent, CODE_SPEECH_INPUT_MOBILE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
            faVoice.setVisibility(View.GONE);
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CODE_SPEECH_INPUT_USERNAME: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etUserName.setText(result.get(0));
                    if(txtMobileNo.length()==0)
                    {
                        promptSpeechInputMobile();
                    }
                }
                break;
            }
            case CODE_SPEECH_INPUT_MOBILE:{
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etMobileNo.setText(result.get(0));
                    txtUsername=etUserName.getText().toString();
                    txtMobileNo=etMobileNo.getText().toString();
                    if(txtUsername.equalsIgnoreCase("abc")&&txtMobileNo.equalsIgnoreCase("123")) {
                        MainActivity.this.finish();
                        Intent i = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(i);
                    }
                       else
                        {
                            Toast.makeText(MainActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                            etUserName.setText("");
                            etMobileNo.setText("");
                        }
                    }
                }
                break;
        }
    }
}
