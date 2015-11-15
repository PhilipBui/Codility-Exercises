import java.util.Arrays;

/**
 * In a room there are N ropes and N weights. Each rope is connected to exactly
 * one weight (at just one end), and each rope has a particular durability − the
 * maximum weight that it can suspend.
 * 
 * There is also a hook, attached to the ceiling. The ropes can be attached to
 * the hook by tying the end without the weight. The ropes can also be attached
 * to other weights; that is, the ropes and weights can be attached to one
 * another in a chain. A rope will break if the sum of weights connected to it,
 * directly or indirectly, is greater than its durability.
 * 
 * We know the order in which we want to attach N ropes. More precisely, we know
 * the parameters of the rope (durability and weight) and the position of each
 * attachment. Durabilities, weights and positions are given in three
 * zero-indexed arrays A, B, C of lengths N. For each I (0 ≤ I < N):
 * 
 * A[I] is the durability of the I-th rope, B[I] is the weight connected to the
 * I-th rope, C[I] (such that C[I] < I) is the position to which we attach the
 * I-th rope; if C[I] equals −1 we attach to the hook, otherwise we attach to
 * the weight connected to the C[I]-th rope. The goal is to find the maximum
 * number of ropes that can be attached in the specified order without breaking
 * any of the ropes.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A, int[] B, int[] C); }
 * 
 * that, given three zero-indexed arrays A, B, C of N integers, returns the
 * maximum number of ropes that can be attached in a given order.
 * 
 * For example, given the following arrays:
 * 
 * A[0] = 5 B[0] = 2 C[0] = -1 A[1] = 3 B[1] = 3 C[1] = 0 A[2] = 6 B[2] = 1 C[2]
 * = -1 A[3] = 3 B[3] = 1 C[3] = 0 A[4] = 3 B[4] = 2 C[4] = 3
 * 
 * 
 * the function should return 3, as if we attach a fourth rope then one rope
 * will break, because the sum of weights is greater than its durability (2 + 3
 * + 1 = 6 and 6 > 5).
 * 
 * Given the following arrays:
 * 
 * A[0] = 4 B[0] = 2 C[0] = -1 A[1] = 3 B[1] = 2 C[1] = 0 A[2] = 1 B[2] = 1 C[2]
 * = 1
 * 
 * 
 * the function should return 2, as if we attach a third rope then one rope will
 * break, because the sum of weights is greater than its durability (2 + 2 + 1 =
 * 5 and 5 > 4).
 * 
 * Assume that:
 * 
 * N is an integer within the range [0..100,000]; each element of array A is an
 * integer within the range [1..1,000,000]; each element of array B is an
 * integer within the range [1..5,000]; each element of array C is an integer
 * such that −1 ≤ C[I] < I, for each I (0 ≤ I < N). Complexity:
 * 
 * expected worst-case time complexity is O(N*log(N)); expected worst-case space
 * complexity is O(N), beyond input storage (not counting the storage required
 * for input arguments). Elements of input arrays can be modified.
 * 
 * @author Philip
 *
 */
public class SulphurChallenge2014 {
	
	public int solution(int[] A, int[] B, int[] C) {
		int start = 0;
		int end = C.length - 1;
		int mid = 0;
		int[] weightSum = new int[C.length];
		int result = -1;
		while (start <= end) {
		    mid = (start+end) / 2;
			if (checkValid(A, B, C, weightSum, mid)) {
				result = mid;
				start = mid + 1;
			}
			else {
				end = mid-1;
			}
		}
		return result + 1;
	}
	public boolean checkValid(int[] A, int[] B, int[] C, int[] weightSum, int mid) {
		Arrays.fill(weightSum, 0);
		for (int i = mid; i >= 0; i--) {
			weightSum[i] += B[i];
			if (weightSum[i] > A[i]) {
				return false;
			}
			if (C[i] != -1) {
				weightSum[C[i]] += weightSum[i];
			}
		}
		return true;
	}
}


  /*
	class Rope {
		int id;
		int parent;
		int durability;
		int weight;
		ArrayList<Integer> children;

		public Rope(int id, int durability, int weight) {
			this.id = id;
			this.durability = durability;
			this.weight = weight;
			children = new ArrayList<Integer>();
		}

		public void addChildren(int id) {
			children.add(id);
		}
	}

	// A = durability, B = weight C = parent attached
	public int solution3(int[] A, int[] B, int[] C) {
		ArrayList<Integer> roots = new ArrayList<Integer>();
		Rope[] ropes = buildRopeTree(A, B, C, roots);
		int first = 0;
		int last = C.length;
		int mid = first + last / 2;
		while (first <= last) {
			if (checkValid(ropes, mid, roots)) {
				first = mid + 1;
			} else {
				last = mid - 1;
			}
			mid = (first + last) / 2;
		}
		return Math.max(0, mid);
	}

	public Rope[] buildRopeTree(int[] A, int[] B, int[] C, ArrayList<Integer> roots) {
		Rope[] ropes = new Rope[C.length];
		for (int i = 0; i < C.length; i++) {
			Rope rope = new Rope(i, A[i], B[i]);
			if (C[i] != -1) {
				rope.parent = C[i];
				Rope parent = ropes[C[i]];
				parent.addChildren(i);
			} else {
				roots.add(i);
				rope.parent = -1;
			}
			ropes[i] = rope;
		}
		return ropes;
	}
	public boolean checkValid(Rope[] ropes, int mid, ArrayList<Integer> roots) {
		Stack<Rope> stack = new Stack<Rope>();
		int[] weightSum = new int[ropes.length];
		Arrays.fill(weightSum, -1); // -1 represents unvisited Ropes
		for (int root : roots) {
			if (root > mid - 1) {
				continue;
			}
			stack.push(ropes[root]);
			while (!stack.isEmpty()) {
				Rope rope = stack.pop();
				// We visited this before, with the children added
				if (weightSum[rope.id] != -1) {
					// Add this Rope's weight onto it
					weightSum[rope.id] += rope.weight;
					if (weightSum[rope.id] > rope.durability) {
						return false;
					} else if (rope.parent != -1) {
						weightSum[rope.parent] += weightSum[rope.id];
					}
				} // We haven't visited before, and there are children
				else if (!rope.children.isEmpty()) {
					weightSum[rope.id] = 0; // Mark it as visited
					stack.push(rope);
					// Push children onto stack, then revisit parent later
					for (int child : rope.children) { 
						if (child > mid - 1) {
							continue;
						}
						stack.push(ropes[child]);
					}
				} 
				// We haven't visited before, no children so just add onto parent
				else {
					if (rope.weight > rope.durability) {
						return false;
					}
					weightSum[rope.id] = rope.weight;
					if (rope.parent != -1) {
						weightSum[rope.parent] += rope.weight;
					}
				}
			}
		}
		return true;
	}
	public int checkValid2(Rope[] ropes, int mid, ArrayList<Integer> roots) {
		int totalWeight = 0;
		for (int root : roots) {
			if (root > mid - 1) {
				continue;
			}
			Rope rope = ropes[root];
			int childrenWeight = checkValid2(ropes, mid, rope.children);
			if (childrenWeight == -1) {
				return -1;
			}
			int ropeWeight = rope.weight;
			ropeWeight += childrenWeight;
			if (ropeWeight > rope.durability) {
				return -1;
			}
			totalWeight += ropeWeight;
		}
		return totalWeight;
	}

	public int solution2(int[] A, int[] B, int[] C) {
		int[] maximumWeightAllowed = new int[C.length];
		// I represents the rope we are on, for every rope weight we remove
		// durability of all parents
		int i;
		for (i = 0; i < C.length; i++) {
			// J represents the rope we are working and removing durability from
			int j = i;
			maximumWeightAllowed[j] = A[j];
			while (C[j] != -1) {
				// Parents of i maximum weight allowed -= current rope's weight
				maximumWeightAllowed[j] -= B[i]; // Remove durability
				if (maximumWeightAllowed[j] < 0) {
					// Rope is broken, return maximum is previous i
					return i;
				}
				j = C[j];
			}
			maximumWeightAllowed[j] -= B[i];
			if (maximumWeightAllowed[j] < 0) {
				// Rope is broken, return maximum is previous i
				return i;
			}
		}
		return i;
	}
	*/
