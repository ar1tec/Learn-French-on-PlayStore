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

public class FamilyActivity extends AppCompatActivity {
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

        words.add(new Word("Father", "le père", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "la mère", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son", "le fils", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "la fille", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Uncle", "l’oncle ", R.drawable.family_older_brother, R.raw.family_uncle));
        words.add(new Word("Brother", "le frère", R.drawable.family_younger_brother, R.raw.family_brother));
        words.add(new Word("Aunt", "la tante", R.drawable.family_older_sister, R.raw.family_aunt));
        words.add(new Word("Sister", "la sœur", R.drawable.family_younger_sister, R.raw.family_sister));
        words.add(new Word("Grandmother ", "la grand-mère  ", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandfather", "le grand-père", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

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