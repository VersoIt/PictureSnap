package ru.verso.picturesnap.presentation.fragments.photograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.databinding.FragmentPhotographPortfolioBinding;

public class PhotographPortfolio extends Fragment {

    private FragmentPhotographPortfolioBinding binding;

    private final String[] data = {"first", "second", "third"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photograph_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager = binding.viewPagerPortfolio;
        viewPager.setAdapter(new PortfolioAdapter(this, new ArrayList<Fragment>() {
            {
                add(new Images());
            }
        }));
        TabLayout tabLayout = binding.tabLayoutTabs;
        new TabLayoutMediator(tabLayout,
                viewPager,
                (@NonNull TabLayout.Tab tab, int position) -> tab.setText(data[position])).attach();
    }

    class PortfolioAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragments;

        public PortfolioAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        public PortfolioAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
            super(fragment);
            this.fragments = fragments;
        }

        public PortfolioAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
            super(fragmentManager, lifecycle);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }
}