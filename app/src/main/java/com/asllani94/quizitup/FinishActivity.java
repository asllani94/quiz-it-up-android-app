package com.asllani94.quizitup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
private int currentLevel;
    private  boolean locked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finish);



        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String value = extras.getString("FINISH");
            currentLevel=extras.getInt("ACTUAL_LEVEL");
            locked=extras.getBoolean("UNLOCKABLE");
            TextView finishTxt=(TextView)findViewById(R.id.finishMsg);
            finishTxt.setText(value);
        }
        if(locked&&currentLevel!=5)//should be changed when new level added
        OpenLevel(currentLevel+1);

        final AlphaAnimation buttonClick = new AlphaAnimation(0.6F, 0.4F);

        final Button replay =(Button)findViewById(R.id.replayBtn);
        replay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                ReplayGame(v,currentLevel);
            }
        });

        final Button quitGame =(Button)findViewById(R.id.quitBtn);
        quitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        final Button levels =(Button)findViewById(R.id.levelsBtn);
        levels.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);
               backToLevels();

            }
        });

    }
    public void ReplayGame(View v,int currenLvl){
        Intent i=new Intent(getApplicationContext(),QuizActivity.class);
        i.putExtra("LEVEL_SELECTED",currenLvl);
        startActivity(i);

    }
    public void backToLevels(){
        Intent i=new Intent(getApplicationContext(),LevelsActivity.class);

        startActivity(i);

    }
    private void OpenLevel(int Level){
        AchievmentOperations op=new AchievmentOperations(this);
        op.open();
        op.addAchievment(Level, 1);
        op.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end, menu);
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
