package AStar.Tests;
import AStar.IGoalNode;
import AStar.ISearchNode;
import AStar.Tests.SearchNode2D;
public class GoalNode2D implements IGoalNode {
    private int x;
    private int y;
    public GoalNode2D(int x, int y) {
        this.x = x;
        this.y = y; 
    }
    public boolean inGoal(ISearchNode other) {
        if(other instanceof SearchNode2D) {
            SearchNode2D otherNode = (SearchNode2D) other;
            return (this.x == otherNode.getX()) && (this.y == otherNode.getY());
        }
        return false;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
