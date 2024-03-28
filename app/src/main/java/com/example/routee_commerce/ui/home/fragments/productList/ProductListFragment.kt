package com.example.routee_commerce.ui.home.fragments.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.domain.model.Product
import com.example.routee_commerce.databinding.FragmentProductListBinding
import com.example.routee_commerce.ui.home.fragments.productList.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    companion object {
        const val SEARCH_KEY_WORD = "searchKeyWord"
    }
    lateinit var binding: FragmentProductListBinding

    @Inject
    lateinit var productsAdapter: ProductsAdapter
    private val viewModel: SearchViewModel by viewModels()
    lateinit var searchKeyWord: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchKeyWord = arguments?.getString(SEARCH_KEY_WORD) as String
        viewModel.invokeAction(ProductListContract.Action.SearchForProducts(searchKeyWord))
        subscribeToLiveData()
        binding.categoryProductsRv.adapter = productsAdapter
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.productsListState.observe(viewLifecycleOwner, ::renderProductsViewState)
    }

    private fun renderProductsViewState(productsListState: ProductListContract.ProductsListState) {
        when (productsListState) {
            is ProductListContract.ProductsListState.Error -> {

                showErrorView(productsListState.message)
            }

            is ProductListContract.ProductsListState.Loading -> {

                showLoadingView()
            }

            is ProductListContract.ProductsListState.Success -> {
                showSuccessView(productsListState.products)
                Log.i("products @@", productsListState.products.toString())

            }
        }
    }

    private fun showLoadingView() {
        binding.productsShimmerViewContainer.isVisible = true
        binding.productsShimmerViewContainer.startShimmerAnimation()
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
    }

    private fun handleEvents(event: ProductListContract.Event) {
        when (event) {
            is ProductListContract.Event.NavigateToProductDetails -> {
                navigateToProductDetails()
            }
        }
    }

    private fun navigateToProductDetails() {
        TODO("Not yet implemented")
    }


    private fun showSuccessView(products: List<Product?>) {

        productsAdapter.bindProducts(products)
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.productsShimmerViewContainer.isVisible = false
        binding.productsShimmerViewContainer.stopShimmerAnimation()

    }


    private fun showErrorView(message: String) {
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.productsShimmerViewContainer.isVisible = false
        binding.productsShimmerViewContainer.stopShimmerAnimation()
        binding.errorMessage.text = message
        binding.tryAgainBtn.setOnClickListener {
            viewModel.invokeAction(ProductListContract.Action.SearchForProducts(searchKeyWord))
        }

    }


    override fun onResume() {
        super.onResume()
        binding.productsShimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.productsShimmerViewContainer.stopShimmerAnimation()
        super.onPause()

    }
}
