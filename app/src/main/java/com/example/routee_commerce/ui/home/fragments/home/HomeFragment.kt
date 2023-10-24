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
import com.example.routee_commerce.R
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
    lateinit var mostSellingProductsAdapter: ProductsAdapter

    @Inject
    lateinit var categoryProductsAdapter: ProductsAdapter


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
        viewModel.invokeAction(HomeFragmentContract.Action.LoadCategoryProducts)

    }

    private fun initViews() {
        categoriesAdapter.categoryClicked = { position, category ->
            viewModel.invokeAction(HomeFragmentContract.Action.CategoryClicked(position, category))
        }

        binding.categoriesRv.adapter = categoriesAdapter
        binding.mostSellingProductsRv.adapter = mostSellingProductsAdapter
        binding.categoryProductsRv.adapter = categoryProductsAdapter
        binding.categoryNameTv.text = getString(R.string.electronics)
    }

    private fun subscribeToLiveData() {
        viewModel.categoriesStates.observe(viewLifecycleOwner, ::renderCategoriesViewState)
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.mostSellingProductsStates.observe(
            viewLifecycleOwner,
            ::renderMostSellingProductViewStates
        )
        viewModel.categoryPoductsStates.observe(
            viewLifecycleOwner,
            ::renderCategoryProductsViewStates
        )
    }

    private fun renderCategoryProductsViewStates(productsState: HomeFragmentContract.ProductsState) {
        when (productsState) {
            is HomeFragmentContract.ProductsState.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(ANIMATION_MODE_SLIDE).show()
                binding.categoryProductsShimmerViewContainer.stopShimmerAnimation()

            }

            is HomeFragmentContract.ProductsState.Loading -> {

                binding.categoryProductsShimmerViewContainer.startShimmerAnimation()
            }

            is HomeFragmentContract.ProductsState.Success -> {
                categoryProductsAdapter.bindProducts(productsState.products)
                binding.categoryProductsShimmerViewContainer.stopShimmerAnimation()
                binding.categoryProductsShimmerViewContainer.visibility = View.INVISIBLE


            }
        }
    }

    private fun renderMostSellingProductViewStates(productsState: HomeFragmentContract.ProductsState) {
        when (productsState) {
            is HomeFragmentContract.ProductsState.Error -> {
                Snackbar.make(
                    requireView(),
                    "Please check your internet connection",
                    Snackbar.LENGTH_LONG
                ).setAnimationMode(ANIMATION_MODE_SLIDE).show()
                binding.mostSellingProductsShimmerViewContainer.stopShimmerAnimation()

            }

            is HomeFragmentContract.ProductsState.Loading -> {
                binding.mostSellingProductsShimmerViewContainer.startShimmerAnimation()

            }

            is HomeFragmentContract.ProductsState.Success -> {
                mostSellingProductsAdapter.bindProducts(productsState.products)
                binding.mostSellingProductsShimmerViewContainer.stopShimmerAnimation()
                binding.mostSellingProductsShimmerViewContainer.visibility = View.INVISIBLE
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
                binding.categoriesShimmerViewContainer.visibility = View.INVISIBLE
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