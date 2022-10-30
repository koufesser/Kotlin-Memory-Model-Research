package mpp.stack

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.loop

class TreiberStack<E> {
    private val top = atomic<Node<E>?>(null)

    /**
     * Adds the specified element [x] to the stack.
     */

    fun push(x: E) {
        top.loop { cur ->
            val newNode = Node(x, cur)
            if (top.compareAndSet(cur, newNode)) return
        }
    }

    /**
     * Retrieves the first element from the stack
     * and returns it; returns `null` if the stack
     * is empty.
     */


    fun pop(): E? {
        top.loop { cur ->
            if (cur == null)
                return null
            if (top.compareAndSet(cur, cur.next))
                return cur.x
        }
    }
}

private class Node<E>(val x: E, val next: Node<E>?)

private const val ELIMINATION_ARRAY_SIZE = 2 // DO NOT CHANGE IT