package com.example.routee_commerce.ui.home.fragments.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.routee_commerce.R
import com.example.routee_commerce.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getWishList()
    }
    private val adapter=WishListAdapter()
    private fun initView() {
        viewBinding.recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.wishListLiveData.observe(viewLifecycleOwner){
            adapter.bindItems(it)
        }
    }

}