package com.example.kolsatest.data.mapper

import com.example.kolsatest.data.remote.model.ApiWorkout
import com.example.kolsatest.domain.model.Workout
import com.example.kolsatest.domain.model.WorkoutType
import javax.inject.Inject

class WorkoutsMapper @Inject constructor() {

    fun fromApiToModel(api: ApiWorkout): Workout {
        return Workout(
            id = api.id,
            title = api.title.orEmpty(),
            description = api.description.orEmpty(),
            type = WorkoutType.fromInt(api.type),
            duration = api.duration.orEmpty(),
        )
    }
}
