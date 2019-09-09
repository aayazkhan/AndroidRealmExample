package com.transo.realmexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.transo.realmexample.model.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Book>> {

    public final static String ACTION_DELETE_BOOK = "DELETE_BOOK";

    private Realm realm;

    private RecyclerView recyclerView;

    private BookAdapter bookAdapter;
    private RealmResults<Book> books;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMM dd YYYY hh:mm", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        realm = Realm.getDefaultInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        books = realm.where(Book.class).findAll();

        bookAdapter = new BookAdapter(books, viewClickListner, editClickListner, deleteClickListner);

        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            String action = data.getAction();
            if (action.equals(ACTION_DELETE_BOOK)) {
                String id = data.getStringExtra("ID");
                for (Book book : books) {
                    if (book.getId().equals(id)) {
                        onDeleteClickSubmit(book);
                        break;
                    }
                }
            }
        }

    }

    Book.OnViewClickListner viewClickListner = (v, position, book) -> {
        startActivityForResult(
                new Intent(MainActivity.this, BookViewActivity.class)
                        .putExtra("ID", book.getId())
                , 100);
    };

    Book.OnEditClickListner editClickListner = (v, position, book) -> {
        showDialogToEditBook(book);
    };
    Book.OnDeleteClickListner deleteClickListner = (v, position, book) -> {
        showConfirmationDialogToDelete(book);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                showDialogToAddNewBook();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showDialogToAddNewBook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout editTextInputLayout = new TextInputLayout(MainActivity.this);
        EditText editText = new EditText(MainActivity.this);
        editText.setHint("Title");
        LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextLayoutParams.setMargins(60, 0, 60, 0);

        editTextInputLayout.addView(editText);

        TextInputLayout editTextDescriptionInputLayout = new TextInputLayout(MainActivity.this);
        EditText editTextDescription = new EditText(MainActivity.this);
        editTextDescription.setHint("Description");
        editTextDescription.setLines(3);
        editTextDescription.setGravity(Gravity.TOP | Gravity.LEFT);
        LinearLayout.LayoutParams editTextDescriptionLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextDescriptionLayoutParams.setMargins(60, 0, 60, 0);

        editTextDescriptionInputLayout.addView(editTextDescription);

        linearLayout.addView(editTextInputLayout, editTextLayoutParams);
        linearLayout.addView(editTextDescriptionInputLayout, editTextDescriptionLayoutParams);

        builder.setTitle("ADD BOOK");
        builder.setView(linearLayout);

        builder.setPositiveButton("submit", (dialogInterface, i) -> {
            String bookTitle = editText.getText().toString().trim();
            String bookDescription = editTextDescription.getText().toString().trim();
            if (!bookTitle.equals("") && !bookDescription.equals("")) {
                onAddClickSubmit(bookTitle, bookDescription);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void showDialogToEditBook(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout editTextInputLayout = new TextInputLayout(MainActivity.this);
        EditText editText = new EditText(MainActivity.this);
        editText.setHint("Title");
        LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextLayoutParams.setMargins(60, 0, 60, 0);

        editText.setText(book.getTitle());
        editText.setSelection(book.getTitle().length());
        editTextInputLayout.addView(editText);

        TextInputLayout editTextDescriptionInputLayout = new TextInputLayout(MainActivity.this);
        EditText editTextDescription = new EditText(MainActivity.this);
        editTextDescription.setHint("Description");
        editTextDescription.setLines(3);
        editTextDescription.setGravity(Gravity.TOP | Gravity.LEFT);
        LinearLayout.LayoutParams editTextDescriptionLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextDescriptionLayoutParams.setMargins(60, 0, 60, 0);

        editTextDescription.setText(book.getDescription());
        editTextDescription.setSelection(book.getDescription().length());
        editTextDescriptionInputLayout.addView(editTextDescription);

        linearLayout.addView(editTextInputLayout, editTextLayoutParams);
        linearLayout.addView(editTextDescriptionInputLayout, editTextDescriptionLayoutParams);

        builder.setTitle("ADD BOOK");
        builder.setView(linearLayout);

        builder.setPositiveButton("submit", (dialogInterface, i) -> {
            String bookTitle = editText.getText().toString().trim();
            String bookDescription = editTextDescription.getText().toString().trim();
            if (!bookTitle.equals("") && !bookDescription.equals("")) {
                onEditClickSubmit(book, bookTitle, bookDescription);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void showConfirmationDialogToDelete(Book book) {
        //TODO
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert")
                .setMessage("Do you want to delete ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    onDeleteClickSubmit(book);
                }).show();

    }

    public void onDeleteClickSubmit(Book book) {
        realm.beginTransaction();
        book.deleteFromRealm();
        realm.commitTransaction();
        bookAdapter.notifyDataSetChanged();
    }

    public void onEditClickSubmit(Book book, String bookTitle, String bookDescription) {
        realm.beginTransaction();
        book.setTitle(bookTitle);
        book.setDescription(bookDescription);
        book.setModifiledOn(new Date());
        realm.commitTransaction();
        bookAdapter.notifyDataSetChanged();
    }

    public void onAddClickSubmit(String bookTitle, String bookDescription) {
        realm.beginTransaction();
        Book book = realm.createObject(Book.class, "" + System.currentTimeMillis());
        book.setTitle(bookTitle);
        book.setDescription(bookDescription);
        book.setAddedOn(new Date());
        book.setModifiledOn(new Date());
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onChange(RealmResults<Book> dogRealmResults) {
        bookAdapter.notifyDataSetChanged();
    }
}
