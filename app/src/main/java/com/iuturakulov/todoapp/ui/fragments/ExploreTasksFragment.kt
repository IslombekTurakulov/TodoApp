package com.iuturakulov.todoapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.iuturakulov.todoapp.R
import com.iuturakulov.todoapp.databinding.FragmentExploreTasksBinding
import com.iuturakulov.todoapp.extensions.safeNavigate
import com.iuturakulov.todoapp.extensions.toListAll
import com.iuturakulov.todoapp.extensions.toListOverdue
import com.iuturakulov.todoapp.ui.adapter.TasksAdapter
import com.iuturakulov.todoapp.ui.viewmodel.explore.ExploreViewModel
import com.iuturakulov.todoapp.ui.viewmodel.explore.FilterTasks
import com.iuturakulov.todoapp.ui.viewmodel.explore.TasksResult
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ExploreTasksFragment : Fragment() {

    private var _binding: FragmentExploreTasksBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ExploreViewModel by viewModels()
    private val tasksAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TasksAdapter(requireContext(), ::onTaskClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupListeners()
        setupLists()
        viewModel.tasksResult.observe(viewLifecycleOwner, ::handleTasks)
        viewModel.filterState.observe(viewLifecycleOwner, ::handleFilterTasks)
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchData()
    }

    private fun setupListeners() {
        binding.toolBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.search_fragment -> {
                    exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                        duration = 500
                    }
                    reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                        duration = 500
                    }
                    val directions = ExploreTasksFragmentDirections.actionExploreFragmentToSearchFragment()
                    findNavController().safeNavigate(directions)
                    true
                }
                else -> false
            }
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_ExploreFragment_to_AddFragment)
            exitTransition = MaterialElevationScale(false).apply {
                duration = 500
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = 500
            }
        }
        binding.tasksButton.setOnClickListener {
            showTasksMenu(it)
        }
    }

    private fun setupLists() {
        binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.todayRecyclerView.adapter = tasksAdapter
    }

    private fun handleTasks(state: TasksResult) {
        when (state) {
            is TasksResult.SuccessResult -> {
                viewModel.currentList = state.result
                when (binding.tasksButton.text) {
                    getString(FilterTasks.ALL.value) -> tasksAdapter.submitList(viewModel.currentList.toListAll())
                    getString(FilterTasks.OVERDUE.value) -> tasksAdapter.submitList(viewModel.currentList.toListOverdue())
                    else -> tasksAdapter.submitList(viewModel.currentList.toListAll())
                }
            }
            is TasksResult.ErrorResult -> {

            }
            is TasksResult.EmptyResult -> {

            }
            is TasksResult.Loading -> {

            }
        }
    }

    private fun handleFilterTasks(state: FilterTasks) {
        when (state) {
            FilterTasks.ALL -> {
                binding.tasksButton.text = getString(FilterTasks.ALL.value)
                tasksAdapter.submitList(viewModel.currentList.toListAll())
            }
            FilterTasks.OVERDUE -> {
                binding.tasksButton.text = getString(FilterTasks.OVERDUE.value)
                tasksAdapter.submitList(viewModel.currentList.toListOverdue())
            }
        }
    }

    private fun showLoadingCategories() {

    }

    private fun hideLoadingCategories() {

    }

    private fun onTaskClicked(id: Long, view: View) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 500
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 500
        }
        Timber.i("id=$id")
        val extras = FragmentNavigatorExtras(view to view.transitionName)
        val directions = ExploreTasksFragmentDirections.actionExploreFragmentToDetailsFragment(id)
        findNavController().safeNavigate(directions, extras)
    }
    private fun showTasksMenu(v: View) {
        val listPopupWindow = ListPopupWindow(
            requireContext(),
            null,
            com.google.android.material.R.attr.listPopupWindowStyle
        )
        listPopupWindow.anchorView = v

        val items = listOf(
            getString(FilterTasks.ALL.value),
            getString(FilterTasks.OVERDUE.value)
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_window_item, items)
        listPopupWindow.setAdapter(adapter)

        listPopupWindow.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> viewModel.toggleFilterState(FilterTasks.ALL)
                1 -> viewModel.toggleFilterState(FilterTasks.OVERDUE)
            }
            listPopupWindow.dismiss()
        }

        listPopupWindow.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}