package com.alsash.contacts.ui.screen.start;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import com.alsash.contacts.data.Contact;

/**
 * This represents sorting order, according to requirements:
 * List should be always sorted by name (firstly) and surname (secondly).
 */
public class StartListSortedCallback extends SortedList.Callback<Contact> {

    private RecyclerView.Adapter adapter;

    StartListSortedCallback() {
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    /**
     * compare two {@link Contact} by {@link com.alsash.contacts.data.Person}
     * name and surname properties ignoring case.
     *
     * @param thisContact - first contact to compare
     * @param thatContact - second contact to compare
     * @return - a negative integer, zero, or a positive integer as the
     * first contact is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Contact thisContact, Contact thatContact) {
        if (thisContact == null && thatContact == null) return 0;
        if (thisContact == null) return -1;
        if (thatContact == null) return 1;
        if (thisContact.equals(thatContact)) return 0;
        int nameSign = thisContact.person().name().compareToIgnoreCase(thatContact.person().name());
        if (nameSign != 0) return nameSign;
        return thisContact.person().surname().compareToIgnoreCase(thatContact.person().surname());
    }

    @Override
    public void onChanged(int position, int count) {
        if (adapter != null) adapter.notifyItemRangeChanged(position, count);
    }

    @Override
    public boolean areContentsTheSame(Contact oldContact, Contact newContact) {
        return areItemsTheSame(oldContact, newContact);
    }

    @Override
    public boolean areItemsTheSame(Contact thisContact, Contact thatContact) {
        return thisContact != null && thatContact != null && thisContact.equals(thatContact);
    }

    @Override
    public void onInserted(int position, int count) {
        if (adapter != null) adapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        if (adapter != null) adapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        if (adapter != null) adapter.notifyItemMoved(fromPosition, toPosition);
    }
}
