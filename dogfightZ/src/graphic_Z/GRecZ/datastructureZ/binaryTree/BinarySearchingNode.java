package graphic_Z.GRecZ.datastructureZ.binaryTree;

public class BinarySearchingNode<DataType extends Comparable<DataType>>
    implements Comparable<BinarySearchingNode<DataType>>, BinaryNodeRequire<BinarySearchingNode<DataType>> {
    
    protected DataType data;
    protected BinarySearchingNode<DataType> left;
    protected BinarySearchingNode<DataType> right;
    protected BinarySearchingNode<DataType> parent;
        
    public BinarySearchingNode(DataType data, BinarySearchingNode<DataType> left, BinarySearchingNode<DataType> right, BinarySearchingNode<DataType> parent) {
        this.data   = data;
        this.left   = left;
        this.right  = right;
        this.parent = parent;
    }
    
    public BinarySearchingNode(DataType data, BinarySearchingNode<DataType> left, BinarySearchingNode<DataType> right) {
        this(data, left, right, null);
    }
    
    public BinarySearchingNode(DataType data) {
        this(data, null, null);
    }
    
    public BinarySearchingNode() {
        this(null);
    }

    @Override
    public BinarySearchingNode<DataType> getParent() {
        return parent;
    }

    @Override
    public BinarySearchingNode<DataType> getLeftChild() {
        return left;
    }

    @Override
    public BinarySearchingNode<DataType> getRightChild() {
        return right;
    }

    @Override
    public int compareTo(BinarySearchingNode<DataType> o) {
        return data.compareTo(o.data);
    }
}