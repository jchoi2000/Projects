# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util, math, sys

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        

        "*** YOUR CODE HERE ***"
        if successorGameState.isWin():
            return sys.maxsize
        if successorGameState.isLose():
            return -sys.maxsize
           
        nearestFood = []

        for fooPos in newFood.asList():
            nearestFood.append(util.manhattanDistance(newPos, fooPos))

        nearestFood = min(nearestFood)        

        edibleGhost = []
        noeatGhost = []

        nearestGhost = -1
        # if noeatGhost:
        #     print("PRINTPRINTPRINTPRINT")
        if newGhostStates:
            for state in newGhostStates:
                if state.scaredTimer == 0:
                    noeatGhost.append(state.getPosition())
                else:
                    edibleGhost.append(state.getPosition())
            if noeatGhost:
                nearestGhost = min([util.manhattanDistance(newPos, dGhost) for dGhost in noeatGhost])
            if edibleGhost:
                nearestFood = min(nearestFood, min([util.manhattanDistance(newPos, dGhost) for dGhost in edibleGhost]))

        nearFoodScore = 1/nearestFood
        nearGhostScore = -1/nearestGhost
        return successorGameState.getScore() + nearFoodScore + nearGhostScore + sum(newScaredTimes)

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        
        
        return self.minimax(gameState, 0, 0)[0]
            

            


        # util.raiseNotDefined()

    # def miniMaxScore(self, gameState, depth):
    #     if gameState.isWin() or gameState.isLose() or depth == self.depth:
    #         return self.evaluationFunction(gameState)
    #     for agent in range(gameState.getNumAgents()):
    #         if agent == 0:
    #             actions = gameState.getLegalActions(agent)
    #             successors = [gameState.generateSuccessor(agent, action) for action in actions]
    #             return max_value(gameState, successors, depth)
    #         else:
    #             actions = gameState.getLegalActions(agent)
    #             successors = [gameState.generateSuccessor(agent, action) for action in actions]
    #             return min_value(gameState, successors, depth)

    # def max_value(gameState, successors, depth):
    #     maxScore = sys.minsize
    #     memory = None
    #     for successor in successors:
    #         mScoretemp = max(maxScore, miniMaxScore(successor, depth + 1))
    #         if mScoretemp != maxScore and mScoretemp != sys.minsize:
    #             memory = successor
    #         maxScore = mScoretemp
    #     return maxScore, memory

    # def min_value(gameState, depth):
    #     minScore = sys.maxsize
    #     memory = None
    #     for successor in successors:
    #         miScoretemp = min(minScore, miniMaxScore(successor, depth + 1))
    #         if miScoretemp != minScore and mScoretemp != sys.maxsize:
    #             minScore = miScoretemp
    #     return minScore, memory

    def minimax(self, gState, depth, agent):
            if agent >= gState.getNumAgents():
                agent = 0
                depth += 1

            if depth == self.depth or gState.isWin() or gState.isLose():
                return 0, self.evaluationFunction(gState)

            # moves = [self.minimax(gState.generateSuccessor(agent, action), depth, agent) for action in gState.getLegalActions(agent)]

            maxPacSco = -sys.maxsize
            minPacSco = sys.maxsize
            optScore = None

            if agent == 0:
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.minimax(gState.generateSuccessor(agent, action), depth, agent+1)
                    if scoreCheck > maxPacSco:
                        maxPacSco = scoreCheck
                        optScore = maxPacSco
                        optMove = action
                       
            else:
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.minimax(gState.generateSuccessor(agent, action), depth, agent+1)
                    if scoreCheck < minPacSco:
                        minPacSco = scoreCheck
                        optScore = minPacSco
                        optMove = action
            return optMove, optScore

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        # util.raiseNotDefined()
        return self.alphabeta(gameState, 0, 0, -sys.maxsize, sys.maxsize)[0]
        
    def alphabeta(self, gState, depth, agent, alpha, beta):
            if agent >= gState.getNumAgents():
                agent = 0
                depth += 1

            if depth == self.depth or gState.isWin() or gState.isLose():
                return 0, self.evaluationFunction(gState)

            # moves = [self.minimax(gState.generateSuccessor(agent, action), depth, agent) for action in gState.getLegalActions(agent)]

            maxPacSco = -sys.maxsize
            minPacSco = sys.maxsize
            optScore = None

            if agent == 0:
                maxPacSco2 = -sys.maxsize
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.alphabeta(gState.generateSuccessor(agent, action), depth, agent+1, alpha, beta)
                    maxPacSco2 = max(maxPacSco2, scoreCheck)
                    if scoreCheck > maxPacSco:
                        maxPacSco = scoreCheck
                        optScore = maxPacSco
                        optMove = action
                    if maxPacSco2 > beta:
                        optMove = action
                        optScore = maxPacSco2
                        return optMove, maxPacSco2
                    # if maxPacSco > beta:
                    #     maxPacSco = scoreCheck
                    #     optScore = maxPacSco
                    #     optMove = action
                    
                    # alpha = max(alpha, scoreCheck)

                    
                    # if maxPacSco > beta:
                    #     return optMove, optScore

                    alpha = max(alpha, maxPacSco2)


            else:
                minPacSco2 = sys.maxsize
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.alphabeta(gState.generateSuccessor(agent, action), depth, agent+1, alpha, beta)
                    minPacSco2 = min(minPacSco2, scoreCheck)
                    if scoreCheck < minPacSco:
                        minPacSco = scoreCheck
                        optScore = minPacSco
                        optMove = action
                    if minPacSco2 < alpha:
                        optMove = action
                        optScore = minPacSco2
                        return optMove, minPacSco2
                    # if scoreCheck < minPacSco:
                    #     minPacSco = scoreCheck
                    #     optScore = minPacSco
                    #     optMove = action
                    
                    # beta = min(beta, scoreCheck)


                    # if minPacSco < beta:
                    #     return optMove, optScore
                    beta = min(beta, minPacSco2)


            # if optScore is None:
            #     return None, self.evaluationFunction(gState)        
            return optMove, optScore

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        # util.raiseNotDefined()
        return self.expectimax(gameState, 0, 0)[0]
    
    def expectimax(self, gState, depth, agent):
            if agent >= gState.getNumAgents():
                agent = 0
                depth += 1

            if depth == self.depth or gState.isWin() or gState.isLose():
                return None, self.evaluationFunction(gState)


            maxPacSco = -sys.maxsize
            minPacSco = sys.maxsize
            optScore = 0

            if agent == 0:
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.expectimax(gState.generateSuccessor(agent, action), depth, agent+1)
                    # print(scoreCheck)
                    # print(maxPacSco)
                    if scoreCheck > maxPacSco:
                        maxPacSco = scoreCheck
                        optScore = maxPacSco
                        optMove = action
                       
            else:
                minPacSco = 0
                for action in gState.getLegalActions(agent):
                    holder, scoreCheck = self.expectimax(gState.generateSuccessor(agent, action), depth, agent+1)
                    # print(holder)
                    sucProb = 1/len(gState.getLegalActions(agent))
                    minPacSco += sucProb * scoreCheck
                    optMove = random.choice(gState.getLegalActions(agent))
                    optScore = minPacSco
                # print(optMove)
            
            return optMove, optScore


def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: 
    The score is modified by a food bonus and a ghost penalty. 
    The food bonus is calculated using the manhattan distance to the closest food pellet, 
    a greater bonus being assigned to closer pellets vs further pellets.

    The ghost penalty is calculated using the distances from pacman to all the ghosts, 
    combined with the scared status/timer of each respective ghost to adjust the penalty value.
    """
    "*** YOUR CODE HERE ***"
    successorGameState = currentGameState
    newPos = successorGameState.getPacmanPosition()
    newFood = successorGameState.getFood()
    newGhostStates = successorGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

    

    score = currentGameState.getScore()

    if successorGameState.isWin() or successorGameState.isLose():
        return score

    closestFoodDistance = min([manhattanDistance(newPos, food) for food in newFood.asList()])

    ghostDistances = []
    ghostPenalty = 0

    for ghostState in newGhostStates:
        distance = manhattanDistance(newPos, ghostState.getPosition())
        if ghostState.scaredTimer > 0:
            ghostPenalty += 1/(distance * ghostState.scaredTimer)
        else:
            ghostPenalty += 1/(distance)



    return score + 1/(closestFoodDistance/2) - ghostPenalty 
    # util.raiseNotDefined()

# Abbreviation
better = betterEvaluationFunction
