package com.example.myapplication.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.model.User

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // Recyclerview
        val adapter = ListAdapter ({
            user -> deleteUser(user)
        })
        val recyclerView = _binding!!.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        _binding!!.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return _binding!!.root
    }

    private fun deleteUser(user: User){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mUserViewModel.deleteUser(user)
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete ${user.firstName}?")
        builder.setMessage("Are you sure you want to delete ${user.firstName}?")
        builder.create().show()
    }
}