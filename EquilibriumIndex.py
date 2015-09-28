def equiIndex(A):
  arraySize = len(A)
  left = 0
  right = 0
  for i in range(0, arraySize):
    if i != 0:
      left += A[i-1]
      right -= A[i]
    elif i == 0:
      for ii in range(i+1, arraySize):
        right += A[ii]
    if left == right:
      return i
  return -1
