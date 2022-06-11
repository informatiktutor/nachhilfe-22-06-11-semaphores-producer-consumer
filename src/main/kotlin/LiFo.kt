interface LiFo<T> {
    fun pop(): T
    fun push(element: T)
}