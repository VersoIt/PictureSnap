package ru.verso.picturesnap.presentation.adapters.client;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.LayoutFeedbackFromClientBinding;
import ru.verso.picturesnap.domain.models.Feedback;

public class FeedbackViewHolder extends RecyclerView.ViewHolder {

    private final LayoutFeedbackFromClientBinding binding;

    public FeedbackViewHolder(LayoutFeedbackFromClientBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull Feedback feedback) {
        binding.textViewOwnerName.setText(feedback.getOwnerName());

        binding.textViewText.setText(feedback.getText());
        binding.textViewDate.setText(feedback.getDate());

        createRatingStars(feedback.getRating(), binding.linearLayoutStars);
    }

    private void createRatingStars(int rating, ViewGroup container) {
        final int valueInDp = 17;
        int valueInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, container.getContext().getResources().getDisplayMetrics());

        for (int cntNum = 0; cntNum < rating; ++cntNum) {
            View view = new View(container.getContext());

            view.setLayoutParams(new LinearLayout.LayoutParams(valueInPx, valueInPx));
            view.setBackground(ContextCompat.getDrawable(container.getContext(), R.drawable.ic_star_yellow));

            container.addView(view);
        }
    }
}
