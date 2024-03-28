package com.example.routee_commerce.ui.home.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.routee_commerce.R
import com.example.routee_commerce.databinding.ActivityMainBinding
import com.example.routee_commerce.ui.home.fragments.productList.ProductListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeStatusBarTransparentAndIconsClear()
        val navController = findNavController(R.id.home_host_fragment)
        NavigationUI.setupWithNavController(binding.content.bottomNav, navController)

        onSearchViewClicked()
        subscribeToEvents()

    }

    private fun subscribeToEvents() {
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(event: MainActivityContract.Event) {
        when (event) {
            is MainActivityContract.Event.NavigateToProductList -> {
                navigateToProductList(event.searchKeyWord)
            }
        }
    }

    private fun navigateToProductList(searchKeyWord: String) {
        val args = Bundle()
        args.putString(ProductListFragment.SEARCH_KEY_WORD, searchKeyWord)
        val fragment = ProductListFragment()
        fragment.arguments = args

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_host_fragment, fragment)
            .commit()

    }

    private fun onSearchViewClicked() {
        binding.content.header.searchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    Log.i("query @@", query)
                    viewModel.invokeAction(MainActivityContract.Action.searchForProducts(query))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes as needed
                return true
            }
        })
    }

    private fun makeStatusBarTransparentAndIconsClear() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}
