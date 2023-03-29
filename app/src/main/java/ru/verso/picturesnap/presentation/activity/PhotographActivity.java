package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.verso.picturesnap.databinding.ActivityPhotographBinding;

public class PhotographActivity extends AppCompatActivity {

    private ActivityPhotographBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotographBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
