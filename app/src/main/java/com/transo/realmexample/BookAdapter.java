package com.transo.realmexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.transo.realmexample.model.Book;

import io.realm.RealmResults;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private RealmResults<Book> books;
    private Book.OnViewClickListner viewClickListner;
    private Book.OnEditClickListner editClickListner;
    private Book.OnDeleteClickListner deleteClickListner;


    public BookAdapter(RealmResults<Book> books, Book.OnViewClickListner viewClickListner, Book.OnEditClickListner editClickListner, Book.OnDeleteClickListner deleteClickListner) {
        this.books = books;
        this.viewClickListner = viewClickListner;
        this.editClickListner = editClickListner;
        this.deleteClickListner = deleteClickListner;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_row, parent, false);

        final BookViewHolder myViewHolder = new BookViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Book book = books.get(position);
        holder.textViewBookName.setText(book.getTitle());
        holder.textViewBookDescription.setText(book.getDescription());
        holder.textViewAddedOn.setText(MainActivity.SIMPLE_DATE_FORMAT.format(book.getAddedOn()));

        holder.imageViewBookView.setOnClickListener(view -> viewClickListner.onClick(view, position, books.get(position)));

        holder.imageViewBookEdit.setOnClickListener(view -> editClickListner.onClick(view, position, books.get(position)));

        holder.imageViewBookDelete.setOnClickListener(view -> deleteClickListner.onClick(view, position, books.get(position)));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

}
