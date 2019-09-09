package com.transo.realmexample;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class BookViewHolder extends RecyclerView.ViewHolder {

    TextView textViewBookName, textViewBookDescription, textViewAddedOn;
    ImageView imageViewBookView, imageViewBookEdit, imageViewBookDelete;

    public BookViewHolder(View view) {
        super(view);
        textViewBookName = view.findViewById(R.id.textViewBookName);
        textViewBookDescription = view.findViewById(R.id.textViewBookDescription);
        textViewAddedOn = view.findViewById(R.id.textViewAddedOn);

        imageViewBookView = view.findViewById(R.id.imageViewActionView);
        imageViewBookEdit = view.findViewById(R.id.imageViewActionEdit);
        imageViewBookDelete = view.findViewById(R.id.imageViewActionDelete);
    }
}
