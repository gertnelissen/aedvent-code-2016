noElves = 3005290#5


#they all start with one present
hasPresent = dict()
for i in range(noElves):
	hasPresent[i] = True


noElvesWithPresent = noElves
currElf = 0
nextElf = 1
while noElvesWithPresent > 1:
	
	while not hasPresent[nextElf]:
		nextElf = (nextElf + 1) % noElves

	hasPresent[nextElf] = False
	noElvesWithPresent -= 1

	while not hasPresent[nextElf]:
		nextElf = (nextElf + 1) % noElves

	currElf = nextElf 
	nextElf = (nextElf + 1) % noElves

print(currElf + 1)