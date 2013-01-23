package AStar;
import AStar.ISearchNode;
import java.util.*;
/**
 * Uses the A* Algorithm to find the shortest path from 
 * an initial to a goal node.Nodes have to implement the
 * ISearchNode interface. 
 *
 */
public class AStar {
    // amount of debug output
    private static int verbose = 0;
    public AStar() {
    }
    /**
     * @param initialNode start of the search
     * @param goalNode end of the search
     * @return goal node from which you can reconstruct the path
     */
    public static ISearchNode search(ISearchNode initialNode, IGoalNode goalNode) {
        PriorityQueue<ISearchNode> openSet = new PriorityQueue<ISearchNode>();
        openSet.add(initialNode);
        ArrayList<ISearchNode> closedSet = new ArrayList<ISearchNode>();

        while(openSet.size() > 0) {
            //get element with the least heuristic costs
            ISearchNode currentNode = openSet.remove();
            if(verbose > 0){
                System.out.println("Open set: " + openSet.toString());
                System.out.println("Current node: " + currentNode.toString());
                System.out.println("Closed set: " + closedSet.toString());
            }
            if(goalNode.equals(currentNode)) {
                //we know the shortest path to the goal node, done
                return currentNode;
            }
            //get successor nodes
            ArrayList<ISearchNode> successorNodes = currentNode.getSuccessors();
            for(ISearchNode successorNode : successorNodes) {
                boolean inOpenSet;
                if(closedSet.contains(successorNode))
                    continue;
                /* Special rule for nodes that are generated within other nodes:
                 * We need to ensure that we use the node and
                 * its g value from the openSet if its already discovered
                 */
                ISearchNode discSuccessorNode = getNode(openSet, successorNode);
                if(discSuccessorNode != null) {
                    successorNode = discSuccessorNode;
                    inOpenSet = true;
                } else {
                    inOpenSet = false;
                }
                //compute tentativeG
                double tentativeG = currentNode.g() + currentNode.c(successorNode);
                //node was already discovered and this path is worse than the last one
                if(inOpenSet && tentativeG >= successorNode.g())
                    continue;
                successorNode.setParent(currentNode);
                if(inOpenSet) {
                    // if successorNode is already in data structure it has to be inserted again to 
                    // regain the order
                    openSet.remove(successorNode);
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                } else {
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                }
            }
            closedSet.add(currentNode);
        }
        return null;
    }

    /**
     * returns the element from a PriorityQueue of ISearchNodes
     * @param queue queue to search in
     * @param searchedNode node we search
     * @return node from the queue
     */
    private static ISearchNode getNode(PriorityQueue<ISearchNode> queue, ISearchNode searchedNode) {
        for(ISearchNode openSearchNode : queue) {
            if(openSearchNode.equals(searchedNode)) {
                return openSearchNode;
            }
        }
        return null;
    }


    /**
     * returns shortest path
     * first element is the initial node and the last element is the goal node
     * @param node goalNode a goal node after a search call
     * @return path to the goal node
     */
    public static ArrayList<ISearchNode> shortestPath(ISearchNode node) {
        ArrayList<ISearchNode> path = new ArrayList<ISearchNode>();
        path.add(node);
        ISearchNode currentNode = node;
        while(currentNode.getParent() != null) {
            ISearchNode parent = currentNode.getParent();
            path.add(0, parent);
            currentNode = parent;
        }
        return path; 
    }
}
