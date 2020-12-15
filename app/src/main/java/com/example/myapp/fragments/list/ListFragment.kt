package com.example.myapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.data.ExerciseDatabase
import com.example.myapp.viewmodel.ExerciseViewModel
import com.example.myapp.viewmodel.ExerciseViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var mExerciseViewModelFactory: ExerciseViewModelFactory
    private lateinit var mExerciseViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ExerciseDatabase.getDatabase(application).exerciseDao()

        mExerciseViewModelFactory = ExerciseViewModelFactory(dataSource, application)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mExerciseViewModel = ViewModelProviders.of(this, mExerciseViewModelFactory).get(ExerciseViewModel::class.java)

        mExerciseViewModel.readAllData.observe(viewLifecycleOwner, Observer { exercise ->
            adapter.setData(exercise)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllExercise()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllExercise() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mExerciseViewModel.deleteAllExercise()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}