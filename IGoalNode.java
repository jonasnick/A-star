package AStar;

/**
 * GoalNodes don't need as much Information
 * as SearchNodes.
 */
public interface IGoalNode{
    public boolean equals(Object other);
} 
