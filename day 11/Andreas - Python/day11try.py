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
            j = i + 1
            while j < len(self.pairs):

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
                            self.dump()

                            newState = None
                            newPairs = deepcopy(self.pairs)
                            if self.currFloor < FLOOR_COUNT: #go up
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
        print(self.currFloor,  " -> ", self.pairs, " {", self.distance, "} ==>> ", self.hash())


#hardcoded start state
testStartState = State(0,0, [[0,1],[0,2]])
#testStartState.dump()
#print()
#for state in testStartState.getAdjacent():
#    state.dump()

#sys.exit()

seen = dict()
paths = queue.Queue()
paths.put(testStartState)
paths.put(testStartState)
currentState = paths.get()
while not currentState.isGoalState() and not paths.empty():

    currentState.dump()    
    print("\tCurr Distance:", currentState.distance)

    seen[currentState.hash()] = 1
    
    for state in currentState.getAdjacent():
        print("\tvalid ones:")
        state.dump()
        paths.put(state)

    print("\tcurr paths: ", paths.qsize())

    currentState = paths.get()
    while currentState.hash() in seen and not paths.empty():
        currentState = paths.get()

if currentState.isGoalState():
    print("found! ")
    print(currentState.currFloor)
    print()
    print(currentState.distance)
    print()
    print(currentState.pairs)
else:
    print("something went wrong")