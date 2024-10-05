package com.example.texttospe;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button btnConvert;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        btnConvert = findViewById(R.id.btnConvert);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.forLanguageTag("bn"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "Language Not Supported", Toast.LENGTH_SHORT).show();
                        textToSpeech.setLanguage(Locale.forLanguageTag("en"));
                    } else {
                        textToSpeech.setLanguage(Locale.forLanguageTag("bn"));
                        Voice voice = new Voice("bn-bd-x-ban-network", new Locale("bn", "BD"), 40, 200, false, null);
                        textToSpeech.setVoice(voice);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnConvert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onClick(View v) {

                String txt = text.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(txt, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    textToSpeech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });


    }
}