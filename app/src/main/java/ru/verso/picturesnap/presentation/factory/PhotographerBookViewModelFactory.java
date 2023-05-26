package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.BookPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.PhotographerBookViewModel;

public class PhotographerBookViewModelFactory implements ViewModelProvider.Factory {

    private final BookPhotographerUseCase bookPhotographerUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    public PhotographerBookViewModelFactory(BookPhotographerUseCase bookPhotographerUseCase, GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.bookPhotographerUseCase = bookPhotographerUseCase;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographerBookViewModel(bookPhotographerUseCase, getPhotographerDataUseCase);
    }
}
