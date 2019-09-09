package com.transo.realmexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.transo.realmexample.model.Book;

import io.realm.Realm;

public class BookViewActivity extends AppCompatActivity {

    private Realm realm;
    private Book book;

    private TextView textViewBookName, textViewBookDescription, textViewAddedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        realm = Realm.getDefaultInstance();

        textViewBookName = findViewById(R.id.textViewBookName);
        textViewBookDescription = findViewById(R.id.textViewBookDescription);
        textViewAddedOn = findViewById(R.id.textViewAddedOn);

        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            String id = savedInstanceState.getString("ID");
            book = realm.where(Book.class).equalTo("id", id).findFirst();

            textViewBookName.setText(book.getTitle());
            textViewBookDescription.setText(book.getDescription());
            textViewAddedOn.setText(MainActivity.SIMPLE_DATE_FORMAT.format(book.getAddedOn()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_view_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete:
                showConfirmationDialog();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showConfirmationDialog() {
        //TODO
        new AlertDialog.Builder(BookViewActivity.this)
                .setTitle("Alert")
                .setMessage("Do you want to delete ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setAction(MainActivity.ACTION_DELETE_BOOK);

                    intent.putExtra("ID", book.getId());
                    setResult(RESULT_OK, intent);
                    finish();
                }).show();

    }

}
