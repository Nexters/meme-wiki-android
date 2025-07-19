package com.mimu_bird.data.datasource.sample

import com.mimu_bird.network.api.SampleService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDatasource @Inject constructor(
    private val sampleService: SampleService
){
}