fileName = "realInput.txt"


freqCols = None

with open(fileName) as f:
	lines = f.readlines()

	#init columns
	freqCols = [ {} for i in range(len(lines[0]))]

	for line in lines:
		for (i,c) in enumerate(line):
			if c not in freqCols[i]:
				freqCols[i][c] = 0

			freqCols[i][c] += 1

code = ""
for freq in freqCols:
	print(freq)
	maxVal = 0
	maxChar = ""

	for key in freq:
		if freq[key] > maxVal:
			maxChar = key
			maxVal = freq[key]

	code += maxChar
print(code)

