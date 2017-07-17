package com.alsash.contacts.ui.screen.start;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsash.contacts.R;
import com.andexert.library.RippleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple view holder
 */
public class StartListContactHolder extends RecyclerView.ViewHolder {

    @Nullable
    @BindView(R.id.contact_ripple)
    RippleView ripple; // Only for pre Lollipop
    @BindView(R.id.contact_image)
    ImageView image;
    @BindView(R.id.contact_name)
    TextView name;
    @BindView(R.id.contact_position)
    TextView position;

    private StartListContactHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static StartListContactHolder create(@NonNull ViewGroup parent) {
        return new StartListContactHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.start_activity_contact_item, parent, false));
    }


    public void setOnClickListener(final View.OnClickListener listener) {
        if (ripple != null) {
            ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    listener.onClick(rippleView);
                }
            });
        } else {
            itemView.setOnClickListener(listener);
        }
    }
}
