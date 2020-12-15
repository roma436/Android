package com.example.myapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.data.ExerciseDatabase
import com.example.myapp.model.Exercise
import com.example.myapp.viewmodel.ExerciseViewModel
import com.example.myapp.viewmodel.ExerciseViewModelFactory
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mExerciseViewModelFactory: ExerciseViewModelFactory
    private lateinit var mExerciseViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ExerciseDatabase.getDatabase(application).exerciseDao()

        mExerciseViewModelFactory = ExerciseViewModelFactory(dataSource, application)
        mExerciseViewModel = ViewModelProviders.of(this, mExerciseViewModelFactory).get(ExerciseViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val exercise = addExercise_et.text.toString()
        val status = addStatus_et.text.toString()

        if(inputCheck(exercise, status)){
            // Create User Object
            val exerciseL = Exercise(
                0,
                exercise,
                status
            )
            // Add Data to Database
            mExerciseViewModel.addExercise(exerciseL)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(exercise: String, status: String): Boolean{
        return !(TextUtils.isEmpty(exercise) && TextUtils.isEmpty(status))
    }

}