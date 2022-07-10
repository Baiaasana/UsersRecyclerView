package com.example.usersrecyclerview

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.usersrecyclerview.databinding.ActivityAddItemBinding
import com.example.usersrecyclerview.databinding.ItemUserBinding

class UserAdapter(val context: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var binding2: ActivityAddItemBinding

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var firstName: AppCompatTextView = binding.txvFirstName
        var lastName: AppCompatTextView = binding.txvLastName
        var email: AppCompatTextView = binding.txvMail

        init {
            binding.btnEdit.setOnClickListener {
                editItem()
            }
            binding.btnRemove.setOnClickListener {
                removeItem()
            }
        }
        @SuppressLint("NotifyDataSetChanged")
        private fun editItem() {

            val position = userList[adapterPosition]
            binding2 = ActivityAddItemBinding.inflate(LayoutInflater.from(context))
            val view = binding2.root

            binding2.edtAddName.hint = position.firstName
            binding2.edtAddLastName.hint = position.lastName
            binding2.edtAddMail.hint = userList[adapterPosition].mail

            val firstName = binding2.edtAddName
            val lastName = binding2.edtAddLastName
            val email = binding2.edtAddMail

            AlertDialog.Builder(context)
                .setView(view)
                .setIcon(R.drawable.ic_baseline_edit_24)
                .setTitle(context.getString(R.string.change_user_info))
                .setPositiveButton(context.getString(R.string.change)) { dialog, _ ->
                    position.firstName = firstName.text.toString()
                    position.lastName = lastName.text.toString()
                    position.mail = email.text.toString()
                    when (false) {
                        !isEmptyField() ->
                            Toast.makeText(context, "Please, fill all fields!", Toast.LENGTH_SHORT)
                                .show()
                        isValidEmail() -> {
                            Toast.makeText(context, "Please, enter the valid mail!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {

                            notifyDataSetChanged()
                            Toast.makeText(
                                context,
                                "User information changed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            dialog.dismiss()
                        }
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
            true
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun removeItem() {

            AlertDialog.Builder(context)
                .setTitle("Remove")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("Are you sure remove this Information?")
                .setPositiveButton("Yes") { dialog, _ ->
                    userList.removeAt(adapterPosition)
                    notifyDataSetChanged()
                    Toast.makeText(
                        context,
                        "User information removed!",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()

            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.firstName.text = newList.firstName
        holder.lastName.text = newList.lastName
        holder.email.text = newList.mail
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding2.edtAddMail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding2) {
        return@with binding2.edtAddName.text.toString().isEmpty() ||
                binding2.edtAddLastName.text.toString().isEmpty() ||
                binding2.edtAddMail.text.toString().isEmpty()
    }


}