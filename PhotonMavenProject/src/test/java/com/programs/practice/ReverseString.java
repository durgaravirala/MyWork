package com.programs.practice;

public class ReverseString {

	public boolean reverseGivenString(String s) {
		boolean result = false;
		String reversedString = "";
		for(int i = 0; i<s.length(); i++) {
			reversedString = s.charAt(i)+reversedString;
		}
		System.out.println("Reversed String is " + reversedString);
		if(s.equals(reversedString)) {
			result = true;
		}	else {
			result = false;		
      System.out.println("given string is not a palindrome string");
		}
		return result ;
	}
	
	public static void main(String s[]) {
		ReverseString reverseString = new ReverseString();
		reverseString.reverseGivenString("Bhanu");
	}
	
}
