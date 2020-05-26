package com.demo;

import com.demo.datastructure.tree.ExpressionNode;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.selection.BitmapBackedSelection;
import tech.tablesaw.selection.Selection;

/**
 * Created by Administrator on 2020/5/26.
 */
public class TestParseSql {

    @org.junit.Test
    public void testParseSql() throws JSQLParserException {
        String sql = "select * from test where id > 10 and (id < 100 or id = 200)";

        Statement statement = CCJSqlParserUtil.parse(sql);

        Select select = (Select)statement;

        System.out.println(select.toString());

        SelectBody selectBody = select.getSelectBody();
//        System.out.println(selectBody.toString());
        PlainSelect plainSelect = (PlainSelect)selectBody;
        Expression where = plainSelect.getWhere();
//        System.out.println(where.getClass().getName());
        ExpressionNode expressionNode = new ExpressionNode(where);
        setExpression(expressionNode,where);

        System.out.println(expressionNode.getData().toString());
        Selection selection = new BitmapBackedSelection();
        inOrderTraverse(expressionNode,null,null);
    }

    DoubleColumn doubleColumn = DoubleColumn.create("test");
    public Selection inOrderTraverse(ExpressionNode root,Selection selection1,Selection selection2) {
        if (root.getData() instanceof ComparisonOperator){
            if (selection1 == null){
                 selection1 = doubleColumn.isEqualTo(0D);
                return selection1;
            }
            if (selection2 == null){
                selection2 = doubleColumn.isEqualTo(1D);
                return selection2;
            }
        }

        if (root.getData() instanceof AndExpression){
            if (selection1 != null && selection2 != null){
                selection1.and(selection2);
            }
            inOrderTraverse(root.getLeft(),selection1,selection2);
            inOrderTraverse(root.getRight(),selection1,selection2);
        }
        if (root.getData() instanceof OrExpression){
            if (selection1 != null && selection2 != null){
                selection1.and(selection2);
            }
            selection1 = inOrderTraverse(root.getLeft(),selection1,selection2);
            inOrderTraverse(root.getRight(),selection1,selection2);
        }
        return null;
    }

    public void setExpression(ExpressionNode expressionNode, Expression expression){
        if (expression instanceof AndExpression){
            AndExpression andExpression = (AndExpression)expression;
            Expression leftExpression = andExpression.getLeftExpression();
            Expression rightExpression = andExpression.getRightExpression();
            ExpressionNode left = new ExpressionNode(leftExpression);
            ExpressionNode right = new ExpressionNode(rightExpression);
            expressionNode.setLeft(left);
            expressionNode.setRight(right);
            setExpression(left,leftExpression);
            setExpression(right,rightExpression);
        }
        if (expression instanceof OrExpression){
            OrExpression orExpression = (OrExpression)expression;
            Expression leftExpression = orExpression.getLeftExpression();
            Expression rightExpression = orExpression.getRightExpression();
            ExpressionNode left = new ExpressionNode(leftExpression);
            ExpressionNode right = new ExpressionNode(rightExpression);
            expressionNode.setLeft(left);
            expressionNode.setRight(right);
            setExpression(left,leftExpression);
            setExpression(right,rightExpression);
        }
        if (expression instanceof Parenthesis){
            Parenthesis parenthesis = (Parenthesis)expression;
            Expression expression1 = parenthesis.getExpression();
            System.out.println(expression1.toString());
            setExpression(expressionNode,expression1);
        }
        if (expression instanceof ComparisonOperator){
            if (expressionNode.getLeft() == null){
                ExpressionNode expressionNode1 = new ExpressionNode(expression);
                expressionNode.setLeft(expressionNode1);
            }else {
                ExpressionNode expressionNode1 = new ExpressionNode(expression);
                expressionNode.setRight(expressionNode1);
            }
        }
    }
}
