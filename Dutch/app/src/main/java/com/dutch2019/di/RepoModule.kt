package com.dutch2019.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.dutch2019.db.RecentLocationDB
import com.dutch2019.network.TMapService
import com.dutch2019.repository.DBRepository
import com.dutch2019.repository.TMapRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    @ViewModelScoped
    fun provideTMapRepository(api : TMapService): TMapRepository = TMapRepository(api)

    @Provides
    @ViewModelScoped
    fun provideDBRepository(db : RecentLocationDB) : DBRepository = DBRepository(db)
}