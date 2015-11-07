def equiIndex(A):
  arraySize = len(A)
  left = 0
  right = 0
  for (i in range(1, arraySize):
    right += A[i]
  for i in range(1, arraySize):
    left += A[i-1]
    right -= A[i]
    if left == right:
      return i
  return -1
