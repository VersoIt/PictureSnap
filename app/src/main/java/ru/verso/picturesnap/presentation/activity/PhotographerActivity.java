package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.databinding.ActivityPhotographerBinding;

public class PhotographerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ru.verso.picturesnap.databinding.ActivityPhotographerBinding binding = ActivityPhotographerBinding.inflate(getLayoutInflater());

        binding.tet.setText(FirebaseAuth.getInstance().getUid());
        setContentView(binding.getRoot());
    }
}
