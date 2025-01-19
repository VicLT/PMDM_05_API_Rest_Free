package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import kotlinx.coroutines.flow.Flow

class GetSortedFavMotorcyclesUseCase(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow composed of a list of favourite motorcycles.
     */
    operator fun invoke(filter: MotorcyclesFilter): Flow<List<Motorcycle>> {
        return when (filter) {
            MotorcyclesFilter.ALPHA_ASC -> localDataSource.getFavMotorcyclesAsc()
            MotorcyclesFilter.ALPHA_DESC -> localDataSource.getFavMotorcyclesDesc()
        }
    }
}