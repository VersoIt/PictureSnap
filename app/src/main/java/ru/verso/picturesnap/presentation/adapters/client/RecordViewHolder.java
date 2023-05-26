package ru.verso.picturesnap.presentation.adapters.client;

import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.databinding.LayoutRecordToPhotographerBinding;
import ru.verso.picturesnap.domain.models.Record;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private final LayoutRecordToPhotographerBinding binding;

    public RecordViewHolder(LayoutRecordToPhotographerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Record record) {

    }
}
