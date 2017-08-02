package com.asllani94.quizitup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.LinkedList;


public class QuizActivity extends Activity  {
    LinkedList<Question> allQuestions = null;
    private int check;
    private int typed;
    private TextView txt;
    private TextView score;
    private int point = 0;
    private int chance = 3;
    private String info;
    private int counter=0;

    private SoundPool soundPool;
    private int soundID;
    private  int soundID2;
    boolean loaded = false;

    private Question next;
    private QuestionOperations op;

    private int level;
    private  boolean unlock;
    private  AchievmentOperations oprt;
    private boolean check1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        Bundle extras = getIntent().getExtras();

      try{  if(extras !=null) {
             level = extras.getInt("LEVEL_SELECTED");

        }}
      catch (Exception e){

      }



        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.correct, 1);
        soundID2=soundPool.load(this,R.raw.wrong,1);





        op=new QuestionOperations(this);
         op.open();


         allQuestions=op.getQuestionByCategory(level);
         op.close();



        try {
            next = getNextQuestion(allQuestions);
        }
        catch (Exception e){}

        txt=(TextView)findViewById(R.id.textView);



        txt.setText(next.getQuestion());
        check=Integer.valueOf(next.getAnswer());

        //Define TextViews
        score=(TextView)findViewById(R.id.score);


        oprt=new AchievmentOperations(this);
        oprt.open();
        check1=oprt.isLevelOpen(level+1);



        oprt.close();









        final Button trueBtn = (Button) findViewById(R.id.trueBtn);



        trueBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                typed = 1;
                if (check == typed) {
                    point++;


                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    // Is the sound loaded already?
                    if (loaded) {
                        soundPool.play(soundID, volume, volume, 1, 0, 1f);

                    }


                    score.setText("Score:" + String.valueOf(point));
                    Context context = getApplicationContext();

                    CharSequence text = "Correct! You have " + point + " points";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } else {
                    chance--;

                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    // Is the sound loaded already?
                    if (loaded) {
                        soundPool.play(soundID2, volume, volume, 1, 0, 1f);

                    }


                    removeLife(chance);
                    Context context = getApplicationContext();

                    CharSequence text = "Wrong, " + chance + " chances left!";
                    int duration = Toast.LENGTH_SHORT;
                    if (chance == 0) {

                        info = ("You Lost! \n" + "Score:" + point);
                        unlock = false;
                        EndGame(v, info, level, unlock);
                    }
                    if (chance > 0) {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }


                }
                try {


                    next = getNextQuestion(allQuestions);

                    txt.setText(next.getQuestion());
                    check = Integer.valueOf(next.getAnswer());
                } catch (Exception e) {
                    unlock = true;
                    if (!check1)
                        info = ("You won! \n" +
                                "Score:" + String.valueOf(point) + "\n" +
                                "Level:" + String.valueOf(level + 1) + " unlocked!");
                    else
                        info = ("You won! \n" +
                                "Score:" + String.valueOf(point)
                        );

                    EndGame(v, info, level, unlock);


                }

            }
        });

        final Button falseBtn = (Button) findViewById(R.id.falsebtn);
        falseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                typed = 0;
                if (check == typed) {
                    point++;

                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    // Is the sound loaded already?
                    if (loaded) {
                        soundPool.play(soundID, volume, volume, 1, 0, 1f);

                    }
                    score.setText("Score:" + String.valueOf(point));
                    Context context = getApplicationContext();
                    CharSequence text = "Correct! You have " + String.valueOf(point) + " points";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } else {
                    chance--;

                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    // Is the sound loaded already?
                    if (loaded) {
                        soundPool.play(soundID2, volume, volume, 1, 0, 1f);

                    }
                    removeLife(chance);
                    Context context = getApplicationContext();
                    CharSequence text = "Wrong, " + String.valueOf(chance) + " chances left!";
                    int duration = Toast.LENGTH_SHORT;
                    if(chance==0){
                        unlock=false;

                        info=("You Lost! \n"+"Score:"+point);
                        EndGame(v,info,level,unlock);
                    }
                    if(chance>0){
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();}


                }
                try {


                    next=getNextQuestion(allQuestions);
                    txt.setText(next.getQuestion());

                    check = Integer.valueOf(next.getAnswer());}
                  catch (Exception e){
                      unlock=true;

                      if(!check1)
                          info=(  "You won! \n" +
                                  "Score:" + String.valueOf(point)+"\n"+
                                  "Level:"+String.valueOf(level+1)+" unlocked!");
                      else
                          info=(  "You won! \n" +
                                  "Score:" + String.valueOf(point)
                          );
                    EndGame(v,info,level,unlock);


                }


            }
        });


    }
    public void EndGame(View v,String messag,int level,boolean unlock){
        Intent i=new Intent(getApplicationContext(),FinishActivity.class);
        i.putExtra("FINISH",messag);
        i.putExtra("UNLOCKABLE",unlock);
        i.putExtra("ACTUAL_LEVEL",level);
        startActivity(i);

    }


    public void removeLife(int remain){


      if(remain==2){
            ImageView life3=(ImageView)findViewById(R.id.heart3);
            life3.setVisibility(View.INVISIBLE);

        }
        else if(remain==1){
            ImageView life2=(ImageView)findViewById(R.id.heart2);
            life2.setVisibility(View.INVISIBLE);

        }
        else {

            ImageView life1=(ImageView)findViewById(R.id.heart1);
            life1.setVisibility(View.INVISIBLE);
        }

    }
    public Question getNextQuestion(LinkedList<Question> list)  {
        Question question;









  try{
         question=list.poll();

    }
    catch (Exception e){
        return null;




    }
        return question;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
