package ru.verso.picturesnap.presentation.adapters.photographer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.Record;

public class RecordsFromClientAdapter extends ListAdapter<RecordsFromClientAdapter.RecordBundle, RecordViewHolder> {

    private final RecordViewHolder.RecordStatusChanger statusChanger;

    public RecordsFromClientAdapter(@NonNull DiffUtil.ItemCallback<RecordBundle> diffCallback, RecordViewHolder.RecordStatusChanger statusChanger) {
        super(diffCallback);
        this.statusChanger = statusChanger;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new RecordViewHolder(inflater.inflate(R.layout.layout_client_record, parent, false), statusChanger);
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

        private final Client client;

        public RecordBundle(Record record, Client client, PhotographerPresentationService service) {
            this.record = record;
            this.client = client;
            photographerPresentationService = service;
        }

        public PhotographerPresentationService getPhotographerPresentationService() {
            return photographerPresentationService;
        }

        public Record getRecord() {
            return record;
        }

        public Client getClient() {
            return client;
        }

        @Override
        public int hashCode() {
            return Objects.hash(record, client);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;

            if (other instanceof RecordBundle) {
                RecordBundle bundle = (RecordBundle) other;
                return bundle.client.equals(client) &&
                        bundle.record.equals(record);
            }

            return false;
        }
    }
}
