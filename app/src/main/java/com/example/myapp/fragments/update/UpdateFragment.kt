package com.example.myapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.data.ExerciseDatabase
import com.example.myapp.model.Exercise
import com.example.myapp.viewmodel.ExerciseViewModel
import com.example.myapp.viewmodel.ExerciseViewModelFactory
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mExerciseViewModelFactory: ExerciseViewModelFactory
    private lateinit var mExerciseViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ExerciseDatabase.getDatabase(application).exerciseDao()

        mExerciseViewModelFactory = ExerciseViewModelFactory(dataSource, application)
        mExerciseViewModel = ViewModelProviders.of(this, mExerciseViewModelFactory).get(ExerciseViewModel::class.java)

        view.updateExercise_et.setText(args.currentExercise.exercise)
        view.updateStatus_et.setText(args.currentExercise.status)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val exercise = updateExercise_et.text.toString()
        val status = updateStatus_et.text.toString()

        if (inputCheck(exercise, status)) {
            // Create User Object
            val updatedExercise = Exercise(args.currentExercise.id, exercise, status)
            // Update Current User
            mExerciseViewModel.updateExercise(updatedExercise)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(exercise: String, status: String): Boolean {
        return !(TextUtils.isEmpty(exercise) && TextUtils.isEmpty(status))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteExercise()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteExercise() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mExerciseViewModel.deleteExercise(args.currentExercise)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentExercise.exercise}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentExercise.exercise}?")
        builder.setMessage("Are you sure you want to delete ${args.currentExercise.exercise}?")
        builder.create().show()
    }
}