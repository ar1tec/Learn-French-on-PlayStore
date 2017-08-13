package com.french.mansigoel.learnfrench;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.french.mansigoel.learnfrench.R;
import com.french.mansigoel.learnfrench.Word;
import com.french.mansigoel.learnfrench.WordAdapter;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    MediaPlayer mmediaPlayer;
    AudioManager maudioManager;
    private MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mmediaPlayer.pause();
                        mmediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mmediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_listview);
        maudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Sunday", "Le Dimanche", R.raw.calendar_sunday));
        words.add(new Word("Monday", "Le Lundi",R.raw.calender_monday));
        words.add(new Word("Tuesday", "Le Mardi",R.raw.calendar_tuesday));
        words.add(new Word("Wednesday", "Le Mercredi",R.raw.calendar_wed));
        words.add(new Word("Thursday", "Le Jeudi",R.raw.calendar_thurs));
        words.add(new Word("Friday", "Le Vendredi",R.raw.calendar_friday));
        words.add(new Word("Saturday", "Le Samedi",R.raw.calendar_sat));
        words.add(new Word("January", "Janvier",R.raw.calendar_jan));
        words.add(new Word("February", "Février",R.raw.calendar_feb));
        words.add(new Word("March", "Mars",R.raw.calendar_mar));
        words.add(new Word("April", "Avril",R.raw.calendar_april));
        words.add(new Word("May", "Mai",R.raw.calendar_may));
        words.add(new Word("June", "Juin",R.raw.calendar_june));
        words.add(new Word("July", "Juillet",R.raw.calendar_july));
        words.add(new Word("August", "Août",R.raw.calendar_august));
        words.add(new Word("September", "Septembre",R.raw.calendar_sept));
        words.add(new Word("October", "Octobre",R.raw.calendar_oct));
        words.add(new Word("November", "Novembre",R.raw.calendar_nov));
        words.add(new Word("December", "Décembre",R.raw.calendar_dec));



        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_calendar);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = maudioManager.requestAudioFocus(onAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mmediaPlayer=MediaPlayer.create(parent.getContext(),word.getSongid());
                    mmediaPlayer.start();
                    mmediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }}
        });

        listView.setAdapter(itemsAdapter);


    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mmediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mmediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mmediaPlayer = null;
            maudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}