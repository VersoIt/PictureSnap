package ru.verso.picturesnap.presentation.fragments.photographer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.databinding.FragmentPhotographerPortfolioFromPhotographerBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographerPortfolioFromPhotographer extends Fragment {
    private FragmentPhotographerPortfolioFromPhotographerBinding binding;

    private LiveData<List<PhotographerPresentationService>> photographerServices;

    private LiveData<List<PhotographerPresentationService>> photographerServiceProvisions;

    private ActivityResultLauncher<String> imagePickerLauncher;

    private static int synchronizeCount = 0;
    private static final int SYNCHRONIZE_END_AMOUNT = 2;

    private ServicesViewModel servicesViewModel;

    private String currentServiceProvisionId = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerPortfolioFromPhotographerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        servicesViewModel = getPhotographerServicesViewModel();
        ViewPager2 viewPager = binding.viewPagerPortfolio;

        photographerServices = servicesViewModel.getPhotographerServices();
        photographerServiceProvisions = servicesViewModel.getPhotographerServices();

        photographerServices.observe(getViewLifecycleOwner(), services ->
                synchronizeLives(servicesViewModel));

        photographerServiceProvisions.observe(getViewLifecycleOwner(), services ->
                synchronizeLives(servicesViewModel));

        registerForActivityResult();
    }
    private void registerForActivityResult() {

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null && currentServiceProvisionId != null && servicesViewModel != null) {
                        servicesViewModel.loadPicture(result, currentServiceProvisionId);
                    }
                }
        );
    }

    private void bindFloatingActionButtonAdd(ViewPager2 viewPager, ServicesViewModel servicesViewModel) {

        binding.floatingButtonAddNewPhoto.setOnClickListener(view -> {
            int position = viewPager.getCurrentItem();
            currentServiceProvisionId = Objects.requireNonNull(photographerServiceProvisions.getValue()).get(position).getId();
            imagePickerLauncher.launch("image/*");
        });
    }

    @SuppressLint("DiscouragedApi")
    private void synchronizeLives(ServicesViewModel servicesViewModel) {
        ++synchronizeCount;

        if (synchronizeCount >= SYNCHRONIZE_END_AMOUNT) {
            ViewPager2 viewPager = binding.viewPagerPortfolio;
            PortfolioAdapter adapter = new PortfolioAdapter(this.requireActivity());
            viewPager.setAdapter(adapter);
            bindFloatingActionButtonAdd(viewPager, servicesViewModel);
            TabLayout tabLayout = binding.tabLayoutTabs;

            Resources resources = binding.getRoot().getResources();
            String packageName = requireContext().getPackageName();

            new TabLayoutMediator(tabLayout,
                    viewPager,
                    (@NonNull TabLayout.Tab tab, int position) -> tab.setText(resources.getIdentifier(Objects
                            .requireNonNull(photographerServices.getValue())
                            .get(position)
                            .getName(), "string", packageName)))
                    .attach();
        }
    }

    private ServicesViewModel getPhotographerServicesViewModel() {

        return new ViewModelProvider(requireActivity())
                .get(ServicesViewModel.class);
    }

    private class PortfolioAdapter extends FragmentStateAdapter {

        public PortfolioAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public int getItemCount() {
            return Objects.requireNonNull(photographerServices.getValue()).size();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new PortfolioImagesFragment(Objects.requireNonNull(photographerServiceProvisions.getValue()).get(position).getId(), servicesViewModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        synchronizeCount = 0;
    }
}