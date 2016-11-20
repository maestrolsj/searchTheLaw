package shufflebrother.mobile.searchthelaw.entity;

import android.graphics.drawable.Drawable;

public class SettingData {

	private Drawable mIcon;
	private String   title;
	private int chkState;
	 
	
	public SettingData(Drawable pIcon, int plevel, String mTitle) {
		// TODO Auto-generated constructor stub
		mIcon = pIcon;
		chkState = plevel;
		title = mTitle;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Drawable getmIcon() {
		return mIcon;
	}


	public void setmIcon(Drawable mIcon) {
		this.mIcon = mIcon;
	}


	public int getChkState() {
		return chkState;
	}


	public void setChkState(int chkState) {
		this.chkState = chkState;
	}
 

}
