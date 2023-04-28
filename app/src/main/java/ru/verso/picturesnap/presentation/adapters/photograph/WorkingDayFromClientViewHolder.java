package ru.verso.picturesnap.presentation.adapters.photograph;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutWorkingDayFromClientBinding;
import ru.verso.picturesnap.domain.models.WorkingDay;

public class WorkingDayFromClientViewHolder extends RecyclerView.ViewHolder {

    private final LayoutWorkingDayFromClientBinding binding;

    private final Context context;

    public enum Days {
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);

        private final int value;

        Days(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public WorkingDayFromClientViewHolder(Context context, LayoutWorkingDayFromClientBinding binding) {
        super(binding.getRoot());

        this.context = context;
        this.binding = binding;
    }

    public void bind(WorkingDay workingDay) {
        binding.textViewDayName.setText(convertDateIdToDay(workingDay.getDayId()));

        String hourStart = String.format(Locale.getDefault(), "%02d", workingDay.getHourStart());
        String hourEnd = String.format(Locale.getDefault(), "%02d", workingDay.getHourEnd());
        String minuteStart = String.format(Locale.getDefault(), "%02d", workingDay.getMinuteStart());
        String minuteEnd = String.format(Locale.getDefault(), "%02d", workingDay.getMinuteEnd());

        binding.timeStart.setText(String.format("%s:%s", hourStart, minuteStart));
        binding.timeEnd.setText(String.format("%s:%s", hourEnd, minuteEnd));
    }

    private String convertDateIdToDay(int id) {

        int[] dayResources = {
                R.string.monday,
                R.string.tuesday,
                R.string.wednesday,
                R.string.thursday,
                R.string.friday,
                R.string.saturday,
                R.string.sunday
        };

        return context.getResources().getString(dayResources[id]);
    }
}
