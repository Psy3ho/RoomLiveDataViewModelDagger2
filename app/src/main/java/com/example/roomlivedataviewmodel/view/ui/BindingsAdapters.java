package com.example.roomlivedataviewmodel.view.ui;


import android.view.View;

import androidx.databinding.BindingAdapter;

import org.jetbrains.annotations.NotNull;

public final class BindingsAdapters {

    @BindingAdapter({"visibleGone"})
    public static void showHide(@NotNull View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }


}