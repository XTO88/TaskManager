package com.example.taskmanager.ui.taskScreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.view.DateTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )
        binding.taskViewModel = taskViewModel

        taskViewModel.navigateToHome.onEach { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                    TaskFragmentDirections.actionTaskToHome()
                )
                taskViewModel.doneNavigating()
            }

        }.launchIn(lifecycleScope)

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etTask.doAfterTextChanged {
            taskViewModel.updateText(it.toString())
        }
        binding.tvDate.listener = DateTextView.DateListener{ date ->
            taskViewModel.updateTime(date)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}