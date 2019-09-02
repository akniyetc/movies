package com.silence.movies.ui.movies;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.silence.movies.App;
import com.silence.movies.MoviesActivity;
import com.silence.movies.R;
import com.silence.movies.domain.entity.Movie;
import com.silence.movies.presentation.movies.MoviesPresenter;
import com.silence.movies.presentation.movies.MoviesView;
import com.silence.movies.ui.base.BaseFragment;
import com.silence.movies.ui.base.SearchHistoryProvider;

import java.util.List;

import butterknife.BindView;

public class MoviesFragment extends BaseFragment implements MoviesView {

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

    @BindView(R.id.progressMovies)
    ProgressBar progressMovies;

    @BindView(R.id.contentMovies)
    FrameLayout contentMovies;

    private MoviesAdapter moviesAdapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_movies;
    }

    @InjectPresenter
    MoviesPresenter moviesPresenter;

    @ProvidePresenter
    MoviesPresenter provideMoviesPresenter() {
        return App.getInstance().moviesScreenComponent().getMoviesPresenter();
    }

    @Override
    protected void setupComponent() {
        App.getInstance().moviesScreenComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        moviesAdapter = new MoviesAdapter();
        rvMovies.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rvMovies.setAdapter(moviesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.movies, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getContext(), MoviesActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                searchView.setQuery(getItemAsString(searchView.getSuggestionsAdapter()), true);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                moviesPresenter.search(s);
                closeKeyboard();

                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getContext(),
                        SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
                suggestions.saveRecentQuery(s, null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void showMessage(String message) {
        showMessage(message, contentMovies);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        rvMovies.setVisibility(View.VISIBLE);
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void showLoading(Boolean show) {
        progressMovies.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private String getItemAsString(CursorAdapter adapter) {
        return (String) adapter.convertToString(adapter.getCursor());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        App.getInstance().closeMoviesComponent();
    }
}
