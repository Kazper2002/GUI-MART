package com.example.activiesandviews;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Scanner;
public class MainActivity extends ComponentActivity {
    private Button button;
    private AssetManager assets;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        assets = getAssets();
        setupButtons();
    }
    private void setupButtons() {
        button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText uText = (EditText) findViewById(R.id.inputName);
                EditText pText = (EditText) findViewById(R.id.inputPassword);
                if(authenticate(uText.getText().toString(), pText.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    uText.setError("Incorrect username and password combination");
                    pText.setError("Incorrect username and password combination");
                    uText.setText("");
                    pText.setText("");
                }
            }
        });
}
    private boolean authenticate(String username, String password) {
        Scanner scnr;
        String str;
        String[] arr = null;
        boolean authenticated = false;

        try {
            scnr = new Scanner(this.assets.open("login.txt"));
            while(scnr.hasNext()){
                str = scnr.nextLine();
                arr = str.split(",");
                if(username.equalsIgnoreCase(arr[1]) && password.equals(arr[2])) {
                    authenticated = true;
                    break;
                }
            }
            scnr.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return authenticated;
    }
}
