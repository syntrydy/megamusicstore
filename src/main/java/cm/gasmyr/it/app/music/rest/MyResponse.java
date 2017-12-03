package cm.gasmyr.it.app.music.rest;

public class MyResponse {
	private boolean status;
	private Object data;

	public MyResponse() {

	}

	public MyResponse(boolean status, Object data) {
		this.status = status;
		this.data = data;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
