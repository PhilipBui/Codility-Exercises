/**
You want to spend your next vacation in Poland. Despite its not being a very big country, Poland has a highly diverse natural environment ranging from the Baltic Sea in the north to the Tatra Mountains in the south. As you enjoy swimming in the sea as well as trekking up mountains, you would like to spend some time in both of those locations. However, the weather in Poland can sometimes be very capricious, so you also need to take that into account when planning your vacation.

In the summer you are free for N consecutive days. You can start and finish your vacation on any of these days. You want to spend the first part of your vacation at the seaside and the remaining part in the mountains. These parts can each be of any positive length, and you want to maximize the total length of the vacation.

You have obtained a weather forecast for all N days when you are free. By curious coincidence, the weather on every day is expected to be either perfect for spending the day at the seaside but not in the mountains, or vice versa (perfect for trekking but not for swimming). Obviously, you want the best possible weather during each part of your vacation, so you require the weather to be perfect for swimming for more than half of the days in the first part of your vacation, and perfect for trekking for more than half of the days in the second part of your vacation.

The weather forecast is described in a zero-indexed array A: for each K (0 ≤ K < N), A[K] is equal to 0 if the weather during day K favors the seaside, or 1 if the weather during that day favors the mountains.

Write a function:

class Solution { public int solution(int[] A); }

that, given a zero-indexed array A of N integers, returns the length of the longest vacation consistent with your requirements.

For example, consider array A such that:

    A[0] = 1
    A[1] = 1
    A[2] = 0
    A[3] = 1
    A[4] = 0
    A[5] = 0
    A[6] = 1
    A[7] = 1
You are free for eight days. The weather during days 2, 4 and 5 will be better for swimming, and better for trekking during the remaining days. You can start your vacation on day 1, spend five days at the seaside (three days will have perfect weather, which is more than half) and then spend two days in the mountains (both days will have perfect weather). That results in a vacation length of seven days, which is the longest possible vacation that meets your criteria, so the function should return 7.

For array A such that:

    A[0] = 1
    A[1] = 0
there is no vacation that satisfies your requirements, so the function should return 0.

Assume that:

N is an integer within the range [2..100,000];
each element of array A is an integer that can have one of the following values: 0, 1.
Complexity:

expected worst-case time complexity is O(N);
expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
Elements of input arrays can be modified.
*/

public class ArgonChallenge {
	int solution(int[] A) {
		int firstZero = 0;
		int lastOne = A.length - 1;
		while (firstZero < A.length) {
			if (A[firstZero] == 0) {
				break;
			}
			firstZero++;
		}
		if (firstZero == A.length) {
			//System.out.println("No zeros found");
			return 0; // No zeros found
		}
		while (lastOne >= 0) {
			if (A[lastOne] == 1) {
				break;
			}
			lastOne--;
		}
		if (lastOne == -1 || lastOne <= firstZero) {
			//System.out.println("No ones found or no holiday possible");
			return 0; // No ones found
		}
		int rightZeros = 0; // How many 0's on right holiday (BAD)
		int rightOnes = 0; // How many 1's on right holiday (GOOD)
		int canAddRight = -1; // How many 0's we can add, remember must be > half so -1
		for (int i = firstZero; i <= lastOne; i++) {
			if (A[i] == 0) {
				rightZeros++;
				canAddRight--;
			} else if (A[i] == 1) {
				rightOnes++;
				canAddRight++;
			} else {
				return -1; // False binary array
			}
		}
		int leftZeros = 0; // How many 0's on left holiday (GOOD)
		int leftOnes = 0; // How many 1's on left holiday (BAD)
		int canAddLeft = -1; // How many 1's we can add, remember must be > half so -1
		int spareLeft = firstZero; // How many 1's before first 0
		int spareRight = A.length - 1 - lastOne; // How many 0's after last 1
		int maximumHoliday = 0; // Maximum holiday found
		//System.out.println(spareLeft + ":" + spareRight);
		for (int i = firstZero; i <= lastOne; i++) {
			if (A[i] == 0) {
				leftZeros++;
				rightZeros--;
				canAddLeft++;
				canAddRight++; // Removing a 0 from right side means need extra one for a 0
			} else if (A[i] == 1) {
				leftOnes++;
				rightOnes--;
				canAddLeft--;
				canAddRight--; // Removing a 1 from right side means less extra 0 allowed
			}
			//System.out.println(leftZeros + ":" + leftOnes + ":" + canAddLeft);
			//System.out.println(rightZeros + ":" + rightOnes + ":" + canAddRight);
			// Check if valid holiday
			if (leftZeros > leftOnes && rightOnes > rightZeros) {
				int left = leftZeros + leftOnes;
				if (canAddLeft > 0) {
    				if (canAddLeft <= spareLeft) {
    					left += canAddLeft; // Add how many 1's allowed
    				} else if (spareLeft < canAddLeft) {
    					left += spareLeft; // Add all the spare 1's
    				}
				}
				int right = rightOnes + rightZeros;
				if (canAddRight > 0) {
    				if (canAddRight <= spareRight) {
    					right += canAddRight; // Add how many 0's allowed
    				} else if (spareRight < canAddRight) {
    					right += spareRight; // Add all the spare 0's
    				}
				}
				//System.out.println(left + ":" + right);
				if (left + right > maximumHoliday) {
					maximumHoliday = left + right;
				}
			}
		}
		return maximumHoliday;
	}
}
