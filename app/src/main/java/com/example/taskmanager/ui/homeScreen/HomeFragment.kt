package com.example.taskmanager.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.model.Task
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val homeViewModel: HomeViewModel by viewModels()

        binding.homeViewModel = homeViewModel

        binding.lifecycleOwner = this.viewLifecycleOwner

        val manager = LinearLayoutManager(activity)
        binding.rvTasks.layoutManager = manager

        val adapter = TaskAdapter(TaskListener({ task ->
            task?.let { homeViewModel.completeTask(task) }
        }, fun(task: Task): Boolean {
            homeViewModel.openTask( task.id)
            return true
        }))
        binding.rvTasks.adapter = adapter

        homeViewModel.tasks.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        homeViewModel.navigateToTaskDetail.observe(viewLifecycleOwner, { id ->
            id?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeToTask(id)
                )
                homeViewModel.doneNavigating()
            }
        })

        homeViewModel.closeKeyboard.observe(viewLifecycleOwner,{close ->
            if(close){
                hideKeyboard()
                homeViewModel.keyboardClosed()
            }
        })

        homeViewModel.showProgress.observe(viewLifecycleOwner,{show->
            binding.progressBar.visibility = when(show){
                true -> View.VISIBLE
                false -> View.GONE
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}