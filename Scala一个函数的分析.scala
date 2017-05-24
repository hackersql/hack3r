#Scala#一个函数的分析

scala> def twice(op: Double => Double, x: Double) = op(op(x))
twice: (op: Double => Double, x: Double)Double
scala> twice(_ + 1, 5)
res0: Double = 7.0
乍一看这句代码，有点晕乎的感觉。

好好分析一下，其实很简单：

第一句定义了一个函数twice，参数有两个：

第一个参数是Lambda表达式，op: Double => Double，其传入一个Double类型参数，返回值也是Double类型；
第二个参数x也是Double类型。

函数体op(op(x))里面一层的op(x)表明将上面的x参数传给op Lambda表达式，也就是x: Double => Double；op(x)返回值是Double，姑且假设返回值是y；之后再次把这个y值传给op Lambda表达式，即y: Double => Double。

对应到第二句，那就更具体了，就按上一句照翻（伪代码）：

函数体op(op(x))里面一层的op(x)表明将x参数（5）传给op Lambda表达式（这时具体的Lambda表达式就是：(_: Double) => _ + 1），也就是5.0: Double => _ + 1
（整数5自动转换成了Double类型；_是占位符，在这里代表x值5.0）；op(x)返回值是5.0 + 1 = 6.0；
之后再次把这个6.0（y值）传给op Lambda表达式，即6.0: Double => _ + 1，最终计算并返回6.0 + 1 = 7.0。