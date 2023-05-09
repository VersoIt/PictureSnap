package ru.verso.picturesnap.presentation.fragments.photographer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.databinding.FragmentPhotographerPortfolioFromClientBinding;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class PhotographerPortfolioFromClient extends Fragment {

    private FragmentPhotographerPortfolioFromClientBinding binding;

    private LiveData<List<PhotographerPresentationService>> photographerServices;

    private LiveData<List<PhotographerPresentationService>> photographerServiceProvisions;

    private static int synchronizeCount = 0;
    private static final int SYNCHRONIZE_END_AMOUNT = 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPhotographerPortfolioFromClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ServicesViewModel servicesViewModel = getPhotographerServicesViewModel();

        photographerServices = servicesViewModel.getPhotographerServices();
        photographerServiceProvisions = servicesViewModel.getPhotographerServices();

        photographerServices.observe(getViewLifecycleOwner(), services ->
                synchronizeLives());

        photographerServiceProvisions.observe(getViewLifecycleOwner(), services ->
                synchronizeLives());
    }

    @SuppressLint("DiscouragedApi")
    private void synchronizeLives() {
        ++synchronizeCount;

        if (synchronizeCount >= SYNCHRONIZE_END_AMOUNT) {
            ViewPager2 viewPager = binding.viewPagerPortfolio;
            viewPager.setAdapter(new PortfolioAdapter(this.requireActivity()));
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
            return new PortfolioImagesFragment(Objects.requireNonNull(photographerServiceProvisions.getValue()).get(position).getId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        synchronizeCount = 0;
    }
}