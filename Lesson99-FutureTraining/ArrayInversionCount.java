/**
 * A zero-indexed array A consisting of N integers is given. An inversion is a
 * pair of indexes (P, Q) such that P < Q and A[Q] < A[P].
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that computes the number of inversions in A, or returns −1 if it exceeds
 * 1,000,000,000.
 * 
 * Assume that:
 * 
 * N is an integer within the range [0..100,000]; each element of array A is an
 * integer within the range [−2,147,483,648..2,147,483,647]. For example, in the
 * following array:
 * 
 * A[0] = -1 A[1] = 6 A[2] = 3 A[3] = 4 A[4] = 7 A[5] = 4 there are four
 * inversions:
 * 
 * (1,2) (1,3) (1,5) (4,5) so the function should return 4.
 * 
 * Complexity:
 * 
 * expected worst-case time complexity is O(N*log(N)); expected worst-case space
 * complexity is O(N), beyond input storage (not counting the storage required
 * for input arguments). Elements of input arrays can be modified.
 * 
 * @author Philip
 *
 */
public class ArrayInversionCount {
	public final int INVERSION_LIMIT = 1000000000;

	public int solution(int[] A) {
		return mergeSort(A, 0, A.length - 1);
	}

	public int mergeSort(int[] A, int start, int end) {
		int leftInversion = 0, rightInversion = 0;
		int mid = (start + end) / 2;
		if (start < end) {
			leftInversion = mergeSort(A, start, mid);
			rightInversion = mergeSort(A, mid + 1, end);
		} else {
			return 0; // This means there is 1 or less elements to sort.
		}
		if (leftInversion == -1 || rightInversion == -1) {
			return -1;
		}
		int mergeInversion = leftInversion + rightInversion;
		int p = start; // Index for sorting left array
		int q = mid + 1; // Index for sorting right array
		int tempIndex = 0;
		int[] tempArray = new int[end - start + 1];
		// P < Q and A[Q] < A[P]
		while (p <= mid && q <= end) {
			if (A[p] <= A[q]) {
				tempArray[tempIndex] = A[p];
				p++;
			} else {
				tempArray[tempIndex] = A[q];
				q++;
				mergeInversion += mid - p + 1;
				if (mergeInversion >= INVERSION_LIMIT) {
					return -1;
				}
			}
			tempIndex++;
		}
		while (p <= mid) {
			tempArray[tempIndex] = A[p];
			p++;
			tempIndex++;
		}
		while (q <= end) {
			tempArray[tempIndex] = A[q];
			q++;
			tempIndex++;
		}
		for (int i = 0; i < tempArray.length; i++) {
			A[start + i] = tempArray[i];
		}
		return mergeInversion;
	}
}
