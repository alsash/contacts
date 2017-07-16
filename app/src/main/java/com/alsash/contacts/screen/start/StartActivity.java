package com.alsash.contacts.screen.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alsash.contacts.R;
import com.alsash.contacts.app.ContactsApp;
import com.alsash.contacts.screen.Navigator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StartActivity extends AppCompatActivity implements StartContract.View {

    @Inject
    StartPresenter presenter;
    @Inject
    StartListAdapter adapter;
    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Unbinder unbinder;

    @OnClick(R.id.fab)
    public void onFabClick() {
        presenter.add();
        // navigator.toAddContactScreen(this);
        //   Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
        //           .setAction("Action", null).show();
    }

    @Override
    public void showContact(int position) {
        list.scrollToPosition(position);
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
        list.setAdapter(adapter);

        presenter.attach(this);
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
