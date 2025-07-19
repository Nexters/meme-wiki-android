package com.mimu_bird.data.di

import com.mimu_bird.data.repository.sample.DefaultSampleRepository
import com.mimu_bird.domain.repository.sample.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindSampleRepository(
        sampleRepository: DefaultSampleRepository
    ): SampleRepository
}