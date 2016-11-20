package shufflebrother.mobile.searchthelaw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.activity.MainActivity;
import shufflebrother.mobile.searchthelaw.entity.SettingData;

public class SettingAdapter extends BaseAdapter {

	private final List<SettingData> list = new ArrayList<SettingData>();
	private Resources res ;
	
	int newChkState;
	Context mContext;
	
	 
	public void reloadAdapter()
	{
		this.notifyDataSetChanged();
	}
	public SettingAdapter(Context context) {
		mContext = context;
	}

	public void add_chunk(SettingData it) {
		list.add(it);
	}

	public void remove_chunk(int position) {
		list.remove(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		private ImageView Icon;
		private ImageView checkIcon;
		private TextView title;

	}
	
	final OnClickListener myBtn1Listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			
			int chkstate = list.get(position).getChkState();
			if(chkstate == 1) newChkState = 0;
			else			  newChkState = 1;
			
			switch (position) 
			{
				case 0:  MainActivity.PATENT_STATE 	    = newChkState;	break;
				case 1:  MainActivity.MINLAW_STATE 	    = newChkState;	break;
				case 2:  MainActivity.MINSALAW_STATE    = newChkState;	break;
                case 3:  MainActivity.MINSA_STATE       = newChkState;	break;
				case 4:  MainActivity.SINAN_STATE 	    = newChkState;	break;
				case 5:  MainActivity.COPYRIGHT_STATE   = newChkState;	break;
				case 6:  MainActivity.FIGHT_STATE 	    = newChkState;	break;
				case 7:  MainActivity.TM_STATE 	        = newChkState;	break;
				case 8:  MainActivity.DESIGN_STATE 	    = newChkState;	break;
				case 9:  MainActivity.HUNLAW_STATE 	    = newChkState;	break;
				case 10:  MainActivity.SANGLAW_STATE    = newChkState;	break;
				case 11: MainActivity.HYUNGLAW_STATE    = newChkState;	break;
				case 12: MainActivity.HYUNGSALAW_STATE  = newChkState;	break;
                case 13: MainActivity.HANGSONG_STATE    = newChkState;	break;
				case 14: MainActivity.SCHOOLLAW_STATE   = newChkState;	break;
                case 15: MainActivity.TEACHER_STATE     = newChkState;	break;
                case 16: MainActivity.TEENAGER_STATE    = newChkState;	break;
                case 17: MainActivity.WORKLAW_STATE     = newChkState;	break;
                case 18: MainActivity.HUNJAEPAN_STATE   = newChkState;	break;
                case 19: MainActivity.COLLATERAL_STATE  = newChkState;	break;
                case 20: MainActivity.SELFCHECK_STATE   = newChkState;	break;
                case 21: MainActivity.PROMISSNOTE_STATE = newChkState;	break;
                case 22: MainActivity.GAINTAX_STATE     = newChkState;	break;
                case 23: MainActivity.HERITAGETAX_STATE = newChkState;	break;
                case 24: MainActivity.FIRMTAX_STATE     = newChkState;	break;
                case 25: MainActivity.VAT_STATE         = newChkState;	break;
                case 26: MainActivity.KOREAOFFICE_STATE = newChkState;	break;
                case 27: MainActivity.TEACHOFFICE_STATE = newChkState;	break;
                case 28: MainActivity.ENVIRONMENT_STATE = newChkState;	break;
                case 29: MainActivity.TRAFFIC_STATE     = newChkState;	break;
                case 30: MainActivity.SECURITY_STATE    = newChkState;	break;
	
				default:
					break;
			} 		
			
			list.get(position).setChkState(newChkState);
			reloadAdapter();
		}
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder = new ViewHolder();
		res = mContext.getResources();
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.setting_item_view, null);

			viewHolder.Icon       = (ImageView) view.findViewById(R.id.ImageView_settingIcon)    ;
			viewHolder.title      = (TextView)  view.findViewById(R.id.TextView_settingItemTitle);
			viewHolder.checkIcon  = (ImageView) view.findViewById(R.id.ImageView_checkIcon)      ;
			viewHolder.checkIcon.setOnClickListener(myBtn1Listener);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		// 아이콘 그리기
		viewHolder.Icon.setImageDrawable(list.get(position).getmIcon());
		
		// 타이틀 그리기
		viewHolder.title.setText(list.get(position).getTitle());
		
		// 체크박스 그리기
		int chkstate = list.get(position).getChkState();
		if (chkstate == 1) viewHolder.checkIcon.setImageDrawable(res.getDrawable(R.drawable.checkbox_checked));
		else               viewHolder.checkIcon.setImageDrawable(res.getDrawable(R.drawable.checkbox_uncheck));
		viewHolder.checkIcon.setTag(Integer.valueOf(position));
		
		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		
		return false;
	}
}
