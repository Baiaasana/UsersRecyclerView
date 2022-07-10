package com.example.usersrecyclerview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersrecyclerview.databinding.ActivityAddItemBinding
import com.example.usersrecyclerview.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var binding2: ActivityAddItemBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList = ArrayList()
        recyclerView = binding.recyclerView
        userAdapter = UserAdapter(this, userList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        binding.btnAdd.setOnClickListener {
            addInfo()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {

        binding2 = ActivityAddItemBinding.inflate(layoutInflater)

        val firstname = binding2.edtAddName
        val lastname = binding2.edtAddLastName
        val mail = binding2.edtAddMail

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(binding2.root)
            .setIcon(R.drawable.ic_baseline_playlist_add_24)
            .setTitle(getString(R.string.add_user_info))
        addDialog.setPositiveButton(getString(R.string.add_user_btn)) { dialog, _ ->
            val names = firstname.text.toString()
            val lastnames = lastname.text.toString()
            val mails = mail.text.toString()

            when (false) {
                !isEmptyField() ->
                    Toast.makeText(this, getString(R.string.toast_empty_field), Toast.LENGTH_SHORT)
                        .show()
                isValidEmail() -> {
                    Toast.makeText(this, getString(R.string.toast_invalid_mail), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    userList.add(UserData(names, lastnames, mails))
                    userAdapter.notifyDataSetChanged()
                    Toast.makeText(this, getString(R.string.add_success), Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
            }
        }
        addDialog.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding2.edtAddMail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding2.edtAddName.text.toString().isEmpty() ||
                binding2.edtAddLastName.text.toString().isEmpty() ||
                binding2.edtAddMail.text.toString().isEmpty()
    }


}