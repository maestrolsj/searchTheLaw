package shufflebrother.mobile.searchthelaw.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.entity.Law;

public class ItemView extends LinearLayout {

    public static int FontSize;
    public static String FindingWord;
    TextView content, title;

    public ItemView(Context context, Law it) {
        super(context);

        init(context);
        setTitle(it.getTitle());
        setContent(it.getContent());

    }

    private void init(Context context) {

        // 안드로이드  OS에서 서비스로 돌고 있는 객체에서 레이아웃 인플레이터 객체
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.listitem, this, true); // listitem을 여기에 보여줘라

        title   = (TextView) findViewById(R.id.TextView_title)  ;
        content = (TextView) findViewById(R.id.TextView_content);

    }

    public void setContent(String data) {

        content.setTextSize(FontSize);

        if (FindingWord.length() > 1) {

            int startIdx = data.indexOf(FindingWord);
            int endIdx   = startIdx + FindingWord.length();
            Spannable WordtoSpan = new SpannableString(data);
            while (startIdx >= 0) {
                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED),
                        startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIdx = data.indexOf(FindingWord, startIdx + 1);
                endIdx = startIdx + FindingWord.length();
            }

            content.setText(WordtoSpan);

        } else
            content.setText(data);
    }

    public void setTitle(String data) {

        title.setTextSize(FontSize);

        if (FindingWord.length() > 1) {

            int start_pos = data.indexOf(FindingWord);
            int end_pos = start_pos + FindingWord.length();

            if (start_pos < 1) {
                start_pos = 0;
                end_pos = 0;
            }
            Log.e("data = ", data);
            Log.e("start_pos = ",
                    FindingWord + "  " + Integer.toString(start_pos) + " ~ " + Integer.toString(end_pos));
            Spannable WordtoSpan = new SpannableString(data);
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), start_pos, end_pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            title.setText(WordtoSpan);

        } else
            title.setText(data);
    }
}
