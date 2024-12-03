package com.example.myapplication.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.model.User
import com.example.myapplication.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private lateinit var mViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        _binding!!.button.setOnClickListener {
            insertDataToDB()
        }

        return _binding!!.root
    }

    private fun insertDataToDB() {
        val fName = _binding?.etFirst?.text.toString()
        val lName = _binding?.etLast?.text.toString()
        val age = _binding?.etAge?.text

        if(inputCheck(fName, lName, age)){
            val user = User(0, fName, lName, Integer.parseInt(age.toString()))
            // Add data to database
            mViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(fName: String, lName: String, age: Editable?): Boolean {
        if (age != null) {
            return !(TextUtils.isEmpty(fName) && TextUtils.isEmpty(lName) && age.isEmpty())
        } else {
            return false
        }
    }
}