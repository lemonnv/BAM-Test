package com.vincent.bamtest

import androidx.room.Room
import com.vincent.bamtest.data.RepoRepositoryImpl
import com.vincent.bamtest.data.db.room.AppDatabase
import com.vincent.bamtest.data.github.GithubService
import com.vincent.bamtest.domain.repository.RepoRepository
import com.vincent.bamtest.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val data = module {
    single<Retrofit> { Retrofit.Builder()
        .baseUrl("https://api.github.com/orgs/bamlab/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
            .build()
        )
        .build()
    }
    factory { get<Retrofit>().create(GithubService::class.java) }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "db").fallbackToDestructiveMigration().build() }

    factory<RepoRepository> { RepoRepositoryImpl(get(), get<AppDatabase>().repositoryDao()) }

}

val viewModels = module {
    viewModel<MainViewModel> { MainViewModel() }
}