package shufflebrother.mobile.searchthelaw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import shufflebrother.mobile.searchthelaw.customview.MenuItemView;
import shufflebrother.mobile.searchthelaw.entity.Menu;


public class MenuAdapter extends BaseAdapter {

	
	private final List<Menu> list = new ArrayList<Menu>();

	Context mContext;

	public MenuAdapter(Context context) {
		mContext = context;
	}

	public void add_chunk(Menu it) {
		 
		list.add(it);

	}
	
 
	
	public void remove_chunk(int position) {
		 
		list.remove(position);

	}
	
	public void remove_all(){
		list.removeAll(list);
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Menu getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		MenuItemView layout;
		
		if (convertView == null) {
			 layout = new MenuItemView(mContext.getApplicationContext(),list.get(position));
		}
		
		else{
			
			layout = (MenuItemView) convertView;
			layout.setTitle(list.get(position).getTitle());
			layout.setIcon(list.get(position).getmIcon());
		 
		}
		  
		

		return layout;
	}
	
	

}
