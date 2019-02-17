package com.exercise.here.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageUtils {

    public static BitmapImageViewTarget getCircleImageView(final Context c, final ImageView imageView) {
        return new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(c.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        };
    }

    public static void setImageViewDrawable(Context c, ImageView iv, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(ResourcesCompat.getDrawable(c.getResources(), resourceId, null));
        } else {
            iv.setImageDrawable(c.getResources().getDrawable(resourceId));
        }

    }
}