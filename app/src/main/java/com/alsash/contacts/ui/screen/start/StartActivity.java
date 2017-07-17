package com.alsash.contacts.ui.screen.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.alsash.contacts.R;
import com.alsash.contacts.app.ContactsApp;
import com.alsash.contacts.data.Contact;
import com.alsash.contacts.ui.screen.Navigator;
import com.alsash.contacts.ui.utils.RecyclerViewHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StartActivity extends AppCompatActivity implements StartContract.View,
        StartListInteraction {

    @Inject
    StartPresenter presenter;
    @Inject
    StartListAdapter adapter;
    @Inject
    Navigator navigator;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Unbinder unbinder;
    Snackbar snackbar;

    private ItemTouchHelper.Callback swipeCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder source,
                              RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
            presenter.requestContactDelete(holder.getAdapterPosition());
        }
    };
    private ItemTouchHelper contactsTouchHelper = new ItemTouchHelper(swipeCallback);

    @OnClick(R.id.fab)
    public void onFabClick() {
        presenter.requestContactAdd();
    }

    @Override
    public void onContactClick(Contact contact) {
        presenter.requestContactEdit(contact);
    }

    @Override
    public void focusContact(int position) {
        RecyclerView.LayoutManager lm = list.getLayoutManager();              //  last, completely
        int firstVisiblePosition = RecyclerViewHelper.getVisibleItemPosition(lm, false, true);
        int lastVisiblePosition = RecyclerViewHelper.getVisibleItemPosition(lm, true, true);
        if (position >= lastVisiblePosition || position <= firstVisiblePosition)
            list.scrollToPosition(position);
    }

    @Override
    public void showContactAdd() {
        navigator.toContactAddScreen(this);
    }

    @Override
    public void showContactEdit(Contact contact) {
        navigator.toContactEditScreen(this, contact);
    }

    @Override
    public void showDeleteMessage(Contact contact) {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(coordinator,
                    getString(R.string.delete_message, contact.name()),
                    Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.delete_undo,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.rejectContactDelete();
                        }
                    });
        } else {
            snackbar.setText(getString(R.string.delete_message, contact.name()));
        }
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((ContactsApp) getApplication())
                .getStartComponent()
                .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        adapter.setInteraction(this);
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(),
                DividerItemDecoration.VERTICAL));
        contactsTouchHelper.attachToRecyclerView(list);

        presenter.attach(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.detach();
        presenter.attach(this);
    }
}
