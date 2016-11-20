package shufflebrother.mobile.searchthelaw.customview;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.entity.Menu;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItemView extends LinearLayout {

	ImageView mIcon;
	TextView menuTitle;

	public MenuItemView(Context context, Menu it) {
		super(context);

		init(context);
		setIcon(it.getmIcon());
		setTitle(it.getTitle());

	}

	private void init(Context context) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.menu_item_view, this, true); 

		mIcon     = (ImageView) linearLayout.findViewById(R.id.ImageView_menuIcon);
		menuTitle = (TextView) linearLayout.findViewById(R.id.TextView_menuItemTitle);

	}

	public void setIcon(Drawable data) {

		mIcon.setImageDrawable(data);
	}

	public void setTitle(String data) {

		menuTitle.setText(data);
	}
}
