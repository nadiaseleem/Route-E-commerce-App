package com.example.routee_commerce.ui.home.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Category
import com.example.routee_commerce.databinding.FragmentHomeBinding
import com.example.routee_commerce.ui.home.fragments.home.adapters.CategoriesAdapter
import com.example.routee_commerce.utils.EventWrapper
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initViews()
        viewModel.invokeAction(HomeFragmentContract.Action.LoadCategories)
    }

    private fun initViews() {
        categoriesAdapter.categoryClicked = { position, category ->
            viewModel.invokeAction(HomeFragmentContract.Action.CategoryClicked(position, category))
        }
        binding.categoriesRv.adapter = categoriesAdapter

    }

    private fun subscribeToLiveData() {
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
    }

    private fun handleEvents(eventWrapper: EventWrapper<HomeFragmentContract.Event>) {
        when (val event = eventWrapper.contentIfNotHandled) {
            is HomeFragmentContract.Event.NavigateToSubCategories -> {
                navigateToCategoriesFragment(event.category)
            }
        }
    }

    private fun renderViewState(state: HomeFragmentContract.State) {
        when (state) {
            is HomeFragmentContract.State.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(ANIMATION_MODE_SLIDE).show()
            }

            is HomeFragmentContract.State.Loading -> {

            }

            is HomeFragmentContract.State.Success -> {
                categoriesAdapter.bindCategories(state.categories)
                binding.categoriesShimmerViewContainer.stopShimmerAnimation()
                binding.categoriesShimmerViewContainer.visibility = View.GONE
            }
        }
    }


    private fun navigateToCategoriesFragment(category: Category) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(category)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        Log.e("app", "onDestroyView call")

    }

    override fun onResume() {
        super.onResume()
        binding.categoriesShimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.categoriesShimmerViewContainer.stopShimmerAnimation()
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
        viewModel.events.removeObservers(viewLifecycleOwner)

    }

}