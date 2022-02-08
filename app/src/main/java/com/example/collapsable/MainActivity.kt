package com.example.collapsable

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.collapsable.databinding.MainActivityBinding
import com.example.collapsable.ui.main.MainBaseFragment
import com.example.collapsable.ui.main.MainFragment
import com.example.collapsable.utils.FragmentUtils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

/* @AndroidEntryPoint annotation needed. Otherwise Fragment @AndroidEntryPoint will give error
* */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainBaseFragment.MainBaseControllerInterface {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        openMainFragment()
        createCollapsedToolbarBehaviour()
    }

    /** Make invisible title when CollapsingToolbarLayout expanded
     **/
    private fun createCollapsedToolbarBehaviour() {
        val collapsingToolbarLayout = binding.toolbarLayout

        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )
    }

    private fun openMainFragment() {
        FragmentUtils.replaceFragment(
            supportFragmentManager, MainFragment.newInstance(),
            binding.container.id, null, false
        )
    }

    /** Called from MainBaseFragment instance via Controller Interface to add toolbar image
     * @param image imageUrl that wanted to show
     * */
    override fun updateToolbarImage(image: String?) {
        if (!TextUtils.isEmpty(image)) {
            Picasso.get()
                .load(image)
                .into(binding.ivImage)
        }
    }

    /** Called from MainBaseFragment instance via Controller Interface to add toolbar title
     * @param title title that wanted to show
     * */
    override fun updateToolbarTitle(title: String?) {
        if (title != null) {
            binding.toolbar.title = title
        } else {
            binding.toolbar.title = resources.getString(R.string.PRODUCT_PAGE)
        }
    }
}