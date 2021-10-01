package com.example.taskmanager.ui.taskScreen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.taskmanager.appComponent
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.util.toReadableString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

class TaskFragment : Fragment() {

    @Inject
    lateinit var factory: TaskViewModelFactory.Factory


    lateinit var taskViewModel:TaskViewModel

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.appComponent?.inject(this)
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        val taskId  = arguments?.getString("taskKey")
        taskViewModel  = factory.create(taskId!!).create(TaskViewModel::class.java)

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
        binding.tvDate.setOnClickListener {
            val calendar = getInstance()
            calendar.time = Date(taskViewModel.task!!.time)
            DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                calendar.set(YEAR, year)
                calendar.set(MONTH, month)
                calendar.set(DAY_OF_MONTH, dayOfMonth)
                TimePickerDialog(requireContext(), { _, hour, minute ->
                    calendar.set(HOUR_OF_DAY, hour)
                    calendar.set(MINUTE, minute)
                    taskViewModel.updateTime(calendar.time)
                    binding.tvDate.text = calendar.time.toReadableString()
                }, calendar.get(HOUR_OF_DAY), calendar.get(MINUTE), false).show()
            }, calendar.get(YEAR), calendar.get(MONTH), calendar.get(DAY_OF_MONTH)).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}