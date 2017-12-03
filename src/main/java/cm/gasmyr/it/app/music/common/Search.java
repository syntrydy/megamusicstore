package cm.gasmyr.it.app.music.common;

public class Search {
	private String text = "";

	public String getText() {
		return text.toUpperCase();
	}

	public void setText(String text) {
		this.text = text;
	}
}
