package com.rechit.canvasviewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private MyCanvasView myCanvasView;
    private int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvasView = new MyCanvasView(this);
        myCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(myCanvasView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.ganti_warna:
                openColourPicker();
                Toast.makeText(this, "Ganti Warna", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ganti_ketebalan:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Ketebalan");
                alert.setMessage("Set Ketebalan Brush");

                LinearLayout linear=new LinearLayout(this);

                linear.setOrientation(1);
                TextView text=new TextView(this);
                text.setPadding(10, 10, 10, 10);


                SeekBar seek=new SeekBar(this);



                linear.addView(seek);
                linear.addView(text);

                alert.setView(linear);

                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        myCanvasView.setStrokeWidth(seekBar.getProgress());
                        text.setText("Pen size: " + seekBar.getProgress());



                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });



                alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Toast.makeText(getApplicationContext(), "OK Pressed",Toast.LENGTH_LONG).show();

                    }
                });

                alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Toast.makeText(getApplicationContext(), "Cancel Pressed",Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();
                Toast.makeText(this, "Ganti Ketebalan", Toast.LENGTH_LONG).show();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void openColourPicker () {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

                Toast.makeText(MainActivity.this, "Unavailable", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultColor = color;

                myCanvasView.setColor(color);

            }

        });

        ambilWarnaDialog.show(); // add

    }
}