package com.french.mansigoel.learnfrench;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.french.mansigoel.learnfrench.R;
import com.french.mansigoel.learnfrench.Word;
import com.french.mansigoel.learnfrench.WordAdapter;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mmediaPlayer;
    AudioManager maudioManager;
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
    private MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_listview);
        maudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("One", "Un", R.drawable.number_one,R.raw.numbers_one));
        words.add(new Word("Two", "Deux", R.drawable.number_two,R.raw.numbers_two));
        words.add(new Word("Three", "Trois", R.drawable.number_three,R.raw.numbers_three));
        words.add(new Word("Four", "Quatre", R.drawable.number_four,R.raw.numbers_four));
        words.add(new Word("Five", "Cinq", R.drawable.number_five,R.raw.numbers_five));
        words.add(new Word("Six", "Six", R.drawable.number_six,R.raw.numbers_six));
        words.add(new Word("Seven", "Sept", R.drawable.number_seven,R.raw.numbers_seven));
        words.add(new Word("Eight", "Huit", R.drawable.number_eight,R.raw.numbers_eight));
        words.add(new Word("Nine", "Neuf", R.drawable.number_nine,R.raw.numbers_nine));
        words.add(new Word("Ten", "Dix", R.drawable.number_ten,R.raw.numbers_ten));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
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
    private void releaseMediaPlayer(){
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