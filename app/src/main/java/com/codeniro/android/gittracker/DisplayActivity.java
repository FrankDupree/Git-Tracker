package com.codeniro.android.gittracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private Button button;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        //get the data passed
        Intent intent = getIntent();
        title=intent.getStringExtra("title");
        TextView title2= (TextView) findViewById(R.id.texto);
        TextView title3= (TextView) findViewById(R.id.url);

        title2.setText(title);
        String link ="https://github.com/"+title;
        title3.setText(link);

        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        ImageView card = (ImageView) findViewById(R.id.imgo);
        card.setImageBitmap(bitmap);

        button = (Button) findViewById(R.id.btn_share);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @" +
                        title + "\nhttps://github.com/"+title);

                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}
