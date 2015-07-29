package com.yahoo.instagramphotoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jhkao on 7/27/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_v2, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        // profile image
        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(ivProfile);

        // username
        tvUsername.setText(photo.username);

        // post time
        int postTime = Integer.parseInt(photo.postTime);
        int currentTime = (int) (new Date().getTime()/1000);
        int diffTime = currentTime - postTime;

        int day = (int)TimeUnit.SECONDS.toDays(diffTime);
        long hours = TimeUnit.SECONDS.toHours(diffTime) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(diffTime) - (TimeUnit.SECONDS.toHours(diffTime) * 60);

        String strPostTime = "";
        if (0 < day) {
            strPostTime = strPostTime.concat(String.valueOf(day) + "d");
        } else if (0 < hours) {
            strPostTime = strPostTime.concat(String.valueOf(hours) + "h");
        } else if (0 < minute) {
            strPostTime = strPostTime.concat(String.valueOf(minute) + "m");
        } else {
            strPostTime = strPostTime.concat(String.valueOf(diffTime) + "s");
        }
        tvTime.setText(strPostTime);

        // image
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        // likes
        String strLikes = getContext().getString(R.string.likes);
        tvLikes.setText(String.format(strLikes, photo.likesCount));

        // caption
        tvCaption.setText(photo.caption);

        return convertView;
    }
}
