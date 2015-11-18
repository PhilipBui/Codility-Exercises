/**
 * A binary gap within a positive integer N is any maximal sequence of
 * consecutive zeros that is surrounded by ones at both ends in the binary
 * representation of N.
 * 
 * For example, number 9 has binary representation 1001 and contains a binary
 * gap of length 2. The number 529 has binary representation 1000010001 and
 * contains two binary gaps: one of length 4 and one of length 3. The number 20
 * has binary representation 10100 and contains one binary gap of length 1. The
 * number 15 has binary representation 1111 and has no binary gaps.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int N); }
 * 
 * that, given a positive integer N, returns the length of its longest binary
 * gap. The function should return 0 if N doesn't contain a binary gap.
 * 
 * For example, given N = 1041 the function should return 5, because N has
 * binary representation 10000010001 and so its longest binary gap is of length
 * 5.
 * 
 * Assume that:
 * 
 * N is an integer within the range [1..2,147,483,647]. Complexity:
 * 
 * expected worst-case time complexity is O(log(N)); expected worst-case space
 * complexity is O(1).
 * 
 * @author Philip
 *
 */
public class BinaryGap {
	public int solution(int N) {
		String s = Integer.toBinaryString(N);
		s = s.replaceAll("1+", "1");
		if (s.length() >= 0 && s.length() <= 2) {
			// Impossible to have a binary gap
			return 0;
		}
		String[] sArray = s.split("1");
		int maxZeros = 0;
		int size = sArray.length;
		if (s.substring(s.length() - 1, s.length()).equals("0")) {
			// Don't read last 0's if String doesn't end in 1
			size--;
		}
		// Don't read first index because it'll be 0's before first 1 or empty
		for (int i = 1; i < size; i++) {
			if (sArray[i].length() > maxZeros) {
				maxZeros = sArray[i].length();
			}
		}
		return maxZeros;
	}
}
