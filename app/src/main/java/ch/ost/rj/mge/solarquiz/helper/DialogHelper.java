package ch.ost.rj.mge.solarquiz.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Locale;

import ch.ost.rj.mge.solarquiz.R;

public class DialogHelper {

    public static ImageView getAppropriateAstronautImage(Context context, String answerTitle) {
        ImageView image = new ImageView(context);
        Drawable dr;
        if (answerTitle.toLowerCase(Locale.ROOT).contains("wrong")) {
            dr = context.getResources().getDrawable(R.drawable.astronaut_false);
        } else {
            dr = context.getResources().getDrawable(R.drawable.astronaut_true);
        }
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable sadAstronaut = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 300, 300, true));
        image.setImageDrawable(sadAstronaut);
        return image;
    }
}
