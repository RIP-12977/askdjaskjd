/** The ExpressionTree class contains the methods to create an expression tree based
 *  on a given prefix expression. It can then evaluate it and print it to the screen.
 *  @author Nikhil Narvekar
 *  Collaborators: None
 *  Teacher: Ms.Ishman
 *  Period: 3
 *  Due Date: 3-6-2020 @ 4:30 PM
 */

/* Import Statements */
import java.util.*;

public class ExpressionTree
{
    /** Root of the expression tree */
    private ExpressionNode root;

    /** Used to parse a prefix expression */
    private Scanner strScan;
    private int arrayCounter = 0;

    /** Default constructor which initializes an empty Expression Tree.
     */
    public ExpressionTree()
    {
        root = null;
    }

    /** Constructor which takes a given prefix expression and creates an expression tree
     *  using it.
     *  @param prefix the prefix expression to convert into a tree
     */
    public ExpressionTree(String prefix)
    {
        root = null;
        setExpression(prefix);
    }

    /** The method takes a prefix expression and creates a root of the expression tree out of it.
     *  It then uses a recursive process through a helper method to construct the rest of the tree.
     *  @param prefix the prefix expression to build a tree from
     */
    public void setExpression(String prefix)
    {
        // Split prefix expression into an array
        String[] prefixElements = prefix.split(" ");
        arrayCounter = 0;

        // Find root of expression
        String value = prefixElements[arrayCounter];
        ExpressionNode toAdd;

        // Based on operator/operand symbol, create an expression-node
        if(value.equals(NodeType.MULTIPLY.getSymbol()))
            toAdd = new ExpressionNode(NodeType.MULTIPLY, null, null);
        else if(value.equals(NodeType.DIVIDE.getSymbol()))
            toAdd = new ExpressionNode(NodeType.DIVIDE, null, null);
        else if(value.equals(NodeType.ADD.getSymbol()))
            toAdd = new ExpressionNode(NodeType.ADD, null, null);
        else if(value.equals(NodeType.SUBTRACT.getSymbol()))
            toAdd = new ExpressionNode(NodeType.SUBTRACT, null, null);
        else if(value.equals(NodeType.REMAINDER.getSymbol()))
            toAdd = new ExpressionNode(NodeType.REMAINDER, null, null);
        else if(value.equals(NodeType.EXPONENT.getSymbol()))
            toAdd = new ExpressionNode(NodeType.EXPONENT, null, null);
        else
            toAdd = new ExpressionNode(Integer.parseInt(value));

        // Start recursive process to build tree
        buildTree(prefixElements, toAdd, false);

        // Set root back to built tree
        root = toAdd;
    }

    /** The method recursively builds the tree, checking each element
     *  and building a new node based on its type.
     *  @param elementArray the elements in the expression tree
     *  @param currentNode the current location in the binary search tree
     *  @param alternator a boolean variable that changes which branch operands are added on
     */
    public void buildTree(String[] elementArray, ExpressionNode currentNode, boolean alternator)
    {

        // Find next operator/operand to use. If all elements have been checked, exit recursion.
        arrayCounter++;
        if(arrayCounter > elementArray.length - 1)
            return;
        String value = elementArray[arrayCounter];

        // Based on operator/operand type, build and add a new node
        if(value.equals(NodeType.MULTIPLY.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.MULTIPLY, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else if(value.equals(NodeType.DIVIDE.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.DIVIDE, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else if(value.equals(NodeType.ADD.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.ADD, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else if(value.equals(NodeType.SUBTRACT.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.SUBTRACT, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else if(value.equals(NodeType.REMAINDER.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.REMAINDER, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else if(value.equals(NodeType.EXPONENT.getSymbol()))
        {
            ExpressionNode toAdd = new ExpressionNode(NodeType.EXPONENT, null, null);
            currentNode.setRight(toAdd);
            buildTree(elementArray, toAdd, alternator);
        }
        else
        {
            ExpressionNode operand = new ExpressionNode(Integer.parseInt(value));

            // If operand, place in opposite branch against another operand
            if(!alternator)
                currentNode.setLeft(operand);
            else
                currentNode.setRight(operand);

            buildTree(elementArray, currentNode, !alternator);
        }
    }


    /** The toString method takes the ExpressionTree, converts it into
     *  a string with the prefix expression, and then translates this into
     *  a human-readable infix expression.
     *  @return the infix expression (with parentheses) for the given expression tree
     */
    @Override
    public String toString()
    {
        // Instance Variables
        String infixExpression = "";
        String tempExpression = "";
        Deque<String> finalResult = new LinkedList<>();

        // If empty tree, return null
        if(root == null)
            return "0";

        // Create temporary node to use and gather its prefix expression
        ExpressionNode temp = root;
        String prefix = compilePrefixExpression(temp).trim();
        String[] arr = prefix.split("\\s+");

        int temp1 = arr.length - 1;

        // Generate infix expression using a stack
        while(temp1 >= 0)
        {
            String character = arr[temp1];
            temp1--;
            if(isOperator(character))
            {
                tempExpression += "(" + finalResult.pop() + " " + character + " " + finalResult.pop() + ")";
                finalResult.push(tempExpression);
            }
            else
            {
                finalResult.push(character);
            }
        }

        // Return built infix expression
        return finalResult.pop();
    }

    /** The helper method generates a string containing the expression tree's
     *  prefix expression recursively.
     *  @param node the current expression-node to read from
     *  @return the tree's prefix notation
     */
    public String compilePrefixExpression(ExpressionNode node)
    {

        // If no node found, return blank string
        if(node == null)
            return "";

        String toAdd = "";

        if(node.getType() == NodeType.NUMBER) {
            toAdd = String.valueOf(node.getValue());
        } else {
            toAdd = node.getType().getSymbol();
        }

        return toAdd + " " + compilePrefixExpression(node.getLeft()) + " " + compilePrefixExpression(node.getRight());
    }

    /** The helper method checks whether a given character is an operator.
     *  @param test the string to check for a operator
     *  @return whether the string is an operator or not
     */
    public boolean isOperator(String test)
    {
        if(test.equals(NodeType.MULTIPLY.getSymbol()))
            return true;
        else if(test.equals(NodeType.DIVIDE.getSymbol()))
            return true;
        else if(test.equals(NodeType.ADD.getSymbol()))
            return true;
        else if(test.equals(NodeType.SUBTRACT.getSymbol()))
            return true;
        else if(test.equals(NodeType.REMAINDER.getSymbol()))
            return true;
        else if(test.equals(NodeType.EXPONENT.getSymbol()))
            return true;
        else
            return false;
    }

    /** Finds value of expression. Currently not working.
     *  @return the value of the expression
     */
    public double evaluate()
    {
        return 0;
    }


}
