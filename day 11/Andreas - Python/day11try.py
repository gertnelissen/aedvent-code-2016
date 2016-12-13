import queue
import sys


class State:
    currFloor = 0
    distance = 0
    pairs = list()

    def __init__(self,currFloor,distance,pairs):
        self.currFloor = currFloor
        self.distance = distance

        #sort in python are guaranteed to be stable :D
        self.pairs = sorted(pairs, key= lambda x: x[1])
        self.pairs = sorted(self.pairs, key= lambda x: x[0])


#hardcoded start state
testStartState = State(0,0, [(0,1),(0,2)])
def isGoalState(state):
    for element in state.pairs:
        for item in element:
            if item != 3:
                return False

    return True




seen = dict()
paths = Queue.Queue()
paths.put(testStartState)

while not isGoalState(currentState):

    currentState = paths.get()
    while currentState in seen:
        currentState = paths.get()

    seen[currentState] = 1

    if currentState.isEqual(goalState):
        print("Found ", currentState.distance)
    else:
        #get all possible moves
        print()
        #get all valid moves