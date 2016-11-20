package shufflebrother.mobile.searchthelaw.entity;

public class Law {

	private String title;
	private String content;
	
	public Law(String title, String content) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.content = content;
	 
	}
    public Law() {


    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
