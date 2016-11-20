package shufflebrother.mobile.searchthelaw.dialog;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.activity.MainActivity;
import shufflebrother.mobile.searchthelaw.adapter.SettingAdapter;
import shufflebrother.mobile.searchthelaw.entity.SettingData;
import shufflebrother.mobile.searchthelaw.utils.FontUtil;

public class SettingDialog extends DialogFragment {

	private ViewHolder mViewHolder;
	private SettingAdapter setting_adapter;
	public OnClickSettingMenuListener onClickSettingMenuListener;

    public SettingDialog(){

    }

	@SuppressLint("ValidFragment")
    public SettingDialog(Fragment fragment) {
		onClickSettingMenuListener = (OnClickSettingMenuListener) fragment;
	}


	public static DialogFragment newInstance(Fragment fragment) {
		DialogFragment f = new SettingDialog(fragment);
		return f;
	}
	
	public interface OnClickSettingMenuListener {
		public void onClickSettingMenuItem();
	}


	
	private class ViewHolder {
		private ListView list;

		public ViewHolder(View rootView) {
			if (list == null)	list = (ListView) rootView.findViewById(R.id.Listview_setting);
		}
	}

	public interface OnClickSettingDialogListener {
		public void onClickSettingLevelItem(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature((int) Window.FEATURE_NO_TITLE);
		
		View rootView                 = inflater.inflate(R.layout.settings, container, false)       ;
		TextView settingTitleTextView = (TextView) rootView.findViewById(R.id.TextView_settingTitle);
		settingTitleTextView.setTypeface(FontUtil.getHannaTypeFace(rootView.getContext()))          ;
		
		mViewHolder     = new ViewHolder(rootView)                 ;
		setting_adapter = new SettingAdapter(rootView.getContext());

		Resources res = getResources();
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.patent)   , MainActivity.PATENT_STATE,"특허법"	 	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.minlaw)   , MainActivity.MINLAW_STATE,"민법"	 	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.minsong)  , MainActivity.MINSALAW_STATE,"민사소송"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.minsa)  , MainActivity.MINSA_STATE,"민사집행법"	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.sinan)    , MainActivity.SINAN_STATE,"실용신안"	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.copyright), MainActivity.COPYRIGHT_STATE,"저작권법"	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.nocopy)   , MainActivity.FIGHT_STATE,"부정경쟁방지"));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.tm)       , MainActivity.TM_STATE,"상표법"		 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.design)   , MainActivity.DESIGN_STATE,"디자인보호"	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.hunlaw)   , MainActivity.HUNLAW_STATE,"헌법"		 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.sanglaw)  , MainActivity.SANGLAW_STATE,"상법"		 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.hyunglaw) , MainActivity.HYUNGLAW_STATE,"형법"		 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.hyunhsong), MainActivity.HYUNGSALAW_STATE,"형사소송"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.hangsong), MainActivity.HANGSONG_STATE,"행정소송법"	 ));
		setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.school)   , MainActivity.SCHOOLLAW_STATE ,"학교폭력법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.teacher)   , MainActivity.TEACHER_STATE ,"초중등교육법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.teenager)   , MainActivity.TEENAGER_STATE ,"청소년보호"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.worklaw)   , MainActivity.WORKLAW_STATE ,"근로기준법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.hunjaepan)   , MainActivity.HUNJAEPAN_STATE ,"헌법재판소법"	 ));

        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.collateral)   , MainActivity.COLLATERAL_STATE ,"가등기담보법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.selfcheck)   , MainActivity.SELFCHECK_STATE,"수표법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.promissnote)   , MainActivity.PROMISSNOTE_STATE ,"어음법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.gaintax)   , MainActivity.GAINTAX_STATE ,"소득세법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.heritagetax)   , MainActivity.HERITAGETAX_STATE ,"상속세법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.firmtax)   , MainActivity.FIRMTAX_STATE ,"법인세법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.vat)   , MainActivity.VAT_STATE ,"부가가치세법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.koreaofficer)   , MainActivity.KOREAOFFICE_STATE ,"국가공무원법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.teachoffice)   , MainActivity.TEACHOFFICE_STATE ,"교육공무원법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.environment)   , MainActivity.ENVIRONMENT_STATE ,"자연환경보전"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.traffic)   , MainActivity.TRAFFIC_STATE ,"도로교통법"	 ));
        setting_adapter.add_chunk(new SettingData(res.getDrawable(R.drawable.security)   , MainActivity.SECURITY_STATE ,"정보보호법"	 ));

		mViewHolder.list.setAdapter(setting_adapter);
	 
		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
	
		SharedPreferences.Editor saveMenuState = MainActivity.sharedPrefer.edit();
		
		saveMenuState.putInt("PATENT_STATE",     MainActivity.PATENT_STATE );
		saveMenuState.putInt("MINLAW_STATE",     MainActivity.MINLAW_STATE );
		saveMenuState.putInt("MINSALAW_STATE",   MainActivity.MINSALAW_STATE );
        saveMenuState.putInt("MINSA_STATE",   MainActivity.MINSA_STATE );
		saveMenuState.putInt("SINAN_STATE",      MainActivity.SINAN_STATE  );
		saveMenuState.putInt("COPYRIGHT_STATE",  MainActivity.COPYRIGHT_STATE );
		saveMenuState.putInt("FIGHT_STATE",      MainActivity.FIGHT_STATE 	 );
		saveMenuState.putInt("TM_STATE",         MainActivity.TM_STATE  );
		saveMenuState.putInt("DESIGN_STATE",     MainActivity.DESIGN_STATE	 );
		saveMenuState.putInt("HUNLAW_STATE",     MainActivity.HUNLAW_STATE  );
		saveMenuState.putInt("SANGLAW_STATE",    MainActivity.SANGLAW_STATE  );
		saveMenuState.putInt("HYUNGLAW_STATE",   MainActivity.HYUNGLAW_STATE );
		saveMenuState.putInt("HYUNGSALAW_STATE", MainActivity.HYUNGSALAW_STATE);
        saveMenuState.putInt("HANGSONG_STATE", MainActivity.HANGSONG_STATE);
		saveMenuState.putInt("SCHOOLLAW_STATE" , MainActivity.SCHOOLLAW_STATE);
        saveMenuState.putInt("TEACHER_STATE" , MainActivity.TEACHER_STATE);
        saveMenuState.putInt("TEENAGER_STATE" , MainActivity.TEENAGER_STATE);
        saveMenuState.putInt("WORKLAW_STATE" , MainActivity.WORKLAW_STATE);
        saveMenuState.putInt("HUNJAEPAN_STATE" , MainActivity.HUNJAEPAN_STATE);

        saveMenuState.putInt("COLLATERAL_STATE" , MainActivity.COLLATERAL_STATE);
        saveMenuState.putInt("SELFCHECK_STATE" , MainActivity.SELFCHECK_STATE);
        saveMenuState.putInt("PROMISSNOTE_STATE" , MainActivity.PROMISSNOTE_STATE);
        saveMenuState.putInt("GAINTAX_STATE" , MainActivity.GAINTAX_STATE);
        saveMenuState.putInt("HERITAGETAX_STATE" , MainActivity.HERITAGETAX_STATE);
        saveMenuState.putInt("FIRMTAX_STATE" , MainActivity.FIRMTAX_STATE);
        saveMenuState.putInt("VAT_STATE" , MainActivity.VAT_STATE);
        saveMenuState.putInt("KOREAOFFICE_STATE" , MainActivity.KOREAOFFICE_STATE);
        saveMenuState.putInt("TEACHOFFICE_STATE" , MainActivity.TEACHOFFICE_STATE);
        saveMenuState.putInt("ENVIRONMENT_STATE" , MainActivity.ENVIRONMENT_STATE);
        saveMenuState.putInt("TRAFFIC_STATE" , MainActivity.TRAFFIC_STATE);
        saveMenuState.putInt("SECURITY_STATE" , MainActivity.SECURITY_STATE);
		
		saveMenuState.commit();
		onClickSettingMenuListener.onClickSettingMenuItem();
		super.onDestroyView();
	}
}
