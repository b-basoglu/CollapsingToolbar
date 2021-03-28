package com.example.dolaptendyol.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.color
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dolaptendyol.R
import com.example.dolaptendyol.databinding.MainFragmentBinding
import com.example.dolaptendyol.network.response.Product.ProductResponse
import com.example.dolaptendyol.network.response.Social.SocialResponse
import com.example.dolaptendyol.network.response.Status
import dagger.hilt.android.AndroidEntryPoint

/* @AndroidEntryPoint annotation needed. Hilt @AndroidEntryPoint's directly inject with this annotation
* */
@AndroidEntryPoint
class MainFragment : MainBaseFragment() {
    val TOTAL_SECONDS = 20  //seconds for count down
    val COUNT_DOWN_INTERVAL = 1000L //interval for count down DO NOT CHANGE
    val TOTAL_TIME = TOTAL_SECONDS * COUNT_DOWN_INTERVAL // total time that counted down

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        this.binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        this.binding.viewModel = viewModel
        viewModel.getSocialResponse()
        viewModel.getProductResponse()
        setObservers()
    }

    private fun setObservers() {
        //Observe user interaction on like button. I could listen it with onclick listener
        //instead i choose async listening for further implementation
        viewModel.isFavourite.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.ivFavorite.setImageDrawable(
                    binding.ivFavorite.context.resources.getDrawable(
                        R.drawable.favourite_added_copy
                    )
                )
                binding.ivFavorite.setColorFilter(Color.RED)
            } else {
                binding.ivFavorite.setImageDrawable(
                    binding.ivFavorite.context.resources.getDrawable(
                        R.drawable.favourite
                    )
                )
                binding.ivFavorite.setColorFilter(Color.GRAY)
            }
        })

        viewModel.socialResponseLMld.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                setSocialViewItems(it.data!!)
                switchCounterLoading(false)
                timeOutRemoveTimer.start()
                Log.d("Timer : ", "TimerStarted " + TOTAL_SECONDS)

            } else if (it.status == Status.ERROR) {
                switchCounterLoading(false)
                timeOutRemoveTimer.start()
                Log.d("Timer : ", "TimerStarted " + TOTAL_SECONDS)
            } else {
                switchCounterLoading(true)
            }
        })
        viewModel.productResponseLMld.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                setProductViewItems(it.data!!)
            }
        })
    }

    /** There is two view while socialResponse status is loading show clpbProgress that shows
     *  response status loading that is quicker(high frequency low period)
     *  else show circleProgress that timer counts down
     */
    private fun switchCounterLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.clpbProgress.visibility = View.VISIBLE
            binding.circleProgress.visibility = View.INVISIBLE

        } else {
            binding.clpbProgress.visibility = View.INVISIBLE
            binding.circleProgress.visibility = View.VISIBLE

        }
    }

    //To set toolbar image
    override fun getImage(): String? {
        return viewModel.imageUrl.value
    }

    //To set toolbar title
    override fun getTitle(): String? {
        return viewModel.productName.value
    }

    /*
     * CountDownTimer takes two arguments TOTAL_TIME is seconds long and COUNT_DOWN_INTERVAL one is interval
     * in milliseconds long
     */
    private var timeOutRemoveTimer = object : CountDownTimer(TOTAL_TIME, COUNT_DOWN_INTERVAL) {
        override fun onFinish() {
            binding.circleProgress.progress = 0f
            binding.tvCircleProgress.text = "20"
            viewModel.getSocialResponse()

        }

        override fun onTick(millisUntilFinished: Long) {
            var resultantFloat: Float = (TOTAL_TIME - millisUntilFinished).toFloat() / TOTAL_TIME
            binding.circleProgress.progress = resultantFloat
            var resultantInt: Int = (resultantFloat * TOTAL_SECONDS).toInt()
            binding.tvCircleProgress.text = resultantInt.toString()
        }
    }

    //Product Related Views
    @SuppressLint("SetTextI18n")
    private fun setProductViewItems(productResponse: ProductResponse) {
        binding.tvProduct.text =
            createProductExplanation(productResponse.name!!, productResponse.desc!!, " ")
        binding.tvPrice.text =
            productResponse.price!!.value.toString() + " " + productResponse.price.currency
        controller?.updateToolbarTitle(viewModel.productName.value)
        controller?.updateToolbarImage(viewModel.imageUrl.value)
    }

    //Social Related Views
    @SuppressLint("SetTextI18n")
    private fun setSocialViewItems(socialResponse: SocialResponse) {
        binding.tvLikeCount.text = socialResponse.likeCount.toString()
        binding.rbRating.rating = socialResponse.commentCounts?.averageRating!!
        binding.tvRating.text = socialResponse.commentCounts.averageRating.toString()
        var totalComment: Int =
            socialResponse.commentCounts.anonymousCommentsCount!! + socialResponse.commentCounts.memberCommentsCount!!
        binding.tvCommentCount.text =
            "(" + totalComment.toString() + ") " + resources.getString(R.string.SUFFIX_COMMENT)
    }

    /**
     * Creates a spannable text for name and desc in Product.
     * @param parser if tou want other kind of delimiter between given two such as new line give as
     * as argument
     */
    private fun createProductExplanation(
        name: String,
        desc: String,
        parser: String
    ): SpannableStringBuilder {
        return SpannableStringBuilder()
            .color(Color.BLACK) { bold { append(name) } }
            .append(parser)
            .color(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.grey_60_transparent
                )
            ) { append(desc) }
    }

}