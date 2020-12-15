package com.example.myapp.repository

import androidx.lifecycle.LiveData
import com.example.myapp.data.ExerciseDao
import com.example.myapp.model.Exercise

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val readAllData: LiveData<List<Exercise>> = exerciseDao.readAllData()

    fun addExercise(exercise: Exercise){
        exerciseDao.addExercise(exercise)
    }

    fun updateExercise(exercise: Exercise){
        exerciseDao.updateExercise(exercise)
    }

    fun deleteExercise(exercise: Exercise){
        exerciseDao.deleteExercise(exercise)
    }

    fun deleteAllExercise(){
        exerciseDao.deleteAllExercise()
    }

}