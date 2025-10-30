public class StringAnagram {
	/**
	 * Checks if two strings are anagrams (case-insensitive, ignores spaces).
	 * @param s1 First string
	 * @param s2 Second string
	 * @return true if anagrams, false otherwise
	 */
	public static boolean areAnagrams(String s1, String s2) {
		if (s1 == null || s2 == null) return false;
		String str1 = s1.replaceAll("\\s", "").toLowerCase();
		String str2 = s2.replaceAll("\\s", "").toLowerCase();
		if (str1.length() != str2.length()) return false;
		char[] arr1 = str1.toCharArray();
		char[] arr2 = str2.toCharArray();
		java.util.Arrays.sort(arr1);
		java.util.Arrays.sort(arr2);
		return java.util.Arrays.equals(arr1, arr2);
	}

    public static void main(String[] args) {
    String s1 = "Listen";
    String s2 = "Silent";
    boolean result = areAnagrams(s1, s2);
    System.out.println("Are '" + s1 + "' and '" + s2 + "' anagrams? " + result);
}
	// ...existing code...
}




