import kotlin.math.max

data class Node(val num: Int, var left: Node? = null, var right: Node? = null, var height: Int)

fun main() {
    // 40, 20, 10, 25, 30, 22, 50
    var root: Node? = null
    root = insert(root, 40)
    root = insert(root, 20)
    root = insert(root, 10)
    root = insert(root, 25)
    root = insert(root, 30)
    root = insert(root, 22)
    root = insert(root, 50)
    preOrder(root)
}

fun preOrder(root: Node?) {
    root ?: return

    println(root.num)
    preOrder(root.left)
    preOrder(root.right)
}

fun insert(root: Node?, key: Int): Node {
    // 첫 insert 할 때만 호출
    if (root == null) return Node(key, null, null, 1)

    if (key < root.num) root.left = insert(root.left, key)
    else if (key > root.num) root.right = insert(root.right, key)

    root.height = 1 + max(getHeight(root.left), getHeight(root.right))

    val balance = getBalance(root)
    // 새로운 root 반환
    if (balance > 1 && key < root.left!!.num) return LL_Rotate(root)
    if (balance < -1 && key > root.right!!.num) return RR_Rotate(root)
    if (balance > 1 && key > root.left!!.num) return LR_Rotate(root)
    if (balance < -1 && key < root.right!!.num) return RL_Rotate(root)

    return root
}

fun getHeight(node: Node?): Int {
    return node?.height ?: 0
}

fun getBalance(node: Node): Int {
    return getHeight(node.left) - getHeight(node.right)
}

fun LL_Rotate(root: Node): Node {
    val leftChild = root.left!!
    val leftRightChild = leftChild.right

    leftChild.right = root
    root.left = leftRightChild

    root.height = max(getHeight(root.left), getHeight(root.right)) + 1
    leftChild.height = max(getHeight(leftChild.left), getHeight(leftChild.right)) + 1

    // new root
    return leftChild
}

fun RR_Rotate(root: Node): Node {
    val rightChild = root.right!!
    val rightLeftChild = rightChild.left

    rightChild.left = root
    root.right = rightLeftChild

    root.height = max(getHeight(root.left), getHeight(root.right)) + 1
    rightChild.height = max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1

    // new root
    return rightChild
}

fun LR_Rotate(root: Node): Node {
    root.left = RR_Rotate(root.left!!)
    return LL_Rotate(root)
}

fun RL_Rotate(root: Node): Node {
    root.right = LL_Rotate(root.right!!)
    return RR_Rotate(root)
}