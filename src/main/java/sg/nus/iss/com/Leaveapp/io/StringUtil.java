package sg.nus.iss.com.Leaveapp.io;

public class StringUtil {
	
	public static String toProper(String s) {
		if(s.length() <= 1) {
			return s.toUpperCase();
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
