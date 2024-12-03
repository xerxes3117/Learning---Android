package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: ArrayAdapter<CustomerModel>
    private val customerList = ArrayList<CustomerModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListView()
        setupButtons()

        viewAllCustomers()
    }

    private fun setupListView() {
        customerAdapter = ArrayAdapter(this, R.layout.list_item, R.id.tvCustomer, customerList)
        binding.lvCustomers.adapter = customerAdapter
    }

    private fun setupButtons() {
        binding.btnAdd.setOnClickListener {
            addCustomer()
        }

        binding.btnViewAll.setOnClickListener {
            viewAllCustomers()
        }

        binding.lvCustomers.setOnItemClickListener { parent, _, position, _ ->
            val customer = parent.getItemAtPosition(position) as CustomerModel
            deleteCustomer(customer)
        }
    }

    private fun addCustomer() {
        val customerModel = try {
            CustomerModel(1, binding.etName.text.toString(), binding.etAge.text.toString().toInt(), binding.acSwitch.isChecked)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            CustomerModel(-1, "error", 0, false)
        }

        val databaseHelper = DatabaseHelper(this)
        val success = databaseHelper.addOne(customerModel)
        if (success) {
            updateCustomerList(databaseHelper)
        }
    }

    private fun viewAllCustomers() {
        val databaseHelper = DatabaseHelper(this)
        updateCustomerList(databaseHelper)
    }

    private fun updateCustomerList(databaseHelper: DatabaseHelper) {
        val everyone = databaseHelper.getEveryone()
        customerList.clear()
        everyone.forEach {
            customerList.add(it)
        }
        customerAdapter.notifyDataSetChanged()
    }

    private fun deleteCustomer(customer: CustomerModel) {
        val databaseHelper = DatabaseHelper(this)
        val success = databaseHelper.deleteOne(customer)
        if (success) {
            updateCustomerList(databaseHelper)
        }
    }
}