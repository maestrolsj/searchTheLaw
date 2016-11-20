package shufflebrother.mobile.searchthelaw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mocoplex.adlib.AdlibConfig;
import com.mocoplex.adlib.AdlibManager;

import java.util.Calendar;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.customview.ItemView;
import shufflebrother.mobile.searchthelaw.enums.MenuState;
import shufflebrother.mobile.searchthelaw.fragment.LawFragment;
import shufflebrother.mobile.searchthelaw.fragment.MenuFragment;
import shufflebrother.mobile.searchthelaw.fragment.MenuFragment.OnClickMenuListener;
import shufflebrother.mobile.searchthelaw.utils.FontUtil;

import static android.view.Gravity.START;

public class MainActivity extends FragmentActivity implements OnClickMenuListener {


    public interface OnChangeFontListener {
        public void changeFont(int fontSize);
    }

    // 슬라이드 메뉴 관련 --------------
    private SlideMenuLib menu;
    private DrawerLayout drawer;
    private boolean flipped;
    private float offset;
    // --------------------------

    private AdlibManager _amanager;
    private TextView actionbarTitle;

    public static SharedPreferences sharedPrefer;

    private OnChangeFontListener mFontChangeListener;
    private FragmentTransaction transaction;

    int fontSize;

    // Back Button 관련 시작 ---------------------------------
    private static final int MSG_TIMER_EXPIRED = 1;
    private static final int BACKKEY_TIMEOUT = 2;
    private static final int MILLIS_IN_SEC = 1000;
    private boolean mIsBackKeyPressed = false;
    private long mCurrTimeInMillis = 0;
    // Back Button 관련 END ---------------------------------

    // --------- 사전종류 -----------------------------------
    public static int PATENT_STATE; // 특허법
    public static int MINLAW_STATE; // 민법
    public static int MINSALAW_STATE; // 민사소송법
    public static int MINSA_STATE; // 민사집행법
    public static int SINAN_STATE; // 실용신안법
    public static int COPYRIGHT_STATE; // 저작권법
    public static int FIGHT_STATE; // 부정경쟁방지법
    public static int TM_STATE; // 상표법
    public static int DESIGN_STATE; // 디자인보호법
    public static int HUNLAW_STATE; // 헌법
    public static int HYUNGLAW_STATE; // 형법
    public static int SANGLAW_STATE; // 상법
    public static int HYUNGSALAW_STATE; // 형사소송법
    public static int HANGSONG_STATE; // 행정소송법
    public static int SCHOOLLAW_STATE; // 학교폭력법
    public static int TEACHER_STATE; //  초중등교육법
    public static int TEENAGER_STATE; // 청소년보호법
    public static int WORKLAW_STATE; // 근로기준법
    public static int HUNJAEPAN_STATE; // 헌법재판소법
    public static int COLLATERAL_STATE;  //가등기담보법
    public static int SELFCHECK_STATE;  // 수표법
    public static int PROMISSNOTE_STATE; // 어음법
    public static int GAINTAX_STATE;      // 소득세법
    public static int HERITAGETAX_STATE;  // 상속세법
    public static int FIRMTAX_STATE;    // 법인세법
    public static int VAT_STATE;       // 부가가치세
    public static int KOREAOFFICE_STATE;  // 국가공무원법
    public static int TEACHOFFICE_STATE;  // 교육공무원법
    public static int ENVIRONMENT_STATE;  // 환경보전법
    public static int TRAFFIC_STATE; // 도로교통법
    public static int SECURITY_STATE; // 정보보호법


    // -------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 애드립 광고배너
        _amanager = new AdlibManager("54d88dd00cf2428354da7425");
        _amanager.onCreate(this);

        // 슬라이드 메뉴---------------------------------------------------------
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        final Resources resources = getResources();

        menu = new SlideMenuLib(resources);
        menu.setStrokeColor(resources.getColor(R.color.light_gray));
        imageView.setImageDrawable(menu);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    menu.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    menu.setFlip(flipped);
                }

                menu.setParameter(offset);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(START))
                    drawer.closeDrawer(START);
                else
                    drawer.openDrawer(START);

            }
        });

        initAD();    // 광고모듈 시작
        _amanager.setAdsContainer(R.id.adview1);
        init_menu(); // 사전 메뉴초기화

        // 슬라이드메뉴 장착
        getSupportFragmentManager().beginTransaction().replace(R.id.menu, MenuFragment.newInstance(this)).commit();

        try {

            //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // -------- 액션바 타이틀 폰트 설정 --------------------------------------
            actionbarTitle = (TextView) findViewById(R.id.actionbar_text_id);
            actionbarTitle.setTypeface(FontUtil.getHannaTypeFace(this));

            // 앱 공유하기 버튼 클릭시-------------------------------------------------------------------------------------------------------
            ImageView actionbarSearch = (ImageView) findViewById(R.id.action_bar_share);

            actionbarSearch.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent msg = new Intent(Intent.ACTION_SEND);
                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                    msg.putExtra(Intent.EXTRA_SUBJECT, "[서치더로우]\n구글마켓 1위 ★★★★★☆ \n오프라인법률사전\n다운로드");
                    msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=shufflebrother.mobile.searchthelaw");
                    msg.putExtra(Intent.EXTRA_TITLE, "서치더로우");
                    msg.setType("text/plain");

                    startActivity(Intent.createChooser(msg, "공유"));

                }
            });
            // ----------------------------------------------------------------------------------------------------------------------------

            setFontSize(); // 법률사전 폰트사이즈 세팅

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // ================================================================================

        openFirstPage();

        Toast.makeText(this, "볼륨 키 : 폰트 조절", Toast.LENGTH_SHORT).show();

        Tracker tracker = ((MainApplication) getApplication())
                .getTracker(MainApplication.TrackerName.APP_TRACKER);
        tracker.setScreenName("MainActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            GoogleAnalytics.getInstance(this).reportActivityStart(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            GoogleAnalytics.getInstance(this).reportActivityStop(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        _amanager.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        _amanager.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _amanager.onDestroy(this);
        super.onDestroy();
    }

    // 사전 메뉴초기화 ----------------------------------------------------------------
    private void init_menu() {
        // 셋팅 다이얼로그에서 체크 된 항목들만 가져오기위해
        sharedPrefer = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        PATENT_STATE = sharedPrefer.getInt("PATENT_STATE", 1);
        MINLAW_STATE = sharedPrefer.getInt("MINLAW_STATE", 1);
        MINSALAW_STATE = sharedPrefer.getInt("MINSALAW_STATE", 1);
        MINSA_STATE = sharedPrefer.getInt("MINSA_STATE", 1);
        SINAN_STATE = sharedPrefer.getInt("SINAN_STATE", 1);
        COPYRIGHT_STATE = sharedPrefer.getInt("COPYRIGHT_STATE", 1);
        FIGHT_STATE = sharedPrefer.getInt("FIGHT_STATE", 1);
        TM_STATE = sharedPrefer.getInt("TM_STATE", 1);
        DESIGN_STATE = sharedPrefer.getInt("DESIGN_STATE", 1);
        HUNLAW_STATE = sharedPrefer.getInt("HUNLAW_STATE", 1);
        SANGLAW_STATE = sharedPrefer.getInt("SANGLAW_STATE", 1);
        HYUNGLAW_STATE = sharedPrefer.getInt("HYUNGLAW_STATE", 1);
        HYUNGSALAW_STATE = sharedPrefer.getInt("HYUNGSALAW_STATE", 1);
        HANGSONG_STATE = sharedPrefer.getInt("HANGSONG_STATE", 1);
        SCHOOLLAW_STATE = sharedPrefer.getInt("SCHOOLLAW_STATE", 1);
        TEACHER_STATE = sharedPrefer.getInt("TEACHER_STATE", 1);
        TEENAGER_STATE = sharedPrefer.getInt("TEENAGER_STATE", 1);
        WORKLAW_STATE = sharedPrefer.getInt("WORKLAW_STATE", 1);
        HUNJAEPAN_STATE = sharedPrefer.getInt("HUNJAEPAN_STATE", 1);
        COLLATERAL_STATE = sharedPrefer.getInt("COLLATERAL_STATE", 1);
        SELFCHECK_STATE = sharedPrefer.getInt("SELFCHECK_STATE", 1);
        PROMISSNOTE_STATE = sharedPrefer.getInt("PROMISSNOTE_STATE", 1);
        GAINTAX_STATE = sharedPrefer.getInt("GAINTAX_STATE", 1);
        HERITAGETAX_STATE = sharedPrefer.getInt("HERITAGETAX_STATE", 1);
        FIRMTAX_STATE = sharedPrefer.getInt("FIRMTAX_STATE", 1);
        VAT_STATE = sharedPrefer.getInt("VAT_STATE", 1);
        KOREAOFFICE_STATE = sharedPrefer.getInt("KOREAOFFICE_STATE", 1);
        TEACHOFFICE_STATE = sharedPrefer.getInt("TEACHOFFICE_STATE", 1);
        ENVIRONMENT_STATE = sharedPrefer.getInt("ENVIRONMENT_STATE", 1);
        TRAFFIC_STATE = sharedPrefer.getInt("TRAFFIC_STATE", 1);
        SECURITY_STATE = sharedPrefer.getInt("SECURITY_STATE", 1);


    }

    // ------------------------------------------------------------------------------

    // 광고모듈 초기화 ---------------------------------------------------------------
    private void initAD() {
        // 광고 스케줄링 설정을 위해 아래 내용을 프로그램 실행시 한번만 실행합니다. (처음 실행되는 activity에서 한번만
        // 호출해주세요.)
        // 광고 subview 의 패키지 경로를 설정합니다. (실제로 작성된 패키지 경로로 수정해주세요.)
        // 일반 Activity 에서는 AdlibManager 를 동적으로 생성한 후 아래 코드가 실행되어야 합니다.
        // (AdlibTestProjectActivity4.java)
        // 쓰지 않을 광고플랫폼은 삭제해주세요.

       // AdlibConfig.getInstance().bindPlatform("NAVER",
         //       "shufflebrother.mobile.searchthelaw.ads.SubAdlibAdViewNaverAdPost");
        AdlibConfig.getInstance().bindPlatform("ADAM", "shufflebrother.mobile.searchthelaw.ads.SubAdlibAdViewAdam");

        // 쓰지 않을 플랫폼은 JAR 파일 및 test.adlib.project.ads 경로에서 삭제하면 최종 바이너리 크기를 줄일 수
        // 있습니다.
        // SMART* dialog 노출 시점 선택시 / setAdlibKey 키가 호출되는 activity 가 시작 activity
        // 이며 해당 activity가 종료되면 app 종료로 인식합니다.
        // adlibr.com 에서 발급받은 api 키를 입력합니다.
        // https://sec.adlibr.com/admin/dashboard.jsp
    }

    // ---------------------------------------------------------------------------
    private void openFirstPage() {

        sharedPrefer = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String lastPage = sharedPrefer.getString("LASTPAGE_NEW", "MENU_HUNLAW");
        Log.d("SEJIN", " >>>>>>>>>>>>>>>   lastpage  >>>>  " + lastPage);
        switch (lastPage) {
            case "MENU_PATENT":
                onClickMenuItem(MenuState.MENU_PATENT);
                break;
            case "MENU_MINLAW":
                onClickMenuItem(MenuState.MENU_MINLAW);
                break;
            case "MENU_MINSONGLAW":
                onClickMenuItem(MenuState.MENU_MINSONGLAW);
                break;
            case "MENU_MINSA":
                onClickMenuItem(MenuState.MENU_MINSA);
                break;
            case "MENU_SINAN":
                onClickMenuItem(MenuState.MENU_SINAN);
                break;
            case "MENU_COPY":
                onClickMenuItem(MenuState.MENU_COPY);
                break;
            case "MENU_FIGHT":
                onClickMenuItem(MenuState.MENU_FIGHT);
                break;
            case "MENU_TM":
                onClickMenuItem(MenuState.MENU_TM);
                break;
            case "MENU_DESIGN":
                onClickMenuItem(MenuState.MENU_DESIGN);
                break;
            case "MENU_HUNLAW":
                onClickMenuItem(MenuState.MENU_HUNLAW);
                break;
            case "MENU_SANGLAW":
                onClickMenuItem(MenuState.MENU_SANGLAW);
                break;
            case "MENU_HYUNGLAW":
                onClickMenuItem(MenuState.MENU_HYUNGLAW);
                break;
            case "MENU_HYUNGSALAW":
                onClickMenuItem(MenuState.MENU_HYUNGSALAW);
                break;
            case "MENU_HANGSONG":
                onClickMenuItem(MenuState.MENU_HANGSONG);
                break;
            case "MENU_SCHOOLLAW":
                onClickMenuItem(MenuState.MENU_SCHOOLLAW);
                break;
            case "MENU_TEACHER":
                onClickMenuItem(MenuState.MENU_TEACHER);
                break;
            case "MENU_TEENAGER":
                onClickMenuItem(MenuState.MENU_TEENAGER);
                break;
            case "MENU_WORKLAW":
                onClickMenuItem(MenuState.MENU_WORKLAW);
                break;
            case "MENU_HUNJAEPAN":
                onClickMenuItem(MenuState.MENU_HUNJAEPAN);
                break;
            case "MENU_COLLATERAL":
                onClickMenuItem(MenuState.MENU_COLLATERAL);
                break;
            case "MENU_SELFCHECK":
                onClickMenuItem(MenuState.MENU_SELFCHECK);
                break;
            case "MENU_PROMISSNOTE":
                onClickMenuItem(MenuState.MENU_PROMISSNOTE);
                break;
            case "MENU_GAINTAX":
                onClickMenuItem(MenuState.MENU_GAINTAX);
                break;
            case "MENU_HERITAGETAX":
                onClickMenuItem(MenuState.MENU_HERITAGETAX);
                break;
            case "MENU_FIRMTAX":
                onClickMenuItem(MenuState.MENU_FIRMTAX);
                break;
            case "MENU_VAT":
                onClickMenuItem(MenuState.MENU_VAT);
                break;
            case "MENU_KOREAOFFICE":
                onClickMenuItem(MenuState.MENU_KOREAOFFICE);
                break;
            case "MENU_TEACHOFFICE":
                onClickMenuItem(MenuState.MENU_TEACHOFFICE);
                break;
            case "MENU_ENVIRONMENT":
                onClickMenuItem(MenuState.MENU_ENVIRONMENT);
                break;
            case "MENU_TRAFFIC":
                onClickMenuItem(MenuState.MENU_TRAFFIC);
                break;
            case "MENU_SECURITY":
                onClickMenuItem(MenuState.MENU_SECURITY);
                break;
            default:
                break;
        }

    }


    private void setFontSize() {

        sharedPrefer = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        fontSize = sharedPrefer.getInt("FONTSIZE", 16);
        ItemView.FontSize = fontSize;

    }

    @Override
    public void onClickMenuItem(MenuState menuType) {

        LawFragment lawFragment;
        String lastPage = "";
        String title = "";

        SharedPreferences.Editor saveLastPage = sharedPrefer.edit();

        transaction = getSupportFragmentManager().beginTransaction();
        lawFragment = LawFragment.newInstance(this);

        switch (menuType) {
            case MENU_PATENT:
                lawFragment.currentPage = MenuState.MENU_PATENT;
                title = "특허법";
                lastPage = "MENU_PATENT";

                break;

            case MENU_MINLAW:

                lawFragment.currentPage = MenuState.MENU_MINLAW;
                title = "민법";
                lastPage = "MENU_MINLAW";

                break;

            case MENU_MINSONGLAW:

                lawFragment.currentPage = MenuState.MENU_MINSONGLAW;
                title = "민사소송";
                lastPage = "MENU_MINSONGLAW";

                break;

            case MENU_MINSA:

                lawFragment.currentPage = MenuState.MENU_MINSA;
                title = "민사집행법";
                lastPage = "MENU_MINSA";

                break;

            case MENU_SINAN:

                lawFragment.currentPage = MenuState.MENU_SINAN;
                title = "실용신안";
                lastPage = "MENU_SINAN";

                break;

            case MENU_COPY:

                lawFragment.currentPage = MenuState.MENU_COPY;
                title = "저작권법";
                lastPage = "MENU_COPY";

                break;

            case MENU_FIGHT:

                lawFragment.currentPage = MenuState.MENU_FIGHT;
                title = "부정경쟁방지";
                lastPage = "MENU_FIGHT";

                break;

            case MENU_TM:

                lawFragment.currentPage = MenuState.MENU_TM;
                title = "상표법";
                lastPage = "MENU_TM";
                break;

            case MENU_DESIGN:

                lawFragment.currentPage = MenuState.MENU_DESIGN;
                title = "디자인보호법";
                lastPage = "MENU_DESIGN";
                break;

            case MENU_HUNLAW:

                lawFragment.currentPage = MenuState.MENU_HUNLAW;
                title = "헌법";
                lastPage = "MENU_HUNLAW";
/* Get tracker.
                Tracker t = ((MainApplication) getApplication()).getTracker(
                        MainApplication.TrackerName.APP_TRACKER);
// Build and send an Event.
                t.send(new HitBuilders.EventBuilder()
                        .setCategory("MENU_HUNLAW")
                        .setAction("Click Menu")
                        .setLabel("")
                        .build());*/
                break;

            case MENU_SANGLAW:

                lawFragment.currentPage = MenuState.MENU_SANGLAW;
                title = "상법";
                lastPage = "MENU_SANGLAW";
                break;

            case MENU_HYUNGLAW:

                lawFragment.currentPage = MenuState.MENU_HYUNGLAW;
                title = "형법";
                lastPage = "MENU_HYUNGLAW";
                break;

            case MENU_HYUNGSALAW:

                lawFragment.currentPage = MenuState.MENU_HYUNGSALAW;
                title = "형사소송";
                lastPage = "MENU_HYUNGSALAW";
                break;


            case MENU_HANGSONG:

                lawFragment.currentPage = MenuState.MENU_HANGSONG;
                title = "행정소송법";
                lastPage = "MENU_HANGSONG";
                break;

            case MENU_SCHOOLLAW:

                lawFragment.currentPage = MenuState.MENU_SCHOOLLAW;
                title = "학교폭력법";
                lastPage = "MENU_SCHOOLLAW";
                break;

            case MENU_TEACHER:

                lawFragment.currentPage = MenuState.MENU_TEACHER;
                title = "초중등교육법";
                lastPage = "MENU_TEACHER";
                break;

            case MENU_TEENAGER:

                lawFragment.currentPage = MenuState.MENU_TEENAGER;
                title = "청소년보호";
                lastPage = "MENU_TEENAGER";
                break;

            case MENU_WORKLAW:

                lawFragment.currentPage = MenuState.MENU_WORKLAW;
                title = "근로기준법";
                lastPage = "MENU_WORKLAW";
                break;

            case MENU_HUNJAEPAN:

                lawFragment.currentPage = MenuState.MENU_HUNJAEPAN;
                title = "헌법재판소법";
                lastPage = "MENU_HUNJAEPAN";
                break;

            case MENU_COLLATERAL:

                lawFragment.currentPage = MenuState.MENU_COLLATERAL;
                title = "가등기담보법";
                lastPage = "MENU_COLLATERAL";
                break;

            case MENU_SELFCHECK:

                lawFragment.currentPage = MenuState.MENU_SELFCHECK;
                title = "수표법";
                lastPage = "MENU_SELFCHECK";
                break;

            case MENU_PROMISSNOTE:

                lawFragment.currentPage = MenuState.MENU_PROMISSNOTE;
                title = "어음법";
                lastPage = "MENU_PROMISSNOTE";
                break;

            case MENU_GAINTAX:

                lawFragment.currentPage = MenuState.MENU_GAINTAX;
                title = "소득세법";
                lastPage = "MENU_GAINTAX";
                break;

            case MENU_HERITAGETAX:

                lawFragment.currentPage = MenuState.MENU_HERITAGETAX;
                title = "상속세법";
                lastPage = "MENU_HERITAGETAX";
                break;

            case MENU_FIRMTAX:

                lawFragment.currentPage = MenuState.MENU_FIRMTAX;
                title = "법인세법";
                lastPage = "MENU_FIRMTAX";
                break;

            case MENU_VAT:

                lawFragment.currentPage = MenuState.MENU_VAT;
                title = "부가가치세법";
                lastPage = "MENU_VAT";
                break;

            case MENU_KOREAOFFICE:

                lawFragment.currentPage = MenuState.MENU_KOREAOFFICE;
                title = "국가공무원법";
                lastPage = "MENU_KOREAOFFICE";
                break;

            case MENU_TEACHOFFICE:

                lawFragment.currentPage = MenuState.MENU_TEACHOFFICE;
                title = "교육공무원법";
                lastPage = "MENU_TEACHOFFICE";
                break;

            case MENU_ENVIRONMENT:

                lawFragment.currentPage = MenuState.MENU_ENVIRONMENT;
                title = "자연환경보전법";
                lastPage = "MENU_ENVIRONMENT";
                break;

            case MENU_TRAFFIC:

                lawFragment.currentPage = MenuState.MENU_TRAFFIC;
                title = "도로교통법";
                lastPage = "MENU_TRAFFIC";
                break;

            case MENU_SECURITY:

                lawFragment.currentPage = MenuState.MENU_SECURITY;
                title = "정보보호법";
                lastPage = "MENU_SECURITY";
                break;
        }

        transaction.replace(R.id.content, lawFragment);
        transaction.commit();

        mFontChangeListener = (OnChangeFontListener) lawFragment;

        actionbarTitle.setText(title);
        saveLastPage.putString("LASTPAGE_NEW", lastPage);
        saveLastPage.commit();

        drawer.closeDrawer(GravityCompat.START); // 슬라이드 메뉴 접기
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        int page = 0;

        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_UP:

                SharedPreferences.Editor saveFont1 = sharedPrefer.edit();
                int newFontSize = fontSize + 1;
                saveFont1.putInt("FONTSIZE", newFontSize);
                saveFont1.commit();
                setFontSize();
                if (mFontChangeListener != null) {
                    mFontChangeListener.changeFont(newFontSize);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:

                SharedPreferences.Editor saveFont2 = sharedPrefer.edit();
                int newFontSize2 = fontSize - 1;
                saveFont2.putInt("FONTSIZE", newFontSize2);
                saveFont2.commit();
                setFontSize();
                if (mFontChangeListener != null) {
                    mFontChangeListener.changeFont(newFontSize2);
                }

                break;
            case KeyEvent.KEYCODE_BACK:

                // 메뉴가 열려있으면 메뉴 닫기
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                else {
                    if (mIsBackKeyPressed == false) {
                        mIsBackKeyPressed = true;
                        mCurrTimeInMillis = Calendar.getInstance().getTimeInMillis();
                        Toast.makeText(this, "종료를 원하시면 다시 한번 \"Back key\"를 눌러 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        startTimer();
                    } else {
                        mIsBackKeyPressed = false;

                        if (Calendar.getInstance().getTimeInMillis() <= (mCurrTimeInMillis + (BACKKEY_TIMEOUT * MILLIS_IN_SEC)))
                            _amanager.loadFullInterstitialAd(this);
                            finish();
                    }
                }
                break;
        }

        return true;
    }

    // 타이머 관련 --------------------------------------------
    private void startTimer() {
        mTimerHandler.sendEmptyMessageDelayed(MSG_TIMER_EXPIRED, BACKKEY_TIMEOUT * MILLIS_IN_SEC);
    }

    private final Handler mTimerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_TIMER_EXPIRED: {
                    mIsBackKeyPressed = false;
                }
                break;
            }
        }
    };


}