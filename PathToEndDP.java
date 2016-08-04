package Algorithms;

import java.util.HashMap;
import java.util.Map;

import Algorithms.Path2End.Retangule;

// 1032am 
public class PathToEndDP {

	static class Retangule{
		final int longEdge;
		final int shortEdge;
		
		Retangule(int s1, int s2) {
			longEdge = s1 > s2 ? s1 : s2;
			shortEdge = s1 < s2 ? s1 : s2;
		}
	}
	
	static Map<Retangule, Integer> map = new HashMap<Retangule, Integer>(); 
	
	
	// Solution 1: 
	public static int pathToEndDP(int row, int col){
		int r = row > col ? row : col;
		int c = row < col ? row : col;
			
		if(c == 3) 
			return (1 + r) * r / 2;
		Retangule ret = new Retangule(r, c);
		if(map.containsKey(ret))
			return map.get(ret);
		
		int pathCount = 1 + (c - 1) + c * (c - 1) / 2;
		for(int i = 4; i <= r; i++) {
			ret = new Retangule(i, c - 1);
			if(map.containsKey(ret))
				pathCount += map.get(ret);
			else {
				int tmpCount = pathToEndDP(i, c - 1);
				map.put(ret, tmpCount);
				pathCount += tmpCount;
			}
		}
		
		return pathCount;
	}
	
	
	// Same solution, 
	public static int path2EndDP(int row, int column) {
		int r = row > column ? row : column;
		int c = row < column ? row : column;
		
		if(c == 1)
			return 1;
		if(c == 2)
			return row;
		if(c == 3)
			return (row + 1) * row / 2;
		
		int pathCount = 0;
		pathCount += 1 + (c - 1) + (c - 1) * c / 2;
		for(int i = 4; i <= r; i++)
			pathCount += pathHelper(i, c - 1);
		return pathCount;
	}
	
	private static int pathHelper(int x1, int x2) {
		int longEdge = x1 > x2 ? x1 : x2;
		int shortEdge = x1 < x2 ? x1 : x2;	
		
		if(shortEdge == 3)
			return (1 + longEdge) * longEdge / 2;
		
		int pathCount = 1 + (shortEdge - 1) + (shortEdge - 1) * shortEdge / 2;
		Retangule ret = new Retangule(longEdge, shortEdge);
		if(map.containsKey(ret)) {
			return map.get(ret);
		} else {
			for(int i = 4; i <= longEdge; i++) {
				pathCount += pathHelper(i, shortEdge - 1);
			}
			map.put(ret, pathCount);
			return pathCount;
		}		
	}
	
	public static void main(String[] args) {
		long tim;
		int pathCount = 0;
		
		tim = System.nanoTime();
		//pathCount = pathToEndDP(50,7);
		tim = System.nanoTime() - tim;
		System.out.println(pathCount + " , time consume is: " + tim / 1000000);
		
		tim = System.nanoTime();
		pathCount = path2EndDP(50,7);
		tim = System.nanoTime() - tim;
		System.out.println(pathCount + " , time consume is: " + tim / 1000000);
		
		
		/* Issue 1: both recursive realization, don't know why pathToEndDP() is 10times slower than path2EndDP()
		 * Issue 2: when input is larger(50,10), we get OutOfMemoryError. Which means we get to find redundancy in the program 
		 * to tackle memeory problem. 
		 */
	}

}
