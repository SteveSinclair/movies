package com.example.android.movies.utilities;

import android.content.Context;

import com.example.android.movies.data.MoviesRepository;
import com.example.android.movies.data.database.MoviesDatabase;
import com.example.android.movies.data.network.MoviesNetworkDataSource;
import com.example.android.movies.ui.detail.DetailsViewModelFactory;
import com.example.android.movies.ui.list.MoviesViewModelFactory;

public class InjectorUtils {

    public static MoviesRepository provideRepository(Context context) {
        MoviesDatabase database = MoviesDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MoviesNetworkDataSource networkDataSource = MoviesNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return MoviesRepository.getInstance(database.movieDao(), networkDataSource, executors);
    }

    public static MoviesNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return MoviesNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static MoviesViewModelFactory provideMoviesViewModelFactory(Context context) {
        MoviesRepository repository = provideRepository(context.getApplicationContext());
        return new MoviesViewModelFactory(repository);
    }

    public static DetailsViewModelFactory provideDetailsViewModelFactory(Context context, int movieId) {
        MoviesRepository repository = provideRepository(context.getApplicationContext());
        return new DetailsViewModelFactory(repository, movieId);
    }
}
