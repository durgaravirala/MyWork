package com.programs.practice;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class MostRepeatedString {
	
	public static void main(String[] s) {
		MostRepeatedString mostRepeatedString = new MostRepeatedString();
		mostRepeatedString.repeatedString();
	}
	public void repeatedString() {
	String s= "I am am Bhavani Kandukuri kandu my kids love me I love my kids I have two kids";
	int count = 0;
	String mostRepeatedWord = "";
	
	String[] wordsList = s.split(" ");	
	HashMap<String, Integer> stringMap = new HashMap<String, Integer> ();
	
	
	for(int i=0; i<wordsList.length; i++) {
		
		if(stringMap.containsKey(wordsList[i])) {
			stringMap.put(wordsList[i], stringMap.get(wordsList[i])+1);
		} else stringMap.put(wordsList[i], 1);				
	}
	
	Set<Entry<String, Integer>> entrySet = stringMap.entrySet();
	
	
	for(Entry<String, Integer> word : entrySet) {
		if(count<word.getValue())
			count = word.getValue();
		mostRepeatedWord = word.getKey();
	}
	System.out.println("word " + mostRepeatedWord + " repeated for " +count + " times");

}
}
