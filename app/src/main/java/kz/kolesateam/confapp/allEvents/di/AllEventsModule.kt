package kz.kolesateam.confapp.allEvents.di

import kz.kolesateam.confapp.allEvents.data.AllEventsDataSource
import kz.kolesateam.confapp.allEvents.data.DefaultAllEventsRepository
import kz.kolesateam.confapp.allEvents.domain.AllEventsRepository
import kz.kolesateam.confapp.allEvents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.common.data.model.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allEventsModule = module {

    viewModel {
        AllEventsViewModel(
            allEventsRepository = get()
        )
    }

    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory {
        DefaultAllEventsRepository(
            allEventsDataSource = get()
        ) as AllEventsRepository
    }
}