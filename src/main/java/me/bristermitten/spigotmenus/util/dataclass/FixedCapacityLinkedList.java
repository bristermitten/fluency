package me.bristermitten.spigotmenus.util.dataclass;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Extension of LinkedList with a fixed capacity of elements.
 * This ensures that {@link List#set(int, Object)}
 * can be called without an {@link IndexOutOfBoundsException}
 * However, a drawback of this is that the internal array must be a fixed,
 * unchanging size. LinkedLists use Nodes, not an array, but it's the best analogy.
 * This means that any empty values are represented by null values, which fit the capacity
 * Therefore, a FixedCapacityLinkedList with size 4 might look like this ["hello", "string 2", null, null],
 * rather than a normal LinkedList which would look like ["hello", "string 2"]
 * This is unfortunately unavoidable without this class becoming a full rewrite of LinkedList
 *
 * @param <E> the type of elements held in this collection
 */
public class FixedCapacityLinkedList<E> extends LinkedList<E> {

    private final int maxCapacity;

    public FixedCapacityLinkedList(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        ensureSize(maxCapacity);
    }

    public FixedCapacityLinkedList(Collection<E> copyOf) {
        this(copyOf.size(), copyOf);
    }

    public FixedCapacityLinkedList(int maxCapacity, Collection<E> copyOf) {
        this.maxCapacity = maxCapacity;
        if (copyOf.size() > maxCapacity) {
            throw new IllegalArgumentException("Collection to copy exceeds maximum capacity");
        }
        addEmptyToFill(maxCapacity);

        Iterator<E> iterator = copyOf.iterator();
        for (int i = 0; i < copyOf.size(); i++) {
            set(i, iterator.next());
        }
    }

    private void addEmptyToFill(int maxCapacity) {
        for (int i = 0; i < maxCapacity; i++) {
            add(null);
        }
    }

    private void ensureSize(int maxCapacity) {
        for (int i = 0; i < maxCapacity; i++) {
            if (get(i) != null) {
                continue;
            }
            set(i, null);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> a) {
        for (E e : a) {
            if (!add(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(E e) {
        if (size() >= maxCapacity) {
            return false;
        }
        return super.add(e);
    }

    @Override
    public E set(int slot, E e) {
        if (slot >= maxCapacity) {
            throw new IndexOutOfBoundsException("Slot exceeds maximum capacity");
        }
        return super.set(slot, e);
    }
}
