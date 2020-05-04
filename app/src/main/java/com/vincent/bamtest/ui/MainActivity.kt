package com.vincent.bamtest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.vincent.bamtest.R
import com.vincent.bamtest.databinding.ItemRepositoryBinding
import com.vincent.bamtest.domain.entity.Repository
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshLayout.setOnRefreshListener {
            viewModel.loadRepositories()
        }

        viewModel.loadRepositories()
        viewModel.repositories.observe(this, Observer {
            list.adapter = Adapter(this, it, object: Adapter.OnPinListener {
                override fun onTap(item: Repository) {
                    viewModel.pin(item, !item.isFavorite)
                }
            })
        })

        viewModel.state.observe(this, Observer {
            when (it) {
                BaseViewModel.State.ERROR -> Toast.makeText(this, "Impossible to load contents", Toast.LENGTH_LONG).show()
                else -> {
                    refreshLayout.isRefreshing = it == BaseViewModel.State.LOADING
                }
            }
        })
    }

    private class Adapter(context: Context, repositories: List<Repository>, val listener: OnPinListener): ArrayAdapter<Repository>(context, R.layout.item_repository, repositories) {

        interface OnPinListener {
            fun onTap(item: Repository)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_repository, null)
                view.binding = DataBindingUtil.bind(view)!!
                view.findViewById<Button>(R.id.buttonStar).setOnClickListener {
                    val parent = it.parent as View
                    listener.onTap(parent.binding.item!!)
                }
            }
            view!!.binding.item = getItem(position)
            return view.binding.root
        }

    }
}

private var View.binding: ItemRepositoryBinding
    get() = tag as ItemRepositoryBinding
    set(value) { tag = value }