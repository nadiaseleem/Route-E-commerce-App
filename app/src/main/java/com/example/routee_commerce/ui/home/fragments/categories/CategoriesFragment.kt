package com.example.routee_commerce.ui.home.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Category
import com.example.domain.model.Subcategory
import com.example.routee_commerce.databinding.FragmentCategoriesBinding
import com.example.routee_commerce.ui.home.fragments.categories.adapters.CategoriesAdapter
import com.example.routee_commerce.ui.home.fragments.categories.adapters.SubcategoriesAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: CategoriesViewModel by viewModels()
    private val args: CategoriesFragmentArgs by navArgs()

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var subcategoriesAdapter: SubcategoriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initViews()
        viewModel.invokeAction(CategoriesFragmentContract.Action.LoadCategories)
    }

    private fun initCategoryCard(category: Category?) {
        Picasso.get()
            .load(category?.image)
            .centerCrop()
            .fit()
            .into(binding.cardBgImv)
        binding.selectedCategoryName.text = category?.name

    }


    private fun initViews() {
        binding.categoriesRv.adapter = categoriesAdapter

        binding.subcategoryRv.adapter = subcategoriesAdapter


        categoriesAdapter.categoryClicked = { position, category ->
            initCategoryCard(category)

            viewModel.invokeAction(
                CategoriesFragmentContract.Action.CategoryClicked(
                    position,
                    category
                )
            )
        }


    }

    private fun subscribeToLiveData() {
        viewModel.categoriesStates.observe(viewLifecycleOwner, ::renderCategoriesViewState)
        viewModel.subcategoriesStates.observe(viewLifecycleOwner, ::renderSubcategoriesViewState)
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
    }

    private fun renderSubcategoriesViewState(state: CategoriesFragmentContract.State<Subcategory>) {
        when (state) {
            is CategoriesFragmentContract.State.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
            }

            is CategoriesFragmentContract.State.Loading -> {

            }

            is CategoriesFragmentContract.State.Success -> {
                subcategoriesAdapter.bindSubcategories(state.data)

            }
        }
    }

    private fun showLoadingView() {
        binding.categoriesShimmerViewContainer.isVisible = true
        binding.categoriesShimmerViewContainer.startShimmerAnimation()
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
    }

    private fun handleEvents(event: CategoriesFragmentContract.Event) {
        when (event) {
            is CategoriesFragmentContract.Event.ShowSubCategories -> {
                viewModel.invokeAction(CategoriesFragmentContract.Action.LoadSubCategories(event.category))
            }
        }

    }

    private fun renderCategoriesViewState(state: CategoriesFragmentContract.State<Category>) {
        when (state) {
            is CategoriesFragmentContract.State.Error -> {

                showErrorView(state.message)
            }

            is CategoriesFragmentContract.State.Loading -> {
                showLoadingView()

            }

            is CategoriesFragmentContract.State.Success -> {
                showSuccessView(state.data)
            }
        }
    }

    private fun showSuccessView(categories: List<Category?>) {

        categoriesAdapter.bindCategories(categories)
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.categoriesShimmerViewContainer.isVisible = false
        binding.categoriesShimmerViewContainer.stopShimmerAnimation()
        initCategoryCard(categories[0])
        categories[0]?.let {
            viewModel.invokeAction(CategoriesFragmentContract.Action.LoadSubCategories(it))

        }

        args.category?.let { category ->
            viewModel.invokeAction(CategoriesFragmentContract.Action.LoadSubCategories(category))
        }
        if (args.category != null) {
            initCategoryCard(args.category)
            categoriesAdapter.selectItemOfCategory(category = args.category)
            args.category?.let { category ->
                viewModel.invokeAction(CategoriesFragmentContract.Action.LoadSubCategories(category))
            }

        }

    }


    private fun showErrorView(message: String) {
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.categoriesShimmerViewContainer.isVisible = false
        binding.categoriesShimmerViewContainer.stopShimmerAnimation()

        binding.errorMessage.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(CategoriesFragmentContract.Action.LoadCategories)
        }

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