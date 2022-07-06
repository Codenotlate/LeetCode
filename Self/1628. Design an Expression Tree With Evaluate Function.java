/*Thought
M1: Without considering follow-up
we define a selfNode class to extend the abstract class Node, define left/right/val, then implement the evaluate() function there.
The evaluate() function will be based on the val of current node, if it's numerical, return the val itself, otherwise, return the operation result based on evaluate results of left node and right node.
Then for the buildTree method, simply use a stack with selfNode, return the node.

M2: considering follow-up to be more modular.
Check the Factory method pattern in post:
https://leetcode.com/problems/design-an-expression-tree-with-evaluate-function/discuss/1209901/Java-Factory-method-pattern
*/

// implement M1 below first, check M2 next time

/**
 * This is the interface for the expression tree Node.
 * You should not remove it, and you can define some classes to implement it.
 */

abstract class Node {
    public abstract int evaluate();
    // define your fields here
};


class TreeNode extends Node {
    String val;
    TreeNode left;
    TreeNode right;
    
    public TreeNode(String value) {
        val = value;
    }
    // implement the abstract evaluate function
    public int evaluate() {
        switch (val) {
            case "+": return left.evaluate() + right.evaluate();
            case "-": return left.evaluate() - right.evaluate();
            case "*": return left.evaluate() * right.evaluate();
            case "/": return left.evaluate() / right.evaluate();
            default: return Integer.valueOf(val);
        }        
    }
}

/**
 * This is the TreeBuilder class.
 * You can treat it as the driver code that takes the postinfix input 
 * and returns the expression tree represnting it as a Node.
 */

class TreeBuilder {
    Node buildTree(String[] postfix) {
        Stack<TreeNode> stack = new Stack<>();
        String opes = "+-*/";
        for (String s: postfix) {
            TreeNode cur = new TreeNode(s);
            if (opes.contains(s)) {
                cur.right = stack.pop();
                cur.left = stack.pop();
            }
            stack.push(cur);
        }
        return stack.pop();
    }
};


/**
 * Your TreeBuilder object will be instantiated and called as such:
 * TreeBuilder obj = new TreeBuilder();
 * Node expTree = obj.buildTree(postfix);
 * int ans = expTree.evaluate();
 */