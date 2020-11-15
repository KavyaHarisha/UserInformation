package com.userinformation.user

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.userinformation.R
import com.userinformation.databinding.FragmentUserListBinding
import com.userinformation.ui.BaseFragment
import com.userinformation.utils.NetworkUtils
import com.userinformation.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserListFragment : BaseFragment<UserViewModel, FragmentUserListBinding>() {

    private var adapter: UserItemAdapter = UserItemAdapter()
    override val mViewModel: UserViewModel by viewModels()

    override fun getViewBinding(): FragmentUserListBinding = FragmentUserListBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.userRecyclerView.adapter = adapter
        initUsers()
        handleNetworkChanges()
    }

    private fun initUsers() {
        mViewModel.postsUsers.observe(viewLifecycleOwner){
            state ->
            when(state){
                is State.Loading -> showLoading(true)
                is State.Success ->{
                    if (state.data.isNotEmpty()) {
                        adapter.submitList(state.data.toMutableList().filter { it.description != null && it.title != null && it.imageHref != null })
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    Toast.makeText(activity,state.message,Toast.LENGTH_LONG).show()
                    showLoading(false)
                }

            }
        }

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getUsers()
        }


        if (mViewModel.postsUsers.value !is State.Success) {
            getUsers()
        }
    }

    private fun getUsers() {
        mViewModel.getUsers()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun handleNetworkChanges() {
        context?.let {
            NetworkUtils.getNetworkLiveData(it).observe(viewLifecycleOwner) { isConnected ->
                if (!isConnected) {
                    mViewBinding.textViewNetworkStatus.text =
                        getString(R.string.text_no_connectivity)
                    mViewBinding.networkStatusLayout.apply {
                        visibility = View.VISIBLE
                        setBackgroundColor(ContextCompat.getColor(context, R.color.colorStatusNotConnected))
                    }
                } else {
                    if (mViewModel.postsUsers.value is State.Error || adapter.itemCount == 0) {
                        getUsers()
                    }
                    mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                    mViewBinding.networkStatusLayout.apply {
                        setBackgroundColor(ContextCompat.getColor(context,R.color.colorStatusConnected))

                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    visibility = View.GONE
                                }
                            })
                    }
                }
            }
        }
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

}