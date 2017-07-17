package com.alsash.contacts.screen.start;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alsash.contacts.R;
import com.alsash.contacts.data.Contact;
import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;

/**
 * A Simple adapter for the Start List of the Contacts
 */
public class StartListAdapter extends RecyclerView.Adapter<StartListContactHolder> {

    private final SortedList<Contact> contacts;
    private StartListInteraction interaction;

    public StartListAdapter(SortedList<Contact> contacts) {
        this.contacts = contacts;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                int contactsSize = StartListAdapter.this.contacts.size();
                int changedPosition = positionStart + itemCount;
                if (changedPosition >= contactsSize) return;
                int changedCount = contactsSize - changedPosition;
                notifyItemRangeChanged(changedPosition, changedCount); // update positions number
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                int contactsSize = StartListAdapter.this.contacts.size();
                if (positionStart >= contactsSize - 1) return;
                int changedCount = contactsSize - positionStart;
                notifyItemRangeChanged(positionStart, changedCount); // update positions number
            }
        });
    }

    public void setInteraction(StartListInteraction interaction) {
        this.interaction = interaction;
    }

    @Override
    public StartListContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StartListContactHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final StartListContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.name());
        holder.position.setText(String.valueOf(position + 1));
        holder.ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (interaction == null) return;
                interaction.onContactClick(contacts.get(holder.getAdapterPosition()));
            }
        });
        Glide.with(holder.image.getContext())
                .load(contact.gifRes())
                .asBitmap()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
