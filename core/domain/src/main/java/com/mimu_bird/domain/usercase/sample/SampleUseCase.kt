package com.mimu_bird.domain.usercase.sample

import com.mimu_bird.domain.repository.sample.SampleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {
    suspend operator fun invoke() {

    }
}