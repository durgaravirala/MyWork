package SamplePhoton.PhotonMavenProject;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class FizzBuzz {
	
	@Test
	public void validateInput() {
	
	List<String> inputValues = new ArrayList<String>();
	inputValues.add("1");	
	inputValues.add("3");
	inputValues.add("5");
	inputValues.add("");
	inputValues.add("15");
	inputValues.add("A");
	inputValues.add("23");
	
	fizzBuzz(inputValues);	
	
	}
	
	public void fizzBuzz(List list) {
		int n =0;
		
		for(int i=0;i<list.size(); i++) {
			try {
			 n =  Integer.parseInt(list.get(i).toString());			
			if(n%3 == 0) {
				if(n%5 ==0) {
					System.out.println("Input: " + n + " - Output: " + "FizzBuzz");
				}
				else System.out.println("Input: " + n + " - Output: " + "Fizz");
			} else if(n%5 == 0) {
				System.out.println("Input: " + n + " - Output: " + "Buzz");
			} else {
				System.out.println("Input: " + n + " - Output: Divided " +n + " by 3");
				System.out.println("Input: " + n + " - Output: Divided " +n + " by 5");
			} 
			
			} catch(NumberFormatException nfe) {
				System.out.println("Invalid Item");
		} 
				
		}			
		
	}

}
