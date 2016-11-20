package shufflebrother.mobile.searchthelaw.entity;

import android.graphics.drawable.Drawable;

public class Menu {

	private Drawable mIcon;
	private String title;

	public Menu(Drawable pIcon, String pTitle) {
		// TODO Auto-generated constructor stub
		mIcon = pIcon;
		title = pTitle;
	}

	public Drawable getmIcon() {
		return mIcon;
	}

	public void setmIcon(Drawable mIcon) {
		this.mIcon = mIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
