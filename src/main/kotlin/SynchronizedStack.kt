// Literally das gleiche wie eine SynchronizedQueue<T>
// Nur statt addLast() wird addFirst() aufgerufen.
class SynchronizedStack<T>(capacity: Int) : LiFo<T>, AccessibleData<List<T>> {
    override fun data(): List<T> {
        TODO("Not yet implemented")
    }

    override fun pop(): T {
        TODO("Not yet implemented")
    }

    override fun push(element: T) {
        TODO("Not yet implemented")
    }
}