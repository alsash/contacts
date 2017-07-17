package com.alsash.contacts.ui.screen.addedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alsash.contacts.R;
import com.alsash.contacts.app.ContactsApp;
import com.alsash.contacts.ui.screen.Navigator;
import com.bumptech.glide.Glide;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddEditActivity extends AppCompatActivity implements AddEditContract.View,
        AddEditImageListInteraction {

    @Inject
    AddEditPresenter presenter;
    @Inject
    Navigator navigator;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_name)
    EditText editName;
    @BindView(R.id.contact_surname)
    EditText editSurname;
    @BindView(R.id.contact_image)
    ImageView image;
    @BindView(R.id.contact_image_list)
    RecyclerView imageList;
    AddEditImageAdapter adapter;
    SimpleTextWatcher editNameListener = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            presenter.requestEditName(s.toString());
        }
    };
    SimpleTextWatcher editSurnameListener = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            presenter.requestEditSurname(s.toString());
        }
    };
    Unbinder unbinder;
    Snackbar snackbar;

    @Override
    public void onImageClick(int imageRes) {
        presenter.requestEditImage(imageRes);
    }

    @Override
    public void showImages(int[] imageRes) {
        adapter = new AddEditImageAdapter(imageRes, this);
        imageList.setAdapter(adapter);
    }

    @Override
    public void showImage(int imageRes) {
        Glide.with(image.getContext())
                .load(imageRes)
                .asGif()
                .error(R.mipmap.ic_launcher_round)
                .into(image);
        if (adapter != null) {
            int position = adapter.setCheckedImageRes(imageRes);
            if (position >= 0) imageList.scrollToPosition(position);
        }
    }

    @Override
    public void showName(String name) {
        editName.setText(name);
    }

    @Override
    public void showSurname(String surname) {
        editSurname.setText(surname);
    }

    @Override
    public void showTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    public void showContactSaveErrorMessageName(boolean focusName) {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(coordinator,
                    getString(R.string.save_error_message_name),
                    Snackbar.LENGTH_SHORT);
            snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
        } else {
            snackbar.setText(getString(R.string.save_error_message_name));
        }
        snackbar.show();
        if (focusName) {
            editName.requestFocus();
        } else {
            editSurname.requestFocus();
        }
    }

    @Override
    public void showContactSaveErrorMessageExist(String contactName) {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(coordinator,
                    getString(R.string.save_error_message_exist, contactName),
                    Snackbar.LENGTH_SHORT);
            snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
        } else {
            snackbar.setText(getString(R.string.save_error_message_exist, contactName));
        }
        snackbar.show();
    }

    @Override
    public void finishView() {
        if (navigator.hasTransition(this)) {
            ActivityCompat.finishAfterTransition(this);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            presenter.requestSave();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((ContactsApp) getApplication())
                .getAddEditComponent()
                .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageList.setNestedScrollingEnabled(false);

        editName.addTextChangedListener(editNameListener);
        editSurname.addTextChangedListener(editSurnameListener);

        UUID contactId = navigator.getContactId(getIntent());
        presenter.init(contactId == null,   // isNewContact
                savedInstanceState != null, // isSavedState
                contactId);
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
        setIntent(intent);
        UUID contactId = navigator.getContactId(intent);
        //                  isNewContact, isSavedState, contactId
        presenter.init(contactId == null, false, contactId);
        presenter.attach(this);
    }

    private static abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing
        }
    }
}
