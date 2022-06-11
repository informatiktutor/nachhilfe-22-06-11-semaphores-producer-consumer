import java.util.concurrent.Semaphore

class SynchronizedQueue<T>(capacity: Int) : FiFo<T>, AccessibleData<List<T>> {
    private val data = ArrayDeque<T>(capacity)

    // Wechselseitiger Ausschluss, sodass die Datenstruktur
    // zu jedem Zeitpunkt von nur einem Thread verändert wird.
    private val mutex = Semaphore(1)

    // Zähler wird erhöht, sobald ein Element der Schlange hinzugefügt wird.
    // Wenn das passiert, wird ein Thread welcher acquire() aufgerufen hat
    // freigegeben und kann mit removeFirst() ein Element entnehmen.
    // Dies sorgt dafür, dass nur dann ein Element entnommen wird,
    // wenn auch eines vorhanden ist.
    private val lockAvailable = Semaphore(0)

    // Zähler wird verringert, bevor ein Element hinzugefügt wird.
    // Sobald der Zähler negativ wird, werden keine Elemente mehr hinzugefügt,
    // da der Thread blockiert wird bis ein Element entnommen wurde.
    private val lockFull = Semaphore(capacity)

    override fun get(): T {
        lockAvailable.acquire() // Warten bis ein Element verfügbar ist
        mutex.acquire() // Wechselseitiger Ausschluss
        val value = data.removeFirst() // Queue
        // val value = data.removeLast() // Stack
        mutex.release() // Wechselseitiger Ausschluss
        lockFull.release() // Ein Platz ist wieder frei
        return value
    }

    override fun put(element: T) {
        lockFull.acquire() // Ein Platz wird belegt
        mutex.acquire() // Wechselseitiger Ausschluss
        data.addLast(element)
        mutex.release() // Wechselseitiger Ausschluss
        lockAvailable.release() // Ein Element ist verfügbar
    }

    override fun data(): List<T> {
        mutex.acquire()
        val result = data.toList()
        mutex.release()
        return result
    }
}
