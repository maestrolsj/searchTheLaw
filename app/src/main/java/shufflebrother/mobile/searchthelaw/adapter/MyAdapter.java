package shufflebrother.mobile.searchthelaw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import shufflebrother.mobile.searchthelaw.customview.ItemView;
import shufflebrother.mobile.searchthelaw.entity.Law;

public class MyAdapter extends BaseAdapter {

	
	private final List<Law> list = new ArrayList<Law>();

	Context mContext;

	public MyAdapter(Context context) {
		mContext = context;
	}

	public void add_chunk(Law it) {
		 
		list.add(it);

	}
	
 
	
	public void remove_chunk(int position) {
		 
		list.remove(position);

	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
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
		
		ItemView layout;
		
		if (convertView == null) {
			 layout = new ItemView(mContext.getApplicationContext(),list.get(position));
		}
		
		else{
			
			layout = (ItemView) convertView;
			layout.setTitle(list.get(position).getTitle());
			layout.setContent(list.get(position).getContent());
		 
		}
		  
	 

		return layout;
	}
	
	

}
