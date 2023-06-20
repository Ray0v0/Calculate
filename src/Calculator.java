import java.util.ArrayList;

public class Calculator {

    public static double calculate(String expStr) throws Exception{

        ArrayList<Element> elements = Element.getElements(expStr);

        System.out.printf("size: %d\n", elements.size());
        for (Element element : elements) {
            System.out.print(element);
        }
        System.out.println();

        Node node = buildTree(elements);
        node.printTree();

        return calculate(node);
    }

    public static double calculate(Node node) throws Exception{
        double ret;

        if (node.element.category == Element.NUMBER) {
            ret = node.element.value;
        } else {
            double left = calculate(node.childLeft);
            double right = calculate(node.childRight);

            switch (node.element.identity) {
                case 0:
                    ret = left + right;
                    break;
                case 1:
                    ret = left - right;
                    break;
                case 2:
                    ret = left * right;
                    break;
                case 3:
                    ret = left / right;
                    break;
                default:
                    throw new Exception("UndefinedOperator");
            }
        }

        return ret;
    }

    public static Node buildTree(ArrayList<Element> elements) throws Exception{
        Node node = new Node();
        Node topNode = node;

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (element.category == Element.NUMBER) {
                if (node.element == null) {
                    node.element = element;
                } else {
                    throw new Exception("IllegalExpressionException");
                }
            } else if (element.category == Element.PARENTHESES) {
                if (node.element == null) {
                    int domainEnd = matchParentheses(elements, i);
                    ArrayList<Element> insideElements = new ArrayList<>();
                    for (int j = i + 1; j < domainEnd; j++) {
                        insideElements.add(elements.get(j));
                    }

                    Node subTopNode = buildTree(insideElements);

                    if (node.parent != node) {
                        subTopNode.parent = node.parent;
                        node.parent.childRight = subTopNode;
                    } else {
                        topNode = subTopNode;
                    }

                    node = subTopNode;

                    i = domainEnd;
                } else {
                    throw new Exception("IllegalExpressionException");
                }
            } else if (element.category == Element.OPERATOR) {
                if (node.element != null) {
                    while (node.parent != node && element.value < node.parent.element.value) {
                        node = node.parent;
                    }
                    if (node == node.parent) {
                        Node newNode = new Node();
                        newNode.element = element;
                        topNode = newNode;

                        newNode.childLeft = node;
                        node.parent = newNode;

                        newNode.childRight = new Node();
                        newNode.childRight.parent = newNode;

                        node = newNode.childRight;
                    } else {
                        Node newNode = new Node();
                        newNode.element = element;

                        newNode.parent = node.parent;
                        node.parent.childRight = newNode;

                        newNode.childLeft = node;
                        node.parent = newNode;

                        newNode.childRight = new Node();
                        newNode.childRight.parent = newNode;

                        node = newNode.childRight;
                    }
                } else {
                    throw new Exception("IllegalExpressionException");
                }
            }
        }

        return topNode;
    }

    public static int matchParentheses(ArrayList<Element> elements, int indexLeft) throws Exception{
        Element element = elements.get(indexLeft);
        int indexRight = indexLeft;

        if (element.identity == Element.PAR_RIGHT) {
            throw new Exception("IllegalParenthesesException");
        }

        int countParentheses = 1;
        for (int j = indexLeft + 1; j < elements.size(); j++) {
            if (countParentheses < 0) {
                throw new Exception("IllegalParenthesesException");
            }
            if (elements.get(j).category == Element.PARENTHESES) {
                if (elements.get(j).identity == Element.PAR_LEFT) {
                    countParentheses++;
                } else {
                    countParentheses--;
                    if (countParentheses == 0) {
                        indexRight = j;
                        break;
                    }
                }
            }
        }
        if (countParentheses != 0) {
            throw new Exception("IllegalParenthesesException");
        }
        return indexRight;
    }
}