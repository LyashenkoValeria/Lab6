package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String ERROR = "Exception";
    private EditText editText;
    private String addr;
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap image = null;
            try {
                InputStream input = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e(ERROR, e.getMessage());
            }
            return image;
        }
        protected void onPostExecute(Bitmap result) {
            if(result == null) imageView.setImageResource(android.R.color.transparent);
            else imageView.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.clear){editText.setText("");}
        else {
            addr = editText.getText().toString();
            new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(addr);
        }
    }
}
