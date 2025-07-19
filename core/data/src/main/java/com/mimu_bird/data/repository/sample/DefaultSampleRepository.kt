package com.mimu_bird.data.repository.sample

import com.mimu_bird.data.datasource.sample.SampleDatasource
import com.mimu_bird.domain.repository.sample.SampleRepository
import javax.inject.Inject

class DefaultSampleRepository @Inject constructor(
    private val sampleDatasource: SampleDatasource
) : SampleRepository {
}