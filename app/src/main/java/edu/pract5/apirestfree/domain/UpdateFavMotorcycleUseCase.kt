package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle

/**
 * Class UpdateFavMotorcycleUseCase.kt
 * Inserts or deletes a favourite motorcycle in the local DB.
 * @author Víctor Lamas
 *
 * @param motorcyclesRepository The repository to get the motorcycles.
 */
class UpdateFavMotorcycleUseCase(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Inserts or deletes a favourite motorcycle in the local DB.
     *
     * @param favMotorcycle Motorcycle marked as favourite.
     */
    suspend operator fun invoke(favMotorcycle: Motorcycle) {
        if (favMotorcycle.favourite) {
            motorcyclesRepository.saveFavMotorcycle(favMotorcycle)
        } else {
            motorcyclesRepository.deleteFavMotorcycle(favMotorcycle)
        }
    }
}