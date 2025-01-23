package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import kotlinx.coroutines.flow.Flow

/**
 * Class GetSortedFavMotorcyclesUseCase.kt
 * Gets the list of favourite motorcycles from the local DB sorted by a filter.
 * @author Víctor Lamas
 *
 * @param motorcyclesRepository The repository to get the motorcycles.
 */
class GetLocalMotorcyclesUC(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    operator fun invoke(filter: MotorcyclesFilter? = null): Flow<List<Motorcycle>> {
        return if (filter == null) {
            motorcyclesRepository.getLocalMotorcycles()
        } else {
            motorcyclesRepository.getLocalMotorcyclesSortedByModel(filter)
        }
    }
}