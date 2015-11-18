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
		//P < Q and A[Q] < A[P]
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
