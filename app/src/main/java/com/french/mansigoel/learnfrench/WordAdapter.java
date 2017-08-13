package com.french.mansigoel.learnfrench;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.french.mansigoel.learnfrench.R;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<com.french.mansigoel.learnfrench.Word> {
    private int activityColor;
    MediaPlayer mediaPlayer;



    public WordAdapter(Activity context, ArrayList<com.french.mansigoel.learnfrench.Word> words, int code) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        activityColor = code;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            //creating a new view from scratch using inflater if no view is found currently
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the object located at this position in the list
        //getItem is function declared in adapter class which is a superclass of arrayAdapter
        // which is further superclass of WordAdapter
        final com.french.mansigoel.learnfrench.Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView mewok_text_view = (TextView) listItemView.findViewById(R.id.mewok_text_view);
        // set this text on the name TextView
        mewok_text_view.setText(currentWord.getMeowkTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView default_text_view = (TextView) listItemView.findViewById(R.id.default_text_view);
        // set this text on the number TextView
        default_text_view.setText(currentWord.getDefaultEnglishTranslation());

        ImageView imageview = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            imageview.setImageResource(currentWord.getResourceId());
            imageview.setVisibility(View.VISIBLE);
        }
        else {
            imageview.setVisibility(View.GONE);
            //imageview.setVisibility(View.INVISIBLE) was not working fine
        }

        View textContainer = listItemView.findViewById(R.id.textContainer);
        int color = ContextCompat.getColor(getContext(), activityColor);
        textContainer.setBackgroundColor(color);


        return listItemView;
    }}



