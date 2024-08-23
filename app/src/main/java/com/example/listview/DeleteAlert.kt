package com.example.listview

import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class DeleteAlert {
    companion object {
        fun makeDialog(context: Context, adapter: ArrayAdapter<User>) =
            AdapterView.OnItemClickListener { parent, v, position, id ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle(context.resources.getString(R.string.alert))
                    .setMessage(context.resources.getString(R.string.delete_confirmation))
                    .setCancelable(true)
                    .setNegativeButton(context.resources.getString(R.string.delete_confirmation_no)) { dialog, which ->
                        dialog.cancel()
                    }
                    .setPositiveButton(context.resources.getString(R.string.delete_confirmation_yes)) { dialog, which ->
                        val user = adapter.getItem(position)
                        adapter.remove(user)
                        Snackbar.make(
                            parent,
                            context.resources.getString(R.string.delete_confirmation),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }.create()
                builder.show()
            }
    }
}