package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.ExerciseDao
import com.example.myapp.repository.ExerciseRepository
import com.example.myapp.model.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(val database: ExerciseDao, application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Exercise>>
    private val repository: ExerciseRepository

    init {
        repository = ExerciseRepository(database)
        readAllData = repository.readAllData
    }

    fun addExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
        }
    }

    fun updateExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExercise(exercise)
        }
    }

    fun deleteAllExercise(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllExercise()
        }
    }

}