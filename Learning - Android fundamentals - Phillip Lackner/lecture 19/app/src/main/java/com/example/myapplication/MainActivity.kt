package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setMessage("Add this contact to your list!")
            .setIcon(R.drawable.ic_add_contact)
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Contact added!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "Contact not added!", Toast.LENGTH_SHORT).show()
            }.create()

        val options = arrayOf("Option 1", "Option 2", "Option 3")
        val singleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose an option")
            .setSingleChoiceItems(options, 0) { _, which ->
                Toast.makeText(this, "You chose ${options[which]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("OK") { _, _ ->
                Toast.makeText(this, "Option chosen!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Option not chosen!", Toast.LENGTH_SHORT).show()
            }.create()

        val multiChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose an option")
            .setMultiChoiceItems(options, booleanArrayOf(false, false, false)) { _, which, isChecked ->
                if (isChecked) {
                    Toast.makeText(this, "You chose ${options[which]}", Toast.LENGTH_SHORT).show()
                }
            }
            .setPositiveButton("OK") { _, _ ->
                Toast.makeText(this, "Option chosen!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Option not chosen!", Toast.LENGTH_SHORT).show()
            }.create()

        with(binding) {
            btnDialog1.setOnClickListener {
                alertDialog.show()
            }
            btnDialog2.setOnClickListener {
                singleChoiceDialog.show()
            }
            btnDialog3.setOnClickListener {
                multiChoiceDialog.show()
            }
        }
    }
}