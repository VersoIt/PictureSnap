package ru.verso.picturesnap.presentation.adapters.client;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.Record;

public class RecordsFromClientAdapter extends ListAdapter<RecordsFromClientAdapter.RecordBundle, RecordViewHolder> {

    public RecordsFromClientAdapter(@NonNull DiffUtil.ItemCallback<RecordBundle> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new RecordViewHolder(inflater.inflate(R.layout.layout_record_to_photographer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class RecordsDiff extends DiffUtil.ItemCallback<RecordBundle> {

        @Override
        public boolean areItemsTheSame(@NonNull RecordBundle oldItem, @NonNull RecordBundle newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecordBundle oldItem, @NonNull RecordBundle newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class RecordBundle {

        private final PhotographerPresentationService photographerPresentationService;

        private final Record record;

        private final Photographer photographer;

        public RecordBundle(Record record, Photographer photographer, PhotographerPresentationService service) {
            this.record = record;
            this.photographer = photographer;
            photographerPresentationService = service;
        }

        public PhotographerPresentationService getPhotographerPresentationService() {
            return photographerPresentationService;
        }

        public Record getRecord() {
            return record;
        }

        public Photographer getPhotographer() {
            return photographer;
        }

        @Override
        public int hashCode() {
            return Objects.hash(record, photographer);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;

            if (other instanceof RecordBundle) {
                RecordBundle bundle = (RecordBundle) other;
                return bundle.photographer.equals(photographer) &&
                        bundle.record.equals(record);
            }

            return false;
        }
    }
}
