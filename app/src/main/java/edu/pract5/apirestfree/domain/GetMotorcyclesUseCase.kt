package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.Flow

/**
 * Class GetRemoteMotorcyclesUseCase.kt
 * Gets the list of motorcycles from the remote API.
 * @author Víctor Lamas
 *
 * @param motorcyclesRepository The repository to get the motorcycles.
 */
class GetMotorcyclesUseCase(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of API motorcycles.
     *
     * @return Cold flow list of motorcycles.
     */
    operator fun invoke(motorcycle: Motorcycle): Flow<List<Motorcycle>> {
        return motorcyclesRepository.getRemoteMotorcycles()
    }
}