package com.alsash.contacts.screen.start;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsash.contacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple view holder
 */
public class StartListContactHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_image)
    ImageView image;
    @BindView(R.id.contact_name)
    TextView name;
    @BindView(R.id.contact_position)
    TextView position;

    StartListContactHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static StartListContactHolder create(@NonNull ViewGroup parent) {
        return new StartListContactHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.start_activity_contact_item, parent, false));
    }
}
