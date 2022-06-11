import kotlin.concurrent.thread

fun main() {
    val q1 = SynchronizedQueue<Int>(100)
    val items = mutableListOf<Int>()

    val range = 1..1024

    val producer = thread {
        // Thread.sleep(100)
        for (i in range) {
            q1.put(i)
            Thread.sleep(1) // Change me!
        }
    }
    val consumer = thread {
        for (i in range) {
            val value = q1.get()
            items.add(value)
            println("$value ${q1.data().size}")
        }
    }

    producer.join()
    consumer.join()

    items.sort()
    assert(items[0] == range.first)
    assert(items[items.size - 1] == range.last)
    assert(items.size == range.last - range.first + 1)
}
