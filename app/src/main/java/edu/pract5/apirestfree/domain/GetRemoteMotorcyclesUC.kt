package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle

/**
 * Class GetRemoteMotorcyclesUseCase.kt
 * Gets the list of motorcycles from the remote API.
 * @author Víctor Lamas
 *
 * @param motorcyclesRepository The repository to get the motorcycles.
 */
class GetRemoteMotorcyclesUC(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of API motorcycles.
     *
     * @return Cold flow list of motorcycles.
     */
    /*suspend operator fun invoke(model: String? = null): List<Motorcycle> {
        return if (!model.isNullOrEmpty()) {
            motorcyclesRepository.getRemoteMotorcyclesByMakeOrModel(model)
        } else {
            motorcyclesRepository.getRemoteMotorcyclesByMakeOrModel(" ")
        }
    }*/
    suspend operator fun invoke(): List<Motorcycle> {
        return motorcyclesRepository.getRemoteMotorcycles()
    }
}