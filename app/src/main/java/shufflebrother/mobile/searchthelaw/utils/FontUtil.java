package shufflebrother.mobile.searchthelaw.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {

	public static Typeface getHannaTypeFace(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/BM-HANNA.ttf");
	}

	public static Typeface getTitleTypeFace(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Bold.ttf");
	}

	public static Typeface getDescriptionTypeFace(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Regular.ttf");
	}
}
