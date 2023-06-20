import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Element {

    public static final int NUMBER = 0, OPERATOR = 1, PARENTHESES = 2;
    public static final int OPE_PLUS = 0, OPE_MINUS = 1, OPE_MULTI = 2, OPE_DIV = 3;
    public static final int PAR_LEFT = 0, PAR_RIGHT = 1;

    public static int outMark = 0;

    public int category = -1;
    public int identity = -1;
    public double value = 0;

    public Element(String expStr, int pointer) throws Exception {

        char[] expCharArr = expStr.toCharArray();

        while (expCharArr[pointer] == ' ') {
            pointer++;
        }

        char c = expCharArr[pointer];

        if (par(c)) {
            category = PARENTHESES;

            if (c == '(') {
                identity = PAR_LEFT;
            } else {
                identity = PAR_RIGHT;
            }

        } else if (num(c)) {

            category = NUMBER;
            identity = 0;
            value = getNumFromCharArr(expCharArr, pointer);

        } else {

            category = OPERATOR;
            identity = ope(c);
            if (identity != -1) {
                value = Operator.values()[identity].priority;
            } else {
                throw new Exception("UnknownCharacterException");
            }

        }
    }

    public static boolean par(char c) {
        return c == '(' || c == ')';
    }

    public static boolean num(char c) {
        return '0' <= c && c <= '9';
    }

    public static int ope(char c) {
        int identity = -1;
        Operator[] operators = Operator.values();
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].expChar == c) {
                identity = i;
                break;
            }
        }
        return identity;
    }

    public static double getNumFromCharArr(char[] charArr, int head) {
        double value = 0;
        int pointer = head;
        while (num(charArr[pointer]) || charArr[pointer] == '.') {
            pointer++;
            if (pointer >= charArr.length) {
                break;
            }
        }
        outMark = pointer;

        value = Double.parseDouble(new String(charArr).substring(head, pointer));
        return value;
    }

    public static String delBlank(String expStr) {
        char[] expCharArr = expStr.toCharArray();
        ArrayList<Character> charArrayList = new ArrayList<>();

        for (char c : expCharArr) {
            if (c != ' ') {
                charArrayList.add(c);
            }
        }

        char[] newCharArr = new char[charArrayList.size()];
        for (int i = 0; i < charArrayList.size(); i++) {
            newCharArr[i] = charArrayList.get(i);
        }

        return new String(newCharArr);
    }

    public static ArrayList<Element> getElements(String expStr) throws Exception {
        ArrayList<Element> elements = new ArrayList<>();

        expStr = delBlank(expStr);
        int pointer = 0;

        while (pointer < expStr.length()) {
            Element element = new Element(expStr, pointer);
            elements.add(element);
            if (element.category == NUMBER) {
                pointer = outMark;
            } else {
                pointer++;
            }
        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).category == OPERATOR && elements.get(i).identity == OPE_MINUS) {
                if (i == 0 || elements.get(i - 1).category == OPERATOR || (elements.get(i - 1).category == PARENTHESES && elements.get(i - 1).identity == PAR_LEFT)) {
                    if (elements.get(i + 1).category == NUMBER) {
                        elements.get(i + 1).value *= -1;
                        elements.remove(i);
                    }
                }
            }
        }

        return elements;
    }

    public String toString() {
        String ret;
        if (this.category == NUMBER) {
            ret = Double.toString(this.value);
        } else if (this.category == OPERATOR) {
            ret = Character.toString(Operator.values()[this.identity].expChar);
        } else {
            if (this.identity == PAR_LEFT) {
                ret = "(";
            } else {
                ret = ")";
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String testStr = in.nextLine();

        System.out.println(getNumFromCharArr(testStr.toCharArray(), 0));
    }
}
