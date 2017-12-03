package cm.gasmyr.it.app.music.mail;

public class Mail {

	public static class Builder {

		private Mail mail;

		private Builder() {
			super();
		}

		private Builder(Mail instance) {
			mail = instance;
		}

		public Builder to(String to) {
			this.mail.to = to;
			return this;
		}

		public Builder from(String from) {
			this.mail.from = from;
			return this;
		}

		public Builder withSubject(String subject) {
			this.mail.subject = subject;
			return this;
		}

		public Builder withBody(String body) {
			this.mail.content = body;
			return this;
		}

		public Mail build() {
			return this.mail;
		}

	}

	public static Builder builder() {
		return new Builder(new Mail());
	}

	private String to;
	private String from;
	private String content;
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContext(String context) {
		this.content = context;
	}

	public String getTo() {
		return to;
	}

	public Mail setTo(String to) {
		this.to = to;
		return this;
	}

	public String getFrom() {
		return from;
	}

	public Mail setFrom(String from) {
		this.from = from;
		return this;
	}
}
