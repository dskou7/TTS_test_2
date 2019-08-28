package dev.sport.tts_wear;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Button listenButton;
    //private SpeechRecognizer recognizer;
    //private RecognitionListener lonk;

    private static final int SPEECH_REQUEST_CODE = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        listenButton = (Button) findViewById(R.id.button_listen);

        // Enables Always-on
        setAmbientEnabled();

        /*
        if (recognizer.isRecognitionAvailable(this)){
            mTextView.setText("Recognizer Ready");
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(lonk);
        }
        else {
            mTextView.setText("Recognizer Not Operational");
            listenButton.setEnabled(false);
        }
        */

        listenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //change background color
                //change text of button
                //listen
                getSpeech();

            }
        });
    }

    public void getSpeech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } else {
            mTextView.setText("This device does not support Speech recognition");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SPEECH_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextView.setText(results.get(0));
                }
                break;
        }
    }

}
