interface FiFo<T> {
    fun get(): T
    fun put(element: T)
}