package kz.kolesateam.confapp.eventDetails.di

import kz.kolesateam.confapp.eventDetails.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.eventDetails.data.DefaultImageLoader
import kz.kolesateam.confapp.eventDetails.data.EventDetailsDataSource
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.eventDetails.domain.ImageLoader
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.eventDetails.presentation.viewModel.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val eventDetailsModule = module {

    viewModel {
        EventDetailsViewModel(
            eventDetailsRepository = get(),
            favoriteEventsRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventDetailsDataSource::class.java)
    }

    factory {
        EventDetailsRouter()
    }

    factory {
        DefaultEventDetailsRepository(
            eventDetailsDataSource = get(),
            eventApiDataMapper = get()
        ) as EventDetailsRepository
    }

    factory {
        DefaultImageLoader() as ImageLoader
    }
}