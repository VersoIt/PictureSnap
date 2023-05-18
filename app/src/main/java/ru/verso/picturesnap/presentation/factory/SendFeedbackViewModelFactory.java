package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.SendFeedbackUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.SendFeedbackViewModel;

public class SendFeedbackViewModelFactory implements ViewModelProvider.Factory {

    private final SendFeedbackUseCase sendFeedbackUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetClientDataUseCase getClientDataUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    public SendFeedbackViewModelFactory(SendFeedbackUseCase sendFeedbackUseCase, GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase, GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.sendFeedbackUseCase = sendFeedbackUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getClientDataUseCase = getClientDataUseCase;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SendFeedbackViewModel(sendFeedbackUseCase, getUserDataUseCase, getClientDataUseCase, getPhotographerDataUseCase);
    }
}
