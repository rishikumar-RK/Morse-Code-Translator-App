package com.example.rishi.morsecode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView input,output;
    Button encode,decode,clear;
    ImageButton copy;
    String copyResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input=(TextView) findViewById(R.id.input);
        output=(TextView) findViewById(R.id.output);
        encode=(Button) findViewById(R.id.enc);
        decode=(Button) findViewById(R.id.dec);
        clear=(Button) findViewById(R.id.clr);
        copy=(ImageButton) findViewById(R.id.cpy);

        final String[] letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s",
                "t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        final String[] morse={".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--",
                "-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..","-----",".----",
                "..---","...--","....-",".....","-....","--...","---..","----."};

        final HashMap<String,String> letterToMorse = new HashMap<>();
        final HashMap<String,String> morseToLetter = new HashMap<>();

        for(int i=0;i<letters.length;i++){
            letterToMorse.put(letters[i],morse[i]);
            morseToLetter.put(morse[i],letters[i]);
        }

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inp=input.getText().toString().toLowerCase();
                boolean valid=true;
                StringBuilder res=new StringBuilder();
                String[] words=inp.trim().split(" ");
                for(String word: words){
                    if(word.matches("[a-z0-9]+")){
                        for(int i=0;i<word.length();i++){
                            res.append(letterToMorse.get(word.substring(i,i+1)));
                            res.append(" ");
                        }
                        res.append("  ");
                    }
                    else{
                        valid=false;
                        break;
                    }

                }
                if(valid){
                    copyResult=res.toString();
                    output.setText(copyResult);
                }
                else
                    Toast.makeText(MainActivity.this, "Enter valid text", Toast.LENGTH_SHORT).show();
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inp=input.getText().toString();
                StringBuilder res=new StringBuilder();
                String[] words=inp.trim().split("  ");
                Boolean valid=true;
                for(String word: words){
                    for(String letter: word.split(" ")){
                        if(!morseToLetter.containsKey(letter)){
                            valid=false;
                            break;
                        }
                        if(valid)
                            res.append(morseToLetter.get(letter));
                        else
                            break;
                    }
                    if(valid)
                        res.append(" ");
                    else
                        break;
                }
                if(valid){
                    copyResult=res.toString();
                    output.setText(copyResult);
                }
                else
                    Toast.makeText(MainActivity.this, "Enter valid code", Toast.LENGTH_SHORT).show();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(copyResult.equals("")){
                    Toast.makeText(MainActivity.this, "No text copied", Toast.LENGTH_SHORT).show();
                }
                else{
                    ClipboardManager clipboard=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip=ClipData.newPlainText("text",copyResult);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Text copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyResult="";
                input.setText("");
                output.setText("");
            }
        });

    }
}
