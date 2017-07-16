package com.alsash.contacts.screen.start;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alsash.contacts.data.Contact;

/**
 * A Simple adapter for the Start List of the Contacts
 */
public class StartListAdapter extends RecyclerView.Adapter<StartListContactHolder> {

    private final SortedList<Contact> contacts;

    public StartListAdapter(SortedList<Contact> contacts) {
        this.contacts = contacts;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount); // update positions number
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount); // update positions number
            }
        });
    }

    @Override
    public StartListContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StartListContactHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(StartListContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText((contact.person().name() + " " + contact.person().surname()).trim());
        holder.position.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
