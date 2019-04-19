package com.facialrecognition.detectfaces;

public class Viginer {
	public static void main(String[] args) {
		char[][] chars= new char[26][26];
		char c='a';
		int indexer=0;
		System.out.println(Character.toString((char)(c+1)));
		for (char[] a : chars) {
			for (char b :a ) {
				b=(char)(c+indexer);
				indexer++;
				System.out.print(b);
			}
			System.out.println();
			
		}
		
		
	}

}
