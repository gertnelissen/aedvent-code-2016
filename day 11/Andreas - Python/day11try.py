import queue
import sys
from copy import deepcopy
from itertools import combinations


FLOOR_COUNT = 4

class State:
    currFloor = 0
    distance = 0
    pairs = list() #[[0,1],[0,2]] corresponds to start of test state; for each element keep floors in pair: (microchip, generator)

    def __init__(self,currFloor,distance,pairs):
        self.currFloor = currFloor
        self.distance = distance

        #sort in python are guaranteed to be stable :D
        self.pairs = sorted(pairs, key= lambda x: x[0])
        self.pairs = sorted(self.pairs, key= lambda x: x[1])

    def isGoalState(self):
        if self.currFloor != FLOOR_COUNT - 1:#goal state will be reached when elevator is on upper floor
            return False;

        for element in state.pairs:
            for item in element:
                if item != FLOOR_COUNT - 1:
                    return False

        return True

    def isValid(self):
        # a chip can only be with another chips if it is connected to its own generator:
        #=> invalid : find a chip that is with another generator
        i = 0
        while i < len(self.pairs):
            j = 0
            while j < len(self.pairs):
                if j != i:
                    if self.pairs[i][0] != self.pairs[i][1] \
                        and self.pairs[i][0] == self.pairs[j][1]:
                        return False

                j+=1
            i+=1

        return True

    def getAdjacent(self):
        #get stuffz at current floor
        currGenerators = list()
        currChips = list()
        for el, pair in enumerate(self.pairs):
            if pair[0] == self.currFloor: currChips.append(el)
            if pair[1] == self.currFloor: currGenerators.append(el)

        adjStates = list()
        for c in range(len(currChips) + 1):
            for g in range(len(currGenerators) + 1):
            
                if g+c > 0 and g+c <= 2: #elevator must carry at least 1 item and cannot carry more than 2 items
                    
                    #add new states
                    for chosenChips in combinations(currChips,c):
                        for chosenGens in combinations(currGenerators, g):
                            
                            print("c:", chosenChips , " - g:", chosenGens)

                            newPairs = deepcopy(self.pairs)
                            if self.currFloor < FLOOR_COUNT - 1: #go up
                                for cc in chosenChips:
                                    newPairs[cc][0] += 1

                                for gg in chosenGens:
                                    newPairs[gg][1] += 1

                                #go up 
                                newState = State(
                                    currFloor = self.currFloor + 1,
                                    distance  = self.distance  + 1,
                                    pairs     = newPairs
                                )

                                newState.dump()

                                if newState.isValid():
                                    adjStates.append(newState)

                            newPairs = deepcopy(self.pairs)
                            if self.currFloor > 0: #go down
                                for cc in chosenChips:
                                    newPairs[cc][0] -= 1

                                for gg in chosenGens:
                                    newPairs[gg][1] -= 1

                                #go up 
                                newState = State(
                                    currFloor = self.currFloor - 1,
                                    distance  = self.distance  + 1,
                                    pairs     = newPairs
                                )
                            
                                newState.dump()

                                if newState.isValid():
                                    adjStates.append(newState)

        return adjStates

    def hash(self):
        var = str(self.currFloor) + "|"
        for pair in self.pairs:
            var+= str(pair[0]) + str(pair[1]) + "|"
        return var

    def dump(self):
        print(self.currFloor,  " -> ", self.pairs, " {", self.distance, "} ==>> ", self.hash(), " ==>> ", self.isValid())


#hardcoded start state
testStartState = State(0,0, [[0,1],[0,2]])

seen = dict()
paths = queue.Queue()
paths.put(testStartState)
currentState = paths.get()
while currentState is not None and not currentState.isGoalState():
    
    if not currentState.hash() in seen:
        seen[currentState.hash()] = 1

        currentState.dump()    
        print("\tCurr Distance:", currentState.distance)

        for state in currentState.getAdjacent():
            paths.put(state)

        print("\tcurr paths: ", paths.qsize(), "\n\n")

    else:
        print("Skipping - ", currentState.hash(), " -> already seen")

    if paths.empty(): #concurrent => the get function will just block and wait for input
        currentState = None
    else:
        currentState = paths.get()


if currentState is not None and currentState.isGoalState():
    print("found! ")
    print(currentState.currFloor)
    print()
    print(currentState.distance)
    print()
    print(currentState.pairs)
else:
    print("something went wrong </3")