#scala#模式匹配
来源：http://www.2gua.info/post/20

object PatternMatchingDemo {
    def main(args: Array[String]): Unit = {
        def matchingDemo(expr: Any) =
        expr match {
            case (a, b, c) => println("匹配具有三个元素的元组")
            case m: Map[_, _] => println("匹配映射")
            case e @ (a: Array[Int]) if a.length == 5 => e.map(_ * 2).foreach(println)
            case _ =>
        }

        matchingDemo((1, 2, "三"))
        matchingDemo(Map(1 -> "First", 2 -> "Second", 3 -> "Third"))
        matchingDemo(Array(2, 3, 5, 7, 11))
    }
}
输出：

匹配具有三个元素的元组
匹配映射
4
6
10
14
22
[Finished in 2.8s]

特别是：
case m: Map[_, _] => println("匹配映射")
case e @ (a: Array[Int]) if a.length == 5 => e.map(_ * 2).foreach(println)
这两行代码，要特别关注Type erasure（类型消除）。
只有数组是特例，可以指定具体元素类型。其他结构就不行了，比如Map。

还运用了变量绑定e @ (a: Array[Int])，变量绑定之后，可以用e来指代(a: Array[Int])
以及Pattern guards（模式守卫）if a.length == 5，
模式守卫通过条件表达式限制只有长度为5个元素的数组才满足匹配条件。