package com.example.routee_commerce.ui.home.fragments.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.routee_commerce.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishListFragment : Fragment() {
     private lateinit var viewBinding:FragmentWishlistBinding
    private lateinit var viewModel: WishListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentWishlistBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[WishListViewModel::class.java]
    }
    private val adapter=WishListAdapter()
    private fun initView() {
        viewBinding.recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
        viewModel.invokeAction(WishListContract.Action.LoadWishList)
    }

    private fun subscribeToLiveData() {
        viewModel.event.observe(viewLifecycleOwner,::handleEvents)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ handleStates(it)}
            }
        }
    }

    private fun handleStates(state: WishListContract.State) {
        when (state){
            is WishListContract.State.Success->{
                adapter.bindItems(state.wishListItems)
                viewBinding.errorView.isVisible = false
                viewBinding.successView.isVisible = true
                viewBinding.loadingView.isVisible = false

            }
            is WishListContract.State.Loading->{
                showLoading(state.message)
            }
            is WishListContract.State.Error->{
                showError(state.message)
            }
        }
    }

    private fun showError(message: String) {
        viewBinding.errorView.isVisible = true
        viewBinding.successView.isVisible = false
        viewBinding.loadingView.isVisible = false
        viewBinding.errorText.text=message
        viewBinding.tryAgainButton.setOnClickListener(){
            viewModel.invokeAction(WishListContract.Action.LoadWishList)
        }
    }

    private fun showLoading(message: String) {
        viewBinding.errorView.isVisible = false
        viewBinding.successView.isVisible = false
        viewBinding.loadingView.isVisible = true
        viewBinding.errorText.text=message
    }

    private fun handleEvents(event: WishListContract.Event?) {

    }

}