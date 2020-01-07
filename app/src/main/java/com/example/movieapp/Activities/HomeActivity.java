package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.movieapp.Fragments.CinemaFragment;
import com.example.movieapp.Fragments.FavoriteFragment;
import com.example.movieapp.Fragments.HomeFragment;
import com.example.movieapp.Fragments.ProfileFragment;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MenuInit();

        FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
        frag_trans.add(R.id.fragment_container,new HomeFragment());
        frag_trans.commit();

    }

    private void MenuInit() {
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
                        frag_trans.addToBackStack(null);
                        frag_trans.replace(R.id.fragment_container,new HomeFragment());
                        frag_trans.commit();
                        return true;
                    case R.id.navigation_favorite:
                        frag_trans = getSupportFragmentManager().beginTransaction();
                        frag_trans.addToBackStack(null);
                        frag_trans.replace(R.id.fragment_container,new FavoriteFragment());
                        frag_trans.commit();
                        return true;
                    case R.id.navigation_cinema:
                        frag_trans = getSupportFragmentManager().beginTransaction();
                        frag_trans.addToBackStack(null);
                        frag_trans.replace(R.id.fragment_container,new CinemaFragment());
                        frag_trans.commit();
                        return true;
                    case R.id.navigation_profile:
                        frag_trans = getSupportFragmentManager().beginTransaction();
                        frag_trans.addToBackStack(null);
                        frag_trans.replace(R.id.fragment_container,new ProfileFragment());
                        frag_trans.commit();
                        return true;
                }
                return false;
            }
        });
    }

    public abstract static class PaginationScrollListener extends RecyclerView.OnScrollListener {

        LinearLayoutManager layoutManager;

        public PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading() && !isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= getTotalPageCount()) {
                    loadMoreItems();
                }
            }

        }

        protected abstract void loadMoreItems();

        protected abstract int getTotalPageCount();

        protected abstract boolean isLastPage();

        protected abstract boolean isLoading();
    }
}
