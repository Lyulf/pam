package com.android.pam.astrology.di.module

import android.content.Context
import com.android.pam.astrology.data.dao.AstrologySettingsDao
import com.android.pam.astrology.data.database.AstrologySettingsRoomDatabase
import com.android.pam.astrology.data.repository.AstrologySettingsRepository
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideAstrologySettingsRoomDatabase(
        ctx: Context
    ) : AstrologySettingsRoomDatabase = AstrologySettingsRoomDatabase.getDatabase(ctx)

    @Singleton
    @Provides
    fun provideAstrologyDao(
        roomDatabase: AstrologySettingsRoomDatabase
    ) : AstrologySettingsDao = roomDatabase.astrologySettingsDao()

    @Singleton
    @Provides
    fun provideIAstrologySettingsRepository(
        dao: AstrologySettingsDao
    ) : IAstrologySettingsRepository = AstrologySettingsRepository(dao)
}