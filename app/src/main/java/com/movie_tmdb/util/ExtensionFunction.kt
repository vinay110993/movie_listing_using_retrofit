package com.movie_tmdb.util

import android.content.Context
import android.nfc.FormatException
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.movie_tmdb.R

/**
 * method to set image over imageview
 * @param: View
 * @param: url
 * */
fun Context.setImage(view: ImageView, url: String){
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .override(400, 400)
        .into(view)
}

/**
 * method to remove boiler plate code related to setting DataBindingUtil
 * @param: layout, that need to inflate
 * */
fun<T : ViewDataBinding?> ViewGroup.inflateLayout(layout: Int): T{
    return DataBindingUtil.inflate<T>(LayoutInflater.from(this.context), layout, this, false)
}

/**
 * method to get Int value from string,
 * and handle if bad format exception occur
 * */
fun String?.getIntValue(): Int{
    return try{
        this?.toIntOrNull() ?: 0
    } catch (e: FormatException){
        0
    }
}
/**
 * method to get Doublt value from string,
 * and handle if bad format exception occur
 * */
fun String?.getDoubleValue(): Double{
    return try{
        this?.toDoubleOrNull() ?: 0.0
    } catch (e: FormatException){
        0.0
    }
}

/**
 * method to remove boiler plate code related to setting text watcher
 * @param: action, lanbda function that need to perform
 * */
fun EditText.onChange(action: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { action(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * method to show long toasts
 * @param: message, that need to show
 * */
fun Context.showToastL(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * method to show info Dialogs
 * @param: title
 * @param: message
 * */
fun Context.showInfoDialog(title: String, message: String){
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        .show()
}

/**
 * method to show snack bar
 * @param: message, that need to show
 * */
fun View.showSnackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}