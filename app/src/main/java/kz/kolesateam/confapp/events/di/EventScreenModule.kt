package kz.kolesateam.confapp.events.di

import kz.kolesateam.confapp.common.data.model.RetrofitClient
import kz.kolesateam.confapp.events.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.data.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.viewModel.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventScreenModule = module {

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            userNameSharedPrefsDataSource = get()
        )
    }

    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get()
        ) as UpcomingEventsRepository
    }
}