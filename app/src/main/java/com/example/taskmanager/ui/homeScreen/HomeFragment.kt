package com.example.taskmanager.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.common.Resource
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
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
            homeViewModel.openTask(task.id)
            return true
        }))
        binding.rvTasks.adapter = adapter

        homeViewModel.tasks.onEach {
                adapter.submitList(it)
        }.launchIn(lifecycleScope)

        homeViewModel.navigateToTaskDetail.onEach { id->
            id?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeToTask(id)
                )
                homeViewModel.doneNavigating()
            }

        }.launchIn(lifecycleScope)

        homeViewModel.closeKeyboard.onEach { close ->
            if (close) {
                hideKeyboard()
                homeViewModel.keyboardClosed()
            }
        }.launchIn(lifecycleScope)

        homeViewModel.weather.onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    result.message?.let { Timber.e(it) }
                }
            }
        }.launchIn(lifecycleScope)

        return binding.root
    }
}