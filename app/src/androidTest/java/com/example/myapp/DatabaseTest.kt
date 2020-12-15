package com.example.myapp

import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapp.data.ExerciseDao
import com.example.myapp.data.ExerciseDatabase
import com.example.myapp.model.Exercise
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var db: ExerciseDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ExerciseDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        exerciseDao = db.exerciseDao()
    }
    //
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    //
    @Test
    @Throws(Exception::class)
     fun insertUser() {
        val item = Exercise(0, "\n" +
                "Arnold press", "In progress")
        exerciseDao.addExercise(item)
        val user = exerciseDao.readAllData()
        assertNotNull(user)
    }
}