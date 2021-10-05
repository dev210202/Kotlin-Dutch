package jkey20.dutch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jkey20.dutch.repository.SafeCasterRepository
import jkey20.dutch.repository.TMapRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideTMapRepository(): TMapRepository = TMapRepository()

    @Provides
    @Singleton
    fun provideSafeCasterRepository(): SafeCasterRepository = SafeCasterRepository()
}