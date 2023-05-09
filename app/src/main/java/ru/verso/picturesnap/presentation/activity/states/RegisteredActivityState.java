package ru.verso.picturesnap.presentation.activity.states;

import ru.verso.picturesnap.R;

public class RegisteredActivityState implements ClientActivityState {


    @Override
    public int getStartFragmentId() {
        return R.id.client_main;
    }

    @Override
    public void createBottomNavigationMenu() {

    }

    @Override
    public void createLeftMenu() {

    }
}
