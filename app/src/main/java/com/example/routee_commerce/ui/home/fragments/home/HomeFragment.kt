package com.example.routee_commerce.ui.home.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.routee_commerce.databinding.FragmentHomeBinding
import com.example.routee_commerce.ui.home.fragments.home.adapters.CategoriesAdapter
import com.example.routee_commerce.ui.home.fragments.home.adapters.ProductsAdapter
import com.example.routee_commerce.ui.productDetails.ProductDetailsActivity
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

    @Inject
    lateinit var productsAdapter: ProductsAdapter
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
        viewModel.invokeAction(HomeFragmentContract.Action.LoadMostSellingProducts)
    }

    private fun initViews() {
        categoriesAdapter.categoryClicked = { position, category ->
            viewModel.invokeAction(HomeFragmentContract.Action.CategoryClicked(position, category))
        }

        binding.categoriesRv.adapter = categoriesAdapter
        binding.mostSellingProductsRv.adapter = productsAdapter

    }

    private fun subscribeToLiveData() {
        viewModel.categoriesStates.observe(viewLifecycleOwner, ::renderCategoriesViewState)
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.productsStates.observe(viewLifecycleOwner, ::renderProductViewStates)
    }

    private fun renderProductViewStates(productsState: HomeFragmentContract.ProductsState) {
        when (productsState) {
            is HomeFragmentContract.ProductsState.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(ANIMATION_MODE_SLIDE).show()
            }

            is HomeFragmentContract.ProductsState.Loading -> {
                binding.productsShimmerViewContainer.startShimmerAnimation()

            }

            is HomeFragmentContract.ProductsState.Success -> {
                productsAdapter.bindProducts(productsState.products)
                binding.productsShimmerViewContainer.stopShimmerAnimation()
                binding.productsShimmerViewContainer.visibility = View.INVISIBLE
            }
        }

    }

    private fun handleEvents(event: HomeFragmentContract.Event) {
        when (event) {
            is HomeFragmentContract.Event.NavigateToSubCategories -> {
                navigateToCategoriesFragment(event.category)
            }

            is HomeFragmentContract.Event.NavigateToProductDetails -> {
                navigateToProductDetailsFragment(event.product)
            }

        }

    }


    private fun navigateToProductDetailsFragment(product: Product) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra(Product.PRODUCT, product)
        startActivity(intent)
    }

    private fun renderCategoriesViewState(categoriesState: HomeFragmentContract.CategoriesState) {
        when (categoriesState) {
            is HomeFragmentContract.CategoriesState.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(ANIMATION_MODE_SLIDE).show()
            }

            is HomeFragmentContract.CategoriesState.Loading -> {
                binding.categoriesShimmerViewContainer.startShimmerAnimation()
            }

            is HomeFragmentContract.CategoriesState.Success -> {
                categoriesAdapter.bindCategories(categoriesState.categories)
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

    }

    override fun onResume() {
        super.onResume()
        binding.categoriesShimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.categoriesShimmerViewContainer.stopShimmerAnimation()
        super.onPause()

    }


}