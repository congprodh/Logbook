package com.example.logbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.logbook.databinding.ActivityMainBinding;
import com.example.logbook.sqlite.DBHelper;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    EditText url;
    Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        url = findViewById(R.id.etURL);
        addbtn = findViewById(R.id.addbtn);

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urL = binding.etURL.getText().toString();
                new AddImage(urL).start();

                DBHelper helper = new DBHelper(MainActivity.this);
                helper.addURL(url.getText().toString().trim());

            }
        });

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.close();

    }

    class AddImage extends Thread{

        String URL;
        Bitmap bitmap;

        AddImage(String URL){
            this.URL = URL;
        }
        @Override
        public void run(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading your picture");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            InputStream inputStream = null;
            try {
                inputStream = new java.net.URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    binding.imageView.setImageBitmap(bitmap);
                }
            });

        }
    }

}