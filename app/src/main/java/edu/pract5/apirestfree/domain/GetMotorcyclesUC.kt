package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import kotlinx.coroutines.flow.Flow

class GetMotorcyclesUC(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    suspend operator fun invoke(filter: MotorcyclesFilter? = null): Flow<List<Motorcycle>> {
            motorcyclesRepository.getLocalMotorcycles().collect { localMotorcycles ->
                if (localMotorcycles.isEmpty()) {
                    motorcyclesRepository.getRemoteMotorcycles().collect { motorcycles ->
                        motorcycles.forEach { motorcycle ->
                            motorcyclesRepository.saveLocalMotorcycle(motorcycle)
                        }
                    }
                }
                if (filter == null) {
                    motorcyclesRepository.getLocalMotorcycles()
                } else {
                    motorcyclesRepository.getLocalMotorcyclesSortedByModel(filter)
                }
            }
    }
}