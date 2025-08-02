package com.mimu_bird.data.di

import com.mimu_bird.data.repository.search.DefaultSearchRepository
import com.mimu_bird.domain.repository.search.SearchRepository
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
    fun bindSearchRepository(
        searchRepository: DefaultSearchRepository
    ): SearchRepository
}