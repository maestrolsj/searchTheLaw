package shufflebrother.mobile.searchthelaw.entity;

import java.util.ArrayList;
import java.util.List;

public class LawHeader {

	private String headTitle;
    private List<Law> list = new ArrayList<Law>();

    public LawHeader(String headTitle, List<Law> list) {
        this.headTitle = headTitle;
        this.list = list;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public List<Law> getLawList() {
        return list;
    }

    public void setLawList(List<Law> list) {
        this.list = list;
    }
}
