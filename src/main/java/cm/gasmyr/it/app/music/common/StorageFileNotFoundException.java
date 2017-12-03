package cm.gasmyr.it.app.music.common;

public class StorageFileNotFoundException extends StorageException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4083169752814822846L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
