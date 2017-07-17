package com.alsash.contacts.screen.addedit;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.alsash.contacts.R;
import com.alsash.contacts.app.ContactsApp;
import com.alsash.contacts.screen.Navigator;
import com.bumptech.glide.Glide;

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
    public void showContactSaveErrorMessage(String contactName) {
        if (snackbar != null && snackbar.isShown()) snackbar.dismiss();
        snackbar = Snackbar.make(coordinator,
                getString(R.string.save_error_message, contactName),
                Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void finishView() {
        finish();
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
        imageList.setNestedScrollingEnabled(false);

        editName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                presenter.requestEditName(s.toString());
            }
        });

        editSurname.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                presenter.requestEditSurname(s.toString());
            }
        });

        presenter.setContactId(navigator.getThisId(getIntent()));
        presenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        unbinder.unbind();
        super.onDestroy();
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
