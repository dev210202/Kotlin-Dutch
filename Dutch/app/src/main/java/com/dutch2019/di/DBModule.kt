package com.dutch2019.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import com.dutch2019.db.LocationDataDao
import com.dutch2019.db.RecentLocationDB

@Module
@InstallIn(ViewModelComponent::class)
object DBModule {

    @Provides
    @ViewModelScoped
    fun provideDataBase(@ApplicationContext context: Context): RecentLocationDB {
        return Room.databaseBuilder(
            context,
                RecentLocationDB::class.java, "recentlocation-db"
            ).build()
    }

    @Provides
    @ViewModelScoped
    fun provideLocationDataDao(database : RecentLocationDB) : LocationDataDao = database.locationDataDao()

}