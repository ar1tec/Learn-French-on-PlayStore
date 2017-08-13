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

public class PhrasesActivity extends AppCompatActivity {
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

        words.add(new Word("what would you like to drink", "Que voulez-vous boire", R.raw.phrase_what_would_you_like_to_drink));
        words.add(new Word("Can you help me", "Pouvez-vous m'aider", R.raw.phrase_can_you_help_me));
        words.add(new Word("Excuse me", "Excusez-moi", R.raw.phrase_excuse_me));
        words.add(new Word("How do you like it here", "Comment aimez-vous ici", R.raw.phrase_how_do_you_like_it_here));
        words.add(new Word("I want to go home", "Je veux aller a la maison", R.raw.phrase_i_want_to_go_home));
        words.add(new Word("What do you want to do", "Qu'est-ce que tu veux faire?", R.raw.phrase_what_do_you_want_to_do));
        words.add(new Word("Would you like a coffee", "Voulez-vous un café", R.raw.phrase_would_you_like_a_coffee));
        words.add(new Word("We feel like", "Nous nous sentons comme", R.raw.phrase_we_feel_like));
        words.add(new Word("The weather is so bad", "Le temps est si mauvais", R.raw.phrase_the_weather_is_so_bad));
        words.add(new Word("The movie is exciting", "Le film est passionnant", R.raw.phrase_the_movie_is_exciting));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_phrases);

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