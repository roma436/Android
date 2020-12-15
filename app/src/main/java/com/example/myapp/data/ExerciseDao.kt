package com.example.myapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapp.model.Exercise

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addExercise(exercise: Exercise)

    @Update
    fun updateExercise(exercise: Exercise)

    @Delete
    fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercise_table")
    fun deleteAllExercise()

    @Query("SELECT * FROM exercise_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Exercise>>

}