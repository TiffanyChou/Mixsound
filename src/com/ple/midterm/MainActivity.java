package com.ple.midterm;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private MediaPlayer mMediaPlayer;

    private static final int MAX_SOUND_COUNT = 5;
    private SoundPool mSoundPool;
    private SoundPool mSoundPool2;
    private int[] mSounds = new int[MAX_SOUND_COUNT];

    private boolean isPrepared = false;
    //
    private AnimationDrawable animDrawable;
    private AnimationDrawable animDrawable1;
    private AnimationDrawable animDrawable2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setBackgroundResource(R.drawable.image_set);
		animDrawable = (AnimationDrawable) imageView.getBackground();
		ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView1.setBackgroundResource(R.drawable.image_set2);
		animDrawable1 = (AnimationDrawable) imageView1.getBackground();
		ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView2.setBackgroundResource(R.drawable.image_set3);
		animDrawable2 = (AnimationDrawable) imageView2.getBackground();

        
    }
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!animDrawable.isRunning()) {
				animDrawable.start();
			} else {
				animDrawable.stop();
			}	
			if (!animDrawable1.isRunning()) {
				animDrawable1.start();
			} else {
				animDrawable1.stop();
			}	
			if (!animDrawable2.isRunning()) {
				animDrawable2.start();
			} else {
				animDrawable2.stop();
			}	
			return true;
		}
		return super.onTouchEvent(event);
	}


    public void onUntanButton(View v) {
        mSoundPool2.play(mSounds[1], 1.0F, 1.0F, 0, 3, 1.0F);
        if (!animDrawable.isRunning()) {
			animDrawable.start();
		} else {
			animDrawable.stop();
		}
    }

    public void onUntanHighButton(View v) {
        mSoundPool.play(mSounds[0], 1.0F, 1.0F, 0, 2, 2.0F);
        if (!animDrawable2.isRunning()) {
			animDrawable2.start();
		} else {
			animDrawable2.stop();
		}	
    }

    public void onUntanLowButton(View v) {
        mSoundPool2.play(mSounds[1], 1.0F, 1.0F, 0, 3, 0.5F);
    }
    public void testButton(View v) {
        mSoundPool.play(mSounds[1], 1.0F, 1.0F, 0, 0, 0.5F);
    }
   public void tryButton(View v){
	   mSoundPool2.play(mSounds[1], 1.0F, 1.0F, 0, 7, 2.0F);
       if (!animDrawable2.isRunning()) {
			animDrawable2.start();
		} else {
			animDrawable2.stop();
		}
   }
    @Override
    public void onResume() {
        super.onResume();

        mMediaPlayer = MediaPlayer.create(this, R.raw.song);
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
 
            }
        });

        mSoundPool = new SoundPool(MAX_SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        mSoundPool2 = new SoundPool(MAX_SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        mSounds[0] = mSoundPool.load(this, R.raw.rubs, 1);
        mSounds[1] = mSoundPool2.load(this, R.raw.sample, 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMediaPlayer.release();
        mSoundPool.release();
    }

    public void onStartButton(View view) {
        if (isPrepared) {
            mMediaPlayer.start();
           
        } else {
            Toast.makeText(this, "正在載入聲音檔的啦",
                           Toast.LENGTH_LONG).show();
        }
    }

    public void onPauseButton(View view) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }

    }

    public void onStopButton(View view) {
   
        if (!mMediaPlayer.isPlaying()) {
            return;
        }
        mMediaPlayer.stop();
        isPrepared = false;
        try {
            mMediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
