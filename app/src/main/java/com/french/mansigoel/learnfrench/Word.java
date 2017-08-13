package com.french.mansigoel.learnfrench;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * custom class to store english translation and meowk words translation
 */
public class Word extends AppCompatActivity {
    private String meowkWords, defaultEnglishWords;
    private int resourceId = NoImage;
    private static final int NoImage = -1;
    private int songid;

    //constructor to accept english and meowk words for phrases activity
    public Word(String english, String meowk,int songId) {
        defaultEnglishWords = english;
        meowkWords = meowk;
        songid=songId;
    }

    //constructor to accept english,miowk and image id for colors,numbers and family activity
    public Word(String english, String meowk, int imageId, int songId) {
        defaultEnglishWords = english;
        meowkWords = meowk;
        resourceId = imageId;
        songid = songId;
    }

    //function to return meowk words
    public String getMeowkTranslation() {
        return meowkWords;
    }

    //function to return english words
    public String getDefaultEnglishTranslation() {
        return defaultEnglishWords;
    }

    //fuction to return image resource id
    public int getResourceId() {
        return resourceId;
    }

    //function to check whether activity refers to image or not
    public boolean hasImage() {
        return resourceId != NoImage;
    }

    //function to return audio file
    public int getSongid() {
        return songid;
    }
}
