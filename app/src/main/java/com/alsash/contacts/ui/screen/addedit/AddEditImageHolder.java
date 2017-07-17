package com.alsash.contacts.ui.screen.addedit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alsash.contacts.R;

/**
 * A simple view holder
 */
public class AddEditImageHolder extends RecyclerView.ViewHolder {

    ImageView image;

    private AddEditImageHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView;
    }

    public static AddEditImageHolder create(@NonNull ViewGroup parent) {
        return new AddEditImageHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_edit_activity_image_item, parent, false));
    }
}
