// Generated by view binder compiler. Do not edit!
package com.google.mediapipe.examples.poselandmarker.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.mediapipe.examples.poselandmarker.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySelectBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView cityName;

  @NonNull
  public final Button food;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final Button sightseeing;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  private ActivitySelectBinding(@NonNull LinearLayout rootView, @NonNull TextView cityName,
      @NonNull Button food, @NonNull ImageView imageView3, @NonNull ImageView imageView4,
      @NonNull Button sightseeing, @NonNull TextView textView3, @NonNull TextView textView4) {
    this.rootView = rootView;
    this.cityName = cityName;
    this.food = food;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.sightseeing = sightseeing;
    this.textView3 = textView3;
    this.textView4 = textView4;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySelectBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySelectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_select, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySelectBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.city_name;
      TextView cityName = ViewBindings.findChildViewById(rootView, id);
      if (cityName == null) {
        break missingId;
      }

      id = R.id.food;
      Button food = ViewBindings.findChildViewById(rootView, id);
      if (food == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.sightseeing;
      Button sightseeing = ViewBindings.findChildViewById(rootView, id);
      if (sightseeing == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      return new ActivitySelectBinding((LinearLayout) rootView, cityName, food, imageView3,
          imageView4, sightseeing, textView3, textView4);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
