package kz.kolesateam.confapp.favorite_events.di

import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.favorite_events.data.DefaultFavoriteEventsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val favoriteEventsModule: Module = module {

    single{
        DefaultFavoriteEventsRepository() as UpcomingEventsRepository
    }
}