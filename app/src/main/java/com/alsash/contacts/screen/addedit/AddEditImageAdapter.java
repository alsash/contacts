package com.alsash.contacts.screen.addedit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alsash.contacts.R;
import com.bumptech.glide.Glide;

/**
 * A Simple adapter for the Start List of the Contacts
 */
public class AddEditImageAdapter extends RecyclerView.Adapter<AddEditImageHolder> {

    private final int[] imageRes;
    private final AddEditImageListInteraction interaction;
    private int checkedPosition = -1;

    public AddEditImageAdapter(int[] imageRes, AddEditImageListInteraction interaction) {
        this.imageRes = imageRes;
        this.interaction = interaction;
    }

    public int setCheckedImageRes(int checkedImageRes) {
        for (int i = 0; i < imageRes.length; i++) {
            if (imageRes[i] == checkedImageRes) {
                checkedPosition = i;
                notifyDataSetChanged();
                return i;
            }
        }
        return -1;
    }

    @Override
    public AddEditImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AddEditImageHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final AddEditImageHolder holder, int position) {
        Glide.with(holder.image.getContext())
                .load(imageRes[position])
                .asBitmap()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.image);
        holder.image.setAlpha(checkedPosition == position ? 1.0F : 0.2F);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedPosition = holder.getAdapterPosition();
                interaction.onImageClick(imageRes[checkedPosition]);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageRes.length;
    }
}
