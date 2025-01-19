package edu.pract5.apirestfree.domain

import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle

class GetRemoteMotorcyclesUseCase(
    private val motorcyclesRepository: MotorcyclesRepository
) {
    operator fun invoke(motorcycle: Motorcycle) {
        motorcyclesRepository.getRemoteMotorcycles()
    }
}