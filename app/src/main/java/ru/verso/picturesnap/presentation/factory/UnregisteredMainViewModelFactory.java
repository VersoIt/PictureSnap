package ru.verso.picturesnap.presentation.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.UnregisteredMainViewModel;

public class UnregisteredMainViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    private final Application context;

    public UnregisteredMainViewModelFactory(Application context,
                                            GetPhotographerDataUseCase getPhotographerDataUseCase,
                                            GetUserDataUseCase getUserDataUseCase,
                                            UpdateUserDataUseCase updateUserDataUseCase) {
        this.context = context;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UnregisteredMainViewModel(context, getPhotographerDataUseCase, getUserDataUseCase, updateUserDataUseCase);
    }
}
