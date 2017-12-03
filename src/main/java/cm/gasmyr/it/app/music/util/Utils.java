package cm.gasmyr.it.app.music.util;

import java.security.Principal;
import java.util.List;
import java.util.function.Predicate;

public class Utils {

	static Predicate<Object> isValid = e -> e != "" || e != null;

	public static String getUserName(Principal principal) {
		return principal.getName().split("\\@")[0].toUpperCase();
	}

	public static boolean canSave(List<Object> values) {
		return values.stream().allMatch(isValid);
	}

}
