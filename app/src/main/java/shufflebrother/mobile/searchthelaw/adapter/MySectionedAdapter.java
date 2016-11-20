package shufflebrother.mobile.searchthelaw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shufflebrother.mobile.searchthelaw.R;
import shufflebrother.mobile.searchthelaw.customview.ItemView;
import shufflebrother.mobile.searchthelaw.entity.Law;
import shufflebrother.mobile.searchthelaw.entity.LawHeader;


public class MySectionedAdapter extends SectionedBaseAdapter {


    private final List<LawHeader> LawHeaderList = new ArrayList<LawHeader>();

    Context mContext;

    public MySectionedAdapter(Context context) {
        mContext = context;
    }


    public void add_chunk(Law it, int headerIdx) {

        LawHeaderList.get(headerIdx).getLawList().add(it);

    }

    public void add_header(LawHeader it) {

        LawHeaderList.add(it);
    }


    public void remove_chunk(int position) {
        LawHeaderList.get(0).getLawList().remove(position);

    }


    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return LawHeaderList.size();
    }   // 헤더

    @Override
    public int getCountForSection(int section) {
        return LawHeaderList.get(section).getLawList().size();
    }  // 내용

    public int calcHeaderCnt(int position) {
        int result = 0;
        if (LawHeaderList != null) {
            int totalSize = 0;
            for (LawHeader lawHeader : LawHeaderList) {
                int size = lawHeader.getLawList().size();
                totalSize += size;
                if (totalSize >= position) {
                    break;
                } else {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {

        ItemView layout;

        if (convertView == null) {
            layout = new ItemView(mContext.getApplicationContext(), LawHeaderList.get(section).getLawList().get(position));
        } else {
            layout = (ItemView) convertView;
            layout.setTitle(LawHeaderList.get(section).getLawList().get(position).getTitle());
            layout.setContent(LawHeaderList.get(section).getLawList().get(position).getContent());
        }


        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(  (LawHeaderList.get(section).getHeadTitle()).trim());

        return layout;
    }

}
