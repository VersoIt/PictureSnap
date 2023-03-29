package ru.verso.picturesnap.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.verso.picturesnap.databinding.ActivityClientBinding;

public class ClientActivity extends AppCompatActivity {

    private ActivityClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}
