package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.verso.picturesnap.databinding.LayoutRecordToPhotographerBinding;
import ru.verso.picturesnap.domain.models.Record;

public class RecordsAdapter extends ListAdapter<Record, RecordViewHolder> {


    public RecordsAdapter(@NonNull DiffUtil.ItemCallback<Record> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutRecordToPhotographerBinding binding = LayoutRecordToPhotographerBinding.inflate(inflater, parent, false);

        return new RecordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class RecordsDiff extends DiffUtil.ItemCallback<Record> {

        @Override
        public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem.equals(newItem);
        }
    }
}
