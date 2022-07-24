package com.example.feature_lists.domain.usecases

import com.example.feature_lists.domain.repository.AppResourcesRepository
import javax.inject.Inject

class GetAppImageUseCase @Inject constructor(
    val appResourcesRepository: AppResourcesRepository
) {
    operator fun invoke() = appResourcesRepository.getEkkoImageResource()
}