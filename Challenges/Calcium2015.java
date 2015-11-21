import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Recently, more and more illegal street races have been spotted at night in
 * the city, and they have become a serious threat to public safety. Therefore,
 * the Police Chief has decided to deploy speed cameras on the streets to
 * collect evidence.
 * 
 * There are N+1 intersections in the city, connected by N roads. Every road has
 * the same length of 1. A street race may take place between any two different
 * intersections by using the roads connecting them. Limited by their budget,
 * the police are able to deploy at most K speed cameras on these N roads. These
 * K speed cameras should be installed such that the length of any possible
 * street race route not covered by speed cameras should be as short as
 * possible.
 * 
 * You are given a map of the city in the form of two zero-indexed arrays, A and
 * B of length N, and an integer K:
 * 
 * For each J (0 ≤ J < N) there is a road connecting intersections A[J] and
 * B[J]. The Police Chief would like to know the minimum length of the longest
 * path out of surveillance after placing at most K speed cameras.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A, int[] B, int K); }
 * 
 * that, given arrays A and B of N integers and integer K, returns the minimum
 * length of the longest path unmonitored by speed cameras after placing at most
 * K speed cameras.
 * 
 * For example, given K = 2 and the following arrays:
 * 
 * A[0] = 5 B[0] = 1 A[1] = 1 B[1] = 0 A[2] = 0 B[2] = 7 A[3] = 2 B[3] = 4 A[4]
 * = 7 B[4] = 2 A[5] = 0 B[5] = 6 A[6] = 6 B[6] = 8 A[7] = 6 B[7] = 3 A[8] = 1
 * B[8] = 9
 * 
 * 
 * the function should return 2. Two speed cameras can be installed on the roads
 * between intersections 1 and 0 and between intersections 0 and 7. (Another
 * solution would be to install speed cameras between intersections 0 and 7 and
 * between intersections 0 and 6.) By installing speed cameras according the
 * first plan, one of the longest paths without a speed camera starts at
 * intersection 8, passes through intersection 6 and ends at intersection 3,
 * which consists of two roads. (Other longest paths are composed of
 * intersections 5, 1, 9 and 7, 2, 4).
 * 
 * Assume that:
 * 
 * N is an integer within the range [1..50,000]; each element of arrays A, B is
 * an integer within the range [0..N]; K is an integer within the range [0..N];
 * the distance between any two intersections is not greater than 900.
 * Complexity:
 * 
 * expected worst-case time complexity is O(N*log(N)); expected worst-case space
 * complexity is O(N), beyond input storage (not counting the storage required
 * for input arguments). Elements of input arrays can be modified.
 * 
 * @author Philip
 *
 */
public class CalciumChallenge2015 {
	public final int MAX_DISTANCE = 900;
	public int diameter[];
	public int subtreeDiameter[];
	public final int maxNodes = 50001;

	class Node {
		public int id;
		public List<Integer> edgeList;

		public Node(int id) {
			this.id = id;
			edgeList = new ArrayList<Integer>();
		}

		public void addEdge(int edge) {
			edgeList.add(edge);
		}
	}

	public int solution(int[] A, int[] B, int K) {
		Node[] nodes = createTree(A, B);
		diameter = new int[maxNodes];
		subtreeDiameter = new int[maxNodes];
		int first = 0;
		int last = Math.min(A.length, MAX_DISTANCE);
		int minStreetDistance = Integer.MAX_VALUE;
		int mid;
		while (first <= last) {
			mid = (first + last) / 2;
			Arrays.fill(diameter, -2);
			diameter[0] = -1;
			if (splitTree(nodes, nodes[0], mid) <= K) {
				minStreetDistance = mid;
				last = mid - 1;

			} else {
				first = mid + 1;
			}
		}
		return minStreetDistance;
	}

	public Node[] createTree(int[] A, int[] B) {
		Node[] nodes = new Node[maxNodes];
		for (int i = 0; i < A.length; i++) {
			if (nodes[A[i]] == null) {
				nodes[A[i]] = new Node(A[i]);
			}
			if (nodes[B[i]] == null) {
				nodes[B[i]] = new Node(B[i]);
			}
			nodes[A[i]].addEdge(B[i]);
			nodes[B[i]].addEdge(A[i]);
		}
		return nodes;
	}

	public int splitTree(Node[] nodes, Node node, int distanceLimit) {
		int cameras = 0;
		for (int nodeId : node.edgeList) {
			if (diameter[nodeId] == -2) { // Not visited, mark as visit and
											// visit it
				diameter[nodeId] = -1;
				cameras += splitTree(nodes, nodes[nodeId], distanceLimit);
			}
		}
		int maxDiameter = -1;
		int subNodes = 0;
		for (int nodeId : node.edgeList) {
			if (diameter[nodeId] >= 0) {
				subtreeDiameter[subNodes] = (diameter[nodeId]);
				subNodes++;
			}
		}
		Arrays.sort(subtreeDiameter, 0, subNodes);
		for (int i = 0; i < subNodes / 2; i++) {
			int temp = subtreeDiameter[i];
			subtreeDiameter[i] = subtreeDiameter[subNodes - 1 - i];
			subtreeDiameter[subNodes - 1 - i] = temp;
		}
		for (int i = 0; i < subNodes; i++) {
			if (i != subNodes - 1 && subtreeDiameter[i] + subtreeDiameter[i + 1] + 2 > distanceLimit) {
				cameras++;
			} else if (i == subNodes - 1 && subtreeDiameter[i] + 1 > distanceLimit) {
				cameras++;
			} else {
				maxDiameter = Math.max(maxDiameter, subtreeDiameter[i]);
			}
		}
		if (maxDiameter == -1) { // We removed all subtrees, diameter is 0 since
									// not linked to tree
			diameter[node.id] = 0;
		} else { // Diameter at this node is max diameter of its biggest subtree
					// + 1
			diameter[node.id] = maxDiameter + 1;
		}
		return cameras;
	}
}