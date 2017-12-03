package cm.gasmyr.it.app.music.util;

public abstract class ObjectBuilder<T> {

	protected T instance;

	public ObjectBuilder() {
		this(null);
	}

	public ObjectBuilder(T instance) {
		super();
		if (instance == null) {
			this.instance = this.newInstance();
		} else {
			this.instance = instance;
		}
	}

	protected abstract T newInstance();

	public T build() {
		return instance;
	}

}
