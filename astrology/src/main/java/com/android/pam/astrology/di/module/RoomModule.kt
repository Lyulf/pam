package com.android.pam.astrology.di.module

import android.content.Context
import com.android.pam.astrology.data.dao.AstrologySettingsDao
import com.android.pam.astrology.data.database.AstrologySettingsRoomDatabase
import com.android.pam.astrology.data.repository.AstrologySettingsRepository
import com.android.pam.astrology.domain.repository.IAstrologySettingsRepository
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    fun provideAstrologySettingsRoomDatabase(
        ctx: Context
    ) : AstrologySettingsRoomDatabase = AstrologySettingsRoomDatabase.getDatabase(ctx)

    @Provides
    fun provideAstrologyDao(
        roomDatabase: AstrologySettingsRoomDatabase
    ) : AstrologySettingsDao = roomDatabase.astrologySettingsDao()

    @Provides
    fun provideIAstrologySettingsRepository(
        dao: AstrologySettingsDao
    ) : IAstrologySettingsRepository = AstrologySettingsRepository(dao)
}