package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository

class GetLocalMotorcyclesByModel(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    /**
     * Get the complete list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    /*operator fun invoke(model: String): List<Motorcycle> {
        return if !(model.isNullOrEmpty()) {
            motorcyclesRepository.getLocalMotorcyclesByModel(model)
        }
    }*/
}