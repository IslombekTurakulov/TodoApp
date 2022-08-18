package com.iuturakulov.todoapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.transition.MaterialContainerTransform
import com.iuturakulov.todoapp.R
import com.iuturakulov.todoapp.data.dao.TaskPriorities
import com.iuturakulov.todoapp.data.dao.TaskPriorities.Companion.toPriority
import com.iuturakulov.todoapp.databinding.FragmentAddTaskBinding
import com.iuturakulov.todoapp.extensions.showErrorResId
import com.iuturakulov.todoapp.extensions.showSnackbar
import com.iuturakulov.todoapp.ui.viewmodel.add.AddState
import com.iuturakulov.todoapp.ui.viewmodel.add.AddViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupViews()
        setupObservers()
        setupAnimationLayout()
    }

    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            if (viewModel.addState.value != AddState.Loading) {
                viewModel.add(
                    title = binding.titleEditText.text.toString(),
                    description = binding.descriptionEditText.text.toString(),
                    category = binding.textField.text.toString().toPriority(requireContext())
                )
            }
        }
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.dateButton.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setupObservers() {
        viewModel.addState.observe(viewLifecycleOwner, ::handleAddState)
    }

    private fun handleAddState(state: AddState) {
        when (state) {
            AddState.Initial -> binding.addIndicator.isGone = true
            AddState.Loading -> binding.addIndicator.isVisible = true
            is AddState.Success -> {
                binding.addIndicator.isGone = true
                findNavController().popBackStack(R.id.explore_fragment, false)
            }
            AddState.Error -> {
                binding.addIndicator.isGone = true
                view?.showSnackbar(R.string.add_fragment_error_db)
            }
            is AddState.InputError -> {
                binding.addIndicator.isGone = true
                handleAddInputError(state)
            }
        }
    }

    private fun handleAddInputError(error: AddState.InputError) {
        when (error) {
            AddState.InputError.Title.Empty -> binding.titleInputLayout.showErrorResId(R.string.add_fragment_error_title_empty)
            AddState.InputError.Title.NotUnique -> binding.titleInputLayout.showErrorResId(R.string.add_fragment_error_title_not_unique)
            AddState.InputError.Description -> binding.descriptionInputLayout.showErrorResId(R.string.add_fragment_error_description)
            AddState.InputError.Date -> binding.root.showSnackbar(R.string.add_fragment_error_invalid_date)
        }
    }

    private fun setupViews() {
        val items = listOf(
            getString(TaskPriorities.HIGH.value),
            getString(TaskPriorities.LOW.value),
            getString(TaskPriorities.EMPTY.value),
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
        binding.textField.setText(getString(TaskPriorities.EMPTY.value), false)
        (binding.textField as? AutoCompleteTextView)?.setAdapter(adapter)

        val dateString = DateFormat.format("dd/MM/yyyy HH:mm", Date(viewModel.date.timeInMillis))
        binding.dateButton.text = dateString
    }

    private fun setupAnimationLayout() {
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.cardView
        }
        returnTransition = Slide().apply {
            duration = 250
            addTarget(R.id.cardView)
        }
    }

    private fun showDatePicker() {

        fun limitRange(): CalendarConstraints.Builder {
            val constraintsBuilderRange = CalendarConstraints.Builder()

            val calendarStart: Calendar = GregorianCalendar.getInstance()
            val calendarEnd: Calendar = GregorianCalendar.getInstance()

            calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.get(Calendar.DAY_OF_MONTH))
            calendarStart.add(Calendar.DAY_OF_MONTH, -1)
            calendarEnd.add(Calendar.YEAR, 5)

            val minDate = calendarStart.timeInMillis
            val maxDate = calendarEnd.timeInMillis

            constraintsBuilderRange.setStart(minDate)
            constraintsBuilderRange.setEnd(maxDate)

            val dateValidatorMin = DateValidatorPointForward.from(minDate)

            return constraintsBuilderRange.setValidator(dateValidatorMin)
        }

        val customCalendar = Calendar.getInstance()
        val datePicker =
            MaterialDatePicker.Builder.datePicker().apply {
                setCalendarConstraints(limitRange().build())
                setTitleText(R.string.add_fragment_date_picker_title)
                setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            }.build()
        datePicker.addOnPositiveButtonClickListener {
            val date = datePicker.selection
            if (date != null)
                customCalendar.timeInMillis = date

            viewModel.date.set(
                customCalendar.get(Calendar.YEAR),
                customCalendar.get(Calendar.MONTH),
                customCalendar.get(Calendar.DAY_OF_MONTH)
            )

            val dateString =
                DateFormat.format("dd/MM/yyyy HH:mm", Date(viewModel.date.timeInMillis))
            binding.dateButton.text = dateString

            showTimePicker()
        }
        datePicker.show(parentFragmentManager, null)
    }

    private fun showTimePicker() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker =
            MaterialTimePicker.Builder().apply {
                setTimeFormat(clockFormat)
                setHour(12)
                setMinute(0)
                setTitleText(R.string.add_fragment_time_picker_title)
            }.build()
        timePicker.addOnPositiveButtonClickListener {
            viewModel.date.set(
                if (isSystem24Hour) Calendar.HOUR_OF_DAY else Calendar.HOUR,
                timePicker.hour
            )
            viewModel.date.set(Calendar.MINUTE, timePicker.minute)

            val dateString =
                DateFormat.format("dd/MM/yyyy HH:mm", Date(viewModel.date.timeInMillis))
            binding.dateButton.text = dateString
        }
        timePicker.show(parentFragmentManager, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}