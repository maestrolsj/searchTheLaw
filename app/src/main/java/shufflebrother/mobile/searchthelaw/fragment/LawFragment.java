package shufflebrother.mobile.searchthelaw.fragment;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.activity.MainActivity;
import shufflebrother.mobile.searchthelaw.activity.MainActivity.OnChangeFontListener;
import shufflebrother.mobile.searchthelaw.adapter.MyAdapter;
import shufflebrother.mobile.searchthelaw.adapter.MySectionedAdapter;
import shufflebrother.mobile.searchthelaw.customview.ItemView;
import shufflebrother.mobile.searchthelaw.customview.PinnedHeaderListView;
import shufflebrother.mobile.searchthelaw.entity.Law;
import shufflebrother.mobile.searchthelaw.entity.LawHeader;
import shufflebrother.mobile.searchthelaw.enums.MenuState;


public class LawFragment extends Fragment implements OnChangeFontListener {

    boolean scroll_move_flag = false;
    boolean find_word_flag   = false;
    int     value            = 0;     // 조항

    // 단어검색 레이어 애니메이션
    Animation moveUpAnim;
    Animation moveDownAnim;
    boolean visibleFlag = false;
    public LinearLayout Word_search_layout;
    public ProgressWheel progressWheel;

    //-------------------------

    BackgroundTask     task;
    MySectionedAdapter sectionedAdapter;
    private ViewHolder mViewHolder;
    private List<Law>  lawList;
    private String     lawTextFile;
    public MenuState currentPage;
    private String MAX_NUMBER;

    public LawFragment() {    }

    public static LawFragment newInstance(MainActivity activity) {
        LawFragment fragment = new LawFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 프래그먼스 뷰 초기화
        View rootView = inflater.inflate(R.layout.law, container, false);
        
        switch(currentPage)
        {
        	case MENU_PATENT :     lawTextFile = "patent.txt";   MAX_NUMBER="232";   break;
    		case MENU_MINLAW:      lawTextFile = "minlaw.txt";   MAX_NUMBER="1118";   break;
    		case MENU_MINSONGLAW:  lawTextFile = "minsong.txt";  MAX_NUMBER="502";   break;
            case MENU_MINSA   :  lawTextFile = "minsa.txt";      MAX_NUMBER="312";   break;
    		case MENU_SINAN:       lawTextFile = "sinan.txt";    MAX_NUMBER="52";   break;
    		case MENU_COPY:        lawTextFile = "copyright.txt";  MAX_NUMBER="142";   break;
    		case MENU_FIGHT:       lawTextFile = "fight.txt";      MAX_NUMBER="20";   break;
    		case MENU_TM:          lawTextFile = "tm.txt";         MAX_NUMBER="237";   break;
    		case MENU_DESIGN:      lawTextFile = "design.txt";     MAX_NUMBER="229";   break;
        	case MENU_HUNLAW:      lawTextFile = "hunlaw.txt";      MAX_NUMBER="130";   break;
    		case MENU_SANGLAW:     lawTextFile = "sanglaw.txt";   MAX_NUMBER="935";   break;
    		case MENU_HYUNGLAW:    lawTextFile = "hyunglaw.txt";  MAX_NUMBER="372";   break;
    		case MENU_HYUNGSALAW:  lawTextFile = "hyungsalaw.txt"; MAX_NUMBER="493";   break;
            case MENU_HANGSONG:    lawTextFile = "hangsong.txt"; MAX_NUMBER="46";   break;
    		case MENU_SCHOOLLAW:   lawTextFile = "school.txt";    MAX_NUMBER="22";   break;
            case MENU_TEACHER:     lawTextFile = "teacher.txt";    MAX_NUMBER="68";   break;
            case MENU_TEENAGER:    lawTextFile = "teenager.txt";   MAX_NUMBER="64";   break;
            case MENU_WORKLAW:     lawTextFile = "worklaw.txt";    MAX_NUMBER="116";   break;
            case MENU_HUNJAEPAN:   lawTextFile = "hunjaepan.txt";     MAX_NUMBER="79";   break;
            case MENU_COLLATERAL:   lawTextFile = "collateral.txt";     MAX_NUMBER="18";   break;
            case MENU_SELFCHECK:   lawTextFile = "selfcheck.txt";     MAX_NUMBER="62";   break;
            case MENU_PROMISSNOTE:   lawTextFile = "promissnote.txt";    MAX_NUMBER="78";   break;
            case MENU_GAINTAX:   lawTextFile = "gaintax.txt";    MAX_NUMBER="175";   break;
            case MENU_HERITAGETAX:   lawTextFile = "heritagetax.txt";     MAX_NUMBER="86";   break;
            case MENU_FIRMTAX:   lawTextFile = "firmtax.txt";    MAX_NUMBER="122";   break;
            case MENU_VAT:           lawTextFile = "vat.txt";      MAX_NUMBER="74";   break;
            case MENU_KOREAOFFICE:   lawTextFile = "koreaoffice.txt";   MAX_NUMBER="85";   break;
            case MENU_TEACHOFFICE:   lawTextFile = "teachoffice.txt";     MAX_NUMBER="63";   break;
            case MENU_ENVIRONMENT:   lawTextFile = "environment.txt";      MAX_NUMBER="66";   break;
            case MENU_TRAFFIC:   lawTextFile = "traffic.txt";      MAX_NUMBER="166";   break;
            case MENU_SECURITY:   lawTextFile = "security.txt";      MAX_NUMBER="76";   break;
        }
        
 
        mViewHolder = new ViewHolder(rootView);
        task = new BackgroundTask();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else task.execute();

       
        return rootView;
    }

  
    private class ViewHolder {
        public ViewHolder(View rootView) {
            // 메인 액티비티 호출해서 뭔가 바꾸고자 할때
            if (Law_ListView == null) {
                Law_ListView = (PinnedHeaderListView) rootView.findViewById(R.id.Law_ListView);
                /*
                Law_ListView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent ev) {
                        switch (ev.getAction()) {
                            case MotionEvent.ACTION_MOVE:       //터치를 한 후 움직이고 있으면
                                dragFlag = true;
                                if (firstDragFlag) {     //터치후 계속 드래그 하고 있다면 ACTION_MOVE가 계속 일어날 것임으로 무브를 시작한 첫번째 터치만 값을 저장함
                                    startYPosition = ev.getY(); //첫번째 터치의 Y(높이)를 저장
                                    firstDragFlag = false;   //두번째 MOVE가 실행되지 못하도록 플래그 변경
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                endYPosition = ev.getY();
                                firstDragFlag = true;
                                if (dragFlag) {  //드래그를 하다가 터치를 실행
                                    // 시작Y가 끝 Y보다 크다면 터치가 아래서 위로 이루어졌다는 것이고, 스크롤은 아래로내려갔다는 뜻이다.
                                    // (startYPosition - endYPosition) > 10 은 터치로 이동한 거리가 10픽셀 이상은 이동해야 스크롤 이동으로 감지하겠다는 뜻임으로 필요하지 않으면 제거해도 된다.
                                    if ((startYPosition > endYPosition) && (startYPosition - endYPosition) > 10) {
                                        //TODO 스크롤 다운 시 작업

                                        if(visibleFlag == true) {
                                            Word_search_layout.startAnimation(moveUpAnim);
                                            visibleFlag = false;
                                        }

                                    }
                                    //시작 Y가 끝 보다 작다면 터치가 위에서 아래로 이러우졌다는 것이고, 스크롤이 올라갔다는 뜻이다.
                                    else if ((startYPosition < endYPosition) && (endYPosition - startYPosition) > 10) {
                                        //TODO 스크롤 업 시 작업

                                        if(visibleFlag == false) {
                                            Word_search_layout.startAnimation(moveDownAnim);
                                            visibleFlag = true;

                                        }
                                    }
                                }
                                startYPosition = 0.0f;
                                endYPosition = 0.0f;
                                dragFlag = false;
                                break;
                        }
                        return false;
                    }
                });
              */

            }
            if (Law_EditText_Search == null) {
                Law_EditText_Search = (EditText) rootView.findViewById(R.id.Law_EditText_Search);
                Law_EditText_Search.setHint("총 "+MAX_NUMBER+ " 조 / 0조항 검색시 전체보기");
                Law_EditText_Search.setOnEditorActionListener(new OnEditorActionListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (Law_EditText_Search.getText().toString().equals("")) {

                            Toast.makeText(getActivity(), "조항을 입력하세요", Toast.LENGTH_SHORT).show();
                        } else {

                            // 검색한 조항
                            value = Integer.parseInt(Law_EditText_Search.getText().toString());

                            // 이전에 단어 검색을 했거나, 전체 조항 검색이면 법률 전체 출력
                            if (find_word_flag == true || value == 0) {

                                find_word_flag = false; // 단어 검색 취소  조항검색으로 전환
                                task = new BackgroundTask();
                                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
                                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                }
                                else task.execute();

                                if (value != 0)
                                    scroll_move_flag = true;
                            } else {
                                int headerCnt = ((MySectionedAdapter) Law_ListView.getAdapter()).calcHeaderCnt(value);
                                Law_ListView.setSelectionFromTop(value + headerCnt, 60);
                            }

                            Law_EditText_Search.setText("");

                        }

                        return true;
                    }
                });

            }

            if (Law_EditText_WordSearch == null) {
                Law_EditText_WordSearch = (EditText) rootView.findViewById(R.id.Law_EditText_WordSearch);
                Law_EditText_WordSearch.setOnEditorActionListener(new OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (Law_EditText_WordSearch.getText().toString().length() < 2) {

                            Toast.makeText(getActivity(), "두글자 이상 입력하세요", Toast.LENGTH_SHORT).show();
                        } else {

                            find_word_flag = true;
                            task = new BackgroundTask();
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
                                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }
                            else task.execute();
                        }
                        return true;
                    }
                });
            }
            if (sectionedAdapter == null) {
                sectionedAdapter = new MySectionedAdapter(getActivity());
            }

            if(Word_search_layout == null){

                Word_search_layout = (LinearLayout) rootView.findViewById(R.id.word_search_layout);
                moveDownAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom);
                moveUpAnim   = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_out_bottom);

                SlidingPageAnimationListener animationListener = new SlidingPageAnimationListener();
                moveDownAnim.setAnimationListener(animationListener);
                moveUpAnim.setAnimationListener(animationListener);
            }

            if(progressWheel == null) {
                progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
            }
        }



        public PinnedHeaderListView Law_ListView;
        public EditText Law_EditText_Search;
        public EditText Law_EditText_WordSearch;


        boolean firstDragFlag = true;
        boolean dragFlag = false;   //현재 터치가 드래그 인지 확인
        float startYPosition = 0;       //터치이벤트의 시작점의 Y(세로)위치
        float endYPosition = 0;       //터치이벤트의 시작점의 Y(세로)위치
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sectionedAdapter != null)
            sectionedAdapter.notifyDataSetChanged();

    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }
        @Override
        public void onAnimationEnd(Animation animation){

            if(visibleFlag) Word_search_layout.setVisibility(getView().VISIBLE);
            else Word_search_layout.setVisibility(getView().INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class BackgroundTask extends AsyncTask<String, MyAdapter, String> {


        StringBuilder sb_title;
        StringBuilder sb_content;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressWheel.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... arg0) {


            // context를 넘겨서 엑티비티 정보 전달
            sectionedAdapter = new MySectionedAdapter(getActivity()); // 여기가 매우 안좋은 구조임
            String read;

            try {
                InputStream is = getActivity().getAssets().open(lawTextFile);
                InputStreamReader mReader;

                mReader = new InputStreamReader(is, "utf-8");
                BufferedReader mBuffer = new BufferedReader(mReader);

                String find_word     = ""; // 찾을단어
                ItemView.FindingWord = "";

                if (find_word_flag) {  //찾을단어가 있을경우
                    find_word = mViewHolder.Law_EditText_WordSearch.getText().toString();
                    ItemView.FindingWord = find_word;
                }


                sb_title   = new StringBuilder("");
                sb_content = new StringBuilder("");

                int headerIdx = -1;
                int last = 0;
                while ((read = mBuffer.readLine()) != null) 
                {
                    // # 을 발견하면 헤더로 추가
                    if (read.indexOf("#") > -1) {

                        lawList = new ArrayList<Law>();
                        sectionedAdapter.add_header(new LawHeader(read, lawList));
                        if (headerIdx > -1) last = 1;

                        headerIdx++;

                    }

                    if (read.indexOf("* 제") > -1) {

                        if (find_word_flag) { // 단어검색일 경우

                            if (sb_title.indexOf(find_word) > -1 || sb_content.indexOf(find_word) > -1) {   // 찾는 단어가 있을때만 어댑터에 추가
                                if (last == 1) {
                                    sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx - 1);
                                    last = 0;
                                } else sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx);
                            }

                        } else {  // 단어검색이 아닐경우( 모든 조항 스캔 )
                            if (last == 1) {
                                sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx - 1);
                                last = 0;
                            } else sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx);
                        }

                        sb_title   = new StringBuilder("");
                        sb_content = new StringBuilder("");
                        sb_title.append(read);

                    } else if (read.indexOf("#") < 0) {

                        sb_content.append(read);
                        sb_content.append("\n\n");

                    }
                }

                if (find_word_flag) { // 단어검색일 경우

                    if (sb_title.indexOf(find_word) > -1 || sb_content.indexOf(find_word) > -1) {   // 찾는 단어가 있을때만 어댑터에 추가
                        sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx);
                    }

                } else {  // 단어검색이 아닐경우( 모든 조항 스캔 )
                    sectionedAdapter.add_chunk(new Law(sb_title.toString(), sb_content.toString()), headerIdx);  // 전부다 어댑터에 추가
                }


                if (find_word_flag) ;                     // 단어검색일 경우
                else sectionedAdapter.remove_chunk(0);  // 단어검색이 아닐경우


            } catch (IOException e) {

                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(MyAdapter... values) {

            mViewHolder.Law_ListView.setAdapter(values[0]);

            values[0].notifyDataSetChanged();

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String result) {

            mViewHolder.Law_ListView.setAdapter(sectionedAdapter);
            sectionedAdapter.notifyDataSetChanged();

            mViewHolder.Law_EditText_WordSearch.setText("");
            if (scroll_move_flag) {
                int headerCnt = ((MySectionedAdapter) mViewHolder.Law_ListView.getAdapter()).calcHeaderCnt(value);
                mViewHolder.Law_ListView.setSelectionFromTop(value + headerCnt, 60);
                scroll_move_flag = false;
            }

          progressWheel.setVisibility(View.GONE);

        }

    }

    @Override
    public void changeFont(int fontSize) {
        if (sectionedAdapter != null) {
            sectionedAdapter.notifyDataSetChanged();
        }
    }

}
