package ru.verso.picturesnap.presentation.activity.states;

public interface ClientActivityState {

    int getStartFragmentId();

    void createBottomNavigationMenu();

    void createLeftMenu();
}
