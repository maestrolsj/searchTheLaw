package shufflebrother.mobile.searchthelaw.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.activity.MainActivity;
import shufflebrother.mobile.searchthelaw.adapter.MenuAdapter;
import shufflebrother.mobile.searchthelaw.dialog.SettingDialog;
import shufflebrother.mobile.searchthelaw.dialog.SettingDialog.OnClickSettingMenuListener;
import shufflebrother.mobile.searchthelaw.entity.Menu;
import shufflebrother.mobile.searchthelaw.enums.MenuState;
import shufflebrother.mobile.searchthelaw.utils.FontUtil;

public class MenuFragment extends Fragment implements OnClickSettingMenuListener   {

	public interface OnClickMenuListener {
		public void onClickMenuItem(MenuState menuType);
	}

	private OnClickMenuListener mOnClickmenuListener;

	private ViewHolder mViewHolder;
	private ListView  list;
	private ImageView settingButton;
	private DialogFragment dlgFragment;
	private String menuTitle;

	MenuAdapter adapter;

	View rootView;

	public MenuFragment() {
	}

	public static MenuFragment newInstance(MainActivity activity) {
		MenuFragment fragment = new MenuFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_more, container, false);

		// 메뉴슬라이드 타이틀 설정
		TextView menuTextView = (TextView) rootView.findViewById(R.id.TextView_menuTitle);
		menuTextView.setTypeface(FontUtil.getHannaTypeFace(rootView.getContext()));

		// 세팅버튼
		settingButton = (ImageView) rootView.findViewById(R.id.ImageView_settingButton);
		settingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initSettingPopup();
			}
		});

		// 리스트뷰 파라미터 설정 및 객체 생성
		list = (ListView) rootView.findViewById(R.id.Menu_ListView);
		adapter = new MenuAdapter(rootView.getContext());

        setMyMenu();
		

		// getActivity() : MenuFragment가 속해있는 Activity(MainActivity)를 가져옴
		mOnClickmenuListener = (OnClickMenuListener) getActivity();
		// 리스트뷰에 어댑터 설정
		list.setAdapter(adapter);
		

		// 리스트뷰에 아이템 클릭 리스너 부착
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				menuTitle = adapter.getItem(position).getTitle();
				
				switch (menuTitle) {
				case "특허법"      : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_PATENT);     break;
				case "민법"		   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_MINLAW);     break;
				case "민사소송"    : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_MINSONGLAW); break;
                case "민사집행법"    : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_MINSA);   break;
				case "실용신안"    : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_SINAN);  	  break;
				case "저작권법"    : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_COPY); 	    break;
				case "부정경쟁방지": mOnClickmenuListener.onClickMenuItem(MenuState.MENU_FIGHT); 	    break;
				case "상표법"	   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_TM);   	    break;
				case "디자인보호"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_DESIGN); 	    break;
				case "헌법"		   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HUNLAW); 	    break;
				case "상법"		   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_SANGLAW);      break;
				case "형법"		   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HYUNGLAW);     break;
				case "형사소송"	   : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HYUNGSALAW);   break;
                case "행정소송법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HANGSONG);   break;
				case "학교폭력법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_SCHOOLLAW) ;   break;
                case "초중등교육법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_TEACHER) ;   break;
                case "청소년보호"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_TEENAGER) ;    break;
                case "근로기준법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_WORKLAW) ;     break;
                case "헌법재판소법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HUNJAEPAN) ; break;
                case "가등기담보법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_COLLATERAL) ; break;
                case "수표법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_SELFCHECK) ; break;
                case "어음법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_PROMISSNOTE) ; break;
                case "소득세법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_GAINTAX) ; break;
                case "상속세법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_HERITAGETAX) ; break;
                case "법인세법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_FIRMTAX) ; break;
                case "부가가치세법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_VAT) ; break;
                case "국가공무원법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_KOREAOFFICE) ; break;
                case "교육공무원법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_TEACHOFFICE) ; break;
                case "자연환경보전법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_ENVIRONMENT) ; break;
                case "도로교통법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_TRAFFIC) ; break;
                case "정보보호법"  : mOnClickmenuListener.onClickMenuItem(MenuState.MENU_SECURITY) ; break;

                default:		break;
				}
			}
		});

		return rootView;
	}

	private void initViews() {

	}

	private void initActionBar() {

	}

	private class ViewHolder {
		public ViewHolder(View rootView) {

		}
	}

	// 세팅 팝업창
	private void initSettingPopup() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("SettingDialog");

		if (prev != null)
			ft.remove(prev);
		ft.addToBackStack(null);

		dlgFragment = SettingDialog.newInstance(this);
		dlgFragment.show(ft, "SettingDialog");

		
	}

	@Override
	public void onClickSettingMenuItem() {
		// TODO Auto-generated method stub
		adapter.remove_all();
        setMyMenu();
		adapter.notifyDataSetChanged();
	}
	
	public void setMyMenu()
	{
		
		// 어댑터에 아이템에 데이터 만들기
		Resources res = getResources();
		if(MainActivity.PATENT_STATE    == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.patent),    "특허법"       ));
		if(MainActivity.MINLAW_STATE    == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.minlaw),    "민법"         ));
		if(MainActivity.MINSALAW_STATE  == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.minsong),   "민사소송"     ));
        if(MainActivity.MINSA_STATE  == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.minsa),   "민사집행법"     ));
		if(MainActivity.SINAN_STATE     == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.sinan),     "실용신안"     ));
		if(MainActivity.COPYRIGHT_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.copyright), "저작권법"     ));
		if(MainActivity.FIGHT_STATE     == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.nocopy),    "부정경쟁방지" ));
		if(MainActivity.TM_STATE        == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.tm),        "상표법"       ));
		if(MainActivity.DESIGN_STATE    == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.design),    "디자인보호"   ));
		if(MainActivity.HUNLAW_STATE    == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.hunlaw),    "헌법"         ));
		if(MainActivity.SANGLAW_STATE   == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.sanglaw),   "상법"         ));
		if(MainActivity.HYUNGLAW_STATE  == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.hyunglaw),  "형법"         ));
		if(MainActivity.HYUNGSALAW_STATE== 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.hyunhsong), "형사소송"     ));
        if(MainActivity.HANGSONG_STATE== 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.hangsong), "행정소송법"     ));
		if(MainActivity.SCHOOLLAW_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.school),    "학교폭력법"   ));
        if(MainActivity.TEACHER_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.teacher),    "초중등교육법"   ));
        if(MainActivity.TEENAGER_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.teenager),    "청소년보호"   ));
        if(MainActivity.WORKLAW_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.worklaw),    "근로기준법"   ));
        if(MainActivity.HUNJAEPAN_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.hunjaepan),    "헌법재판소법"   ));
        if(MainActivity.COLLATERAL_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.collateral),    "가등기담보법"   ));
        if(MainActivity.SELFCHECK_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.selfcheck),    "수표법"   ));
        if(MainActivity.PROMISSNOTE_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.promissnote),    "어음법"   ));
        if(MainActivity.GAINTAX_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.gaintax),    "소득세법"   ));
        if(MainActivity.HERITAGETAX_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.heritagetax),    "상속세법"   ));
        if(MainActivity.FIRMTAX_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.firmtax),    "법인세법"   ));
        if(MainActivity.VAT_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.vat),    "부가가치세법"   ));
        if(MainActivity.KOREAOFFICE_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.koreaofficer),    "국가공무원법"   ));
        if(MainActivity.TEACHOFFICE_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.teachoffice),    "교육공무원법"   ));
        if(MainActivity.ENVIRONMENT_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.environment),    "자연환경보전법"   ));
        if(MainActivity.TRAFFIC_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.traffic),    "도로교통법"   ));
        if(MainActivity.SECURITY_STATE == 1) adapter.add_chunk(new Menu(res.getDrawable(R.drawable.security),    "정보보호법"   ));
		
	}


}
