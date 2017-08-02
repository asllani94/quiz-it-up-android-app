package com.asllani94.quizitup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

public class LevelsActivity extends AppCompatActivity {
private  int levelSelected=0;
private  AchievmentOperations op;
    private boolean check1;
    private  boolean check2;
    private  boolean check3;
    private boolean check4;
    private boolean check5;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        DatabaseRipper ripper=new DatabaseRipper(this);
        try {
            ripper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ripper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        op=new AchievmentOperations(this);
        op.open();
        check1=op.isLevelOpen(1);
        check2=op.isLevelOpen(2);
        check3=op.isLevelOpen(3);
        check4=op.isLevelOpen(4);
        check5=op.isLevelOpen(5);


        op.close();
        ImageView one=(ImageView)findViewById(R.id.locker1);
        ImageView two=(ImageView)findViewById(R.id.locker2);
        ImageView three=(ImageView)findViewById(R.id.locker3);
        ImageView four=(ImageView)findViewById(R.id.locker4);
        ImageView five=(ImageView)findViewById(R.id.locker5);


        if (check1){
            one.setImageResource(R.drawable.lock);

        }
         else{
            one.setImageResource(R.drawable.unlock);}
        if (check2){
            two.setImageResource(R.drawable.lock);}
        else{
        two.setImageResource(R.drawable.unlock);}
       if(check3){
            three.setImageResource(R.drawable.lock);}
        else{
        three.setImageResource(R.drawable.unlock);}
        if(check4){
            four.setImageResource(R.drawable.lock);}
        else{
            four.setImageResource(R.drawable.unlock);}
        if(check5){
            five.setImageResource(R.drawable.lock);}
        else{
            five.setImageResource(R.drawable.unlock);}






        final Button levelOneBtn = (Button) findViewById(R.id.lvl1Btn);

        if(check1)
            levelOneBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
        else
            levelOneBtn.setBackgroundColor(getResources().getColor(R.color.blue));


        levelOneBtn.getBackground().setAlpha(128);

        levelOneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                levelSelected = 1;
                if (check1)
                    StartLevel(v, levelSelected);
            }
        });
        final Button levelTwoBtn = (Button) findViewById(R.id.lvl2Btn);

        if(check2)
            levelTwoBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
        else
            levelTwoBtn.setBackgroundColor(getResources().getColor(R.color.blue));
        levelTwoBtn.getBackground().setAlpha(128);

        levelTwoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                levelSelected = 2;

                if (check2)
                    StartLevel(v, levelSelected);
                else {
                    Toast.makeText(getApplicationContext(), "Score 8 in level 1 to unlock", Toast.LENGTH_LONG).show();
                }

            }
        });
        final Button levelThreeBtn = (Button) findViewById(R.id.lvl3Btn);

        if(check3)
            levelThreeBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
        else
            levelThreeBtn.setBackgroundColor(getResources().getColor(R.color.blue));
        levelThreeBtn.getBackground().setAlpha(128);
        levelThreeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                levelSelected = 3;

                if (check3)
                    StartLevel(v, levelSelected);
                else
                    Toast.makeText(getApplicationContext(), "Score 8 in level 2 to unlock", Toast.LENGTH_LONG).show();


            }
        });

        final Button levelFourBtn = (Button) findViewById(R.id.lvl4Btn);

        if(check4)
            levelFourBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
        else
            levelFourBtn.setBackgroundColor(getResources().getColor(R.color.blue));
        levelFourBtn.getBackground().setAlpha(128);
        levelFourBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                levelSelected = 4;

                if (check4)
                    StartLevel(v, levelSelected);
                else
                    Toast.makeText(getApplicationContext(), "Score 8 in level 3 to unlock", Toast.LENGTH_LONG).show();


            }
        });
        final Button levelFiveBtn = (Button) findViewById(R.id.lvl5Btn);

        if(check5)
            levelFiveBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
        else
            levelFiveBtn.setBackgroundColor(getResources().getColor(R.color.blue));
        levelFiveBtn.getBackground().setAlpha(128);
        levelFiveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                levelSelected = 5;

                if (check5)
                    StartLevel(v, levelSelected);
                else
                    Toast.makeText(getApplicationContext(), "Score 8 in level 4 to unlock", Toast.LENGTH_LONG).show();


            }
        });




        final Button infoBtn=(Button)findViewById(R.id.infoBtn);

        infoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Credits");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Developed by Arnold Asllani \n" +
                                    "E-mail:asllani94@gmail.com \n"+
                                "\nCopyright © All Rights Reserved ASLLANISOFT™")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });


                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();






            }
        });



    }



    public void StartLevel(View v,int levelSelected){
        try {
            Intent i=new Intent(getApplicationContext(),QuizActivity.class);
            i.putExtra("LEVEL_SELECTED",levelSelected);
            startActivity(i);
        }
        catch (Exception e){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
