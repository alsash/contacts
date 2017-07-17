package com.alsash.contacts.ui.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.Arrays;

/**
 * Simple helper for RecyclerView calculations
 */
public class RecyclerViewHelper {

    private static final String TAG = "RecyclerViewHelper";

    @SuppressWarnings("ConstantConditions")
    public static int getVisibleItemPosition(RecyclerView.LayoutManager lm,
                                             boolean last,
                                             boolean completely) {

        Class<? extends RecyclerView.LayoutManager> lmClass = lm.getClass();
        Class<LinearLayoutManager> linearLmClass = LinearLayoutManager.class;
        Class<StaggeredGridLayoutManager> staggeredLmClass = StaggeredGridLayoutManager.class;

        try {
            if (lmClass.equals(linearLmClass) || linearLmClass.isAssignableFrom(lmClass)) {

                if (last && completely)
                    return linearLmClass.cast(lm).findLastCompletelyVisibleItemPosition();
                if (last && !completely)
                    return linearLmClass.cast(lm).findLastVisibleItemPosition();
                if (!last && completely)
                    return linearLmClass.cast(lm).findFirstCompletelyVisibleItemPosition();
                if (!last && !completely)
                    return linearLmClass.cast(lm).findFirstVisibleItemPosition();


            } else if (lmClass == staggeredLmClass || staggeredLmClass.isAssignableFrom(lmClass)) {
                int[] into = null;
                if (last && completely)
                    into = staggeredLmClass.cast(lm).findLastCompletelyVisibleItemPositions(null);
                if (last && !completely)
                    into = staggeredLmClass.cast(lm).findLastVisibleItemPositions(null);
                if (!last && completely)
                    into = staggeredLmClass.cast(lm).findFirstCompletelyVisibleItemPositions(null);
                if (!last && !completely)
                    into = staggeredLmClass.cast(lm).findFirstVisibleItemPositions(null);

                if (into != null && into.length > 0) {
                    Arrays.sort(into);
                    return last ? into[into.length - 1] : into[0];
                }
            }
        } catch (ClassCastException e) {
            Log.d(TAG, e.getMessage(), e);
        }
        return -1;
    }
}
