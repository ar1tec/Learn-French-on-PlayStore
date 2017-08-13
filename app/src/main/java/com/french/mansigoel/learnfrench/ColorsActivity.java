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

public class ColorsActivity extends AppCompatActivity {
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

        words.add(new Word("Red", "Rouge", R.drawable.color_red, R.raw.colors_red));
        words.add(new Word("Green", "Vert", R.drawable.color_green, R.raw.colors_green));
        words.add(new Word("Brown", "Marron", R.drawable.color_brown, R.raw.colors_brown));
        words.add(new Word("Gray", "Gris ", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black", "Noir ", R.drawable.color_black, R.raw.colors_black));
        words.add(new Word("White", "Blanc ", R.drawable.color_white, R.raw.colors_white));
        words.add(new Word("Orange", "Orange ", R.drawable.color_orange, R.raw.colors_orange));
        words.add(new Word("Blue", "Bleu", R.drawable.color_blue, R.raw.colors_blue));
        words.add(new Word("Yellow", "Jaune", R.drawable.color_yellow, R.raw.colors_yellow));


        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_colors);

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