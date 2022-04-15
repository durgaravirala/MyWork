package com.programs.practice;

public class IntegerPalindrome {

	public void verifyGivenInteger(int n) {
		
		int r, sum=0,temp;
		temp =n;
		
		while(n>0) {
			r = n%10;
			sum = sum*10+r;
			n=n/10;
		}
		if(temp==sum)
		System.out.println("given number is a palindrome");
		else System.out.println("given number is not a palindrome");
	
	}
	
	public static void main(String s[]) {
		IntegerPalindrome integerPalindrome = new IntegerPalindrome();
		integerPalindrome.verifyGivenInteger(454);
	}
	
}
