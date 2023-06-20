public class Node {
    public Node parent = this, childLeft = this, childRight = this;
    public Element element;

    public void printTree() {
        System.out.println(this.info());
        if (this.childLeft != this) {
            this.childLeft.printTree();
            this.childRight.printTree();
        }
    }

    public String info() {
        return "par: %s, this: %s, exp: %5s, childL: %s, childR: %s".formatted(parent, this, element, childLeft, childRight);
    }
}
