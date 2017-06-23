//sealed关键字意味着这个特质的所有实现都必须定义在这个文件里
//List是一个泛型的数据类型，类型参数用A表示。
sealed trait List[+A] // `List` data type, parameterized on a type, `A`
//用于表现空List的List数据构造器
case object Nil extends List[Nothing] // A `List` data Consor representing the empty list
/* Another data Consor, representing nonempty lists. Note that `tail` is another `List[A]`,
  which may be `Nil` or another `Cons`.
 * 另一个数据构造器，呈现非空List。注意尾部是另一个List[A],当然这个尾部也可能为Nil或另一个Cons。
 */
case class Cons[+A](head: A, tail: List[A]) extends List[A]

//List伴生对象。包含创建List和对List操作的一些函数。
object List { // `List` companion object. Contains functions for creating and working with lists.
  //利用模式匹配对一个整数型List进行求和。
  def sum(ints: List[Int]): Int =
    ints match { // A function that uses pattern matching to add up a list of integers
      case Nil => 0 // The sum of the empty list is 0.空列表的累加值为0
      //对一个头部是x的列表进行累加，这个过程是用x加上该列表剩余部分的累加值。
      case Cons(`x`, xs) =>
        x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
    }

  //乘积
  def product(ds: List[Double]): Double = ds match {
    case Nil          => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(`x`, xs)  => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax可变参数
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  /** List(1, 2, 3, 4, 5)
    * 等同于
    * Cons(1,Cons(2,Cons(3,Cons(4,Cons(5,Nil)))))
    * 如果目标与多个模式都匹配，scala选择第一个匹配的。
    */
  val x: Int = List(1, 2, 3, 4, 5) match {
    case Cons(`x`, Cons(2, Cons(4, _)))          => x
    case Nil                                   => 42
    case Cons(`x`, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t)                            => h + sum(t)

    /** 推导过程
      * 1 + sum(Cons(2,Cons(3,Cons(4,Cons(5,Nil)))))
      * 1 + (2 + sum(Cons(3,Cons(4,Cons(5,Nil)))))
      * 1 + (2 + (3 + sum(4,Cons(5,Nil))))
      * 1 + (2 + (3 + (4 + sum(Cons(5,Nil)))))
      * 1 + (2 + (3 + (4 + (5 + sum(Nil,0)))))
      * 1 + (2 + (3 + (4 + (5 + (0)))))
      */
    case _ => 101
  }

  //实现tail函数，删除一个List的第一个元素
  def tail[A](l: List[A]): List[A] =
    l match {
      case Nil        => sys.error("tail of empty list")
      case Cons(_, t) => t
    }

  //使用相同的思路，实现函数setHead用一个不同的值替代列表中的第一个元素
  def setHead[A](l: List[A], h: A): List[A] = l match {
    case Nil        => sys.error("setHead on empty list")
    case Cons(_, t) => Cons(h, t)
  }

  //把tail泛化为drop函数，用于从列表中删除前n个元素
  def drop[A](l: List[A], n: Int): List[A] =
    if (n <= 0) l
    else
      l match {
        case Nil        => Nil
        case Cons(_, t) => drop(t, n - 1)
      }

  //实现dropWhile函数，删除列表中前缀全部符合判定的元素
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Cons(h, t) if f(h) => dropWhile(t, f)
      case _                  => l
    }

  //利用数据共享引用的特性将一个列表的所有元素加到另一个列表的后面
  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil        => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  //右折叠的简单运用
  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    as match {
      case Nil         => z
      case Cons(x: A, xs) => f(x, v2 = foldRight(xs, z)(f))
    }

  /** 有两个参数的函数可以表现为：
    * 只接收一个参数，返回另一个函数，这个返回的函数用另一个参数作它的参数。
    * 如果as:List中的值为Nil，则返回z，否则返回Cons。
    */
  def sum2(ns: List[Int]): Int = {
    foldRight(ns, 0)((x, y) => x + y)
  }

  def product2(ns: List[Double]): Double =
    foldRight(ns, 1.0)(_ * _) //`_ * _`对于`(x,y) => x * y`是更简洁的写法

  //实现一个init函数，返回一个列表，它包含原列表中除了最后一个元素之外的所有元素。
  def init[A](l: List[A]): List[A] =
    l match {
      case Nil          => sys.error("init of empty list")
      case Cons(_, Nil) => Nil
      case Cons(h, t)   => Cons(h, init(t))
    }

  def init2[A](l: List[A]): List[A] = {
    import collection.mutable.ListBuffer
    val buf = new ListBuffer[A]

    @annotation.tailrec
    def go(cur: List[A]): List[A] = cur match {
      case Nil          => sys.error("init of empty list")
      case Cons(_, Nil) => List(buf.toList: _*)
      case Cons(h, t)   => buf += h; go(t)
    }

    go(l)
  }

  /*
    foldRight(Cons(1, Cons(2, Cons(3, Nil))), Nil:List[Int])(Cons(_,_))
    Cons(1, foldRight(Cons(2, Cons(3, Nil)), Nil:List[Int])(Cons(_,_)))
    Cons(1, Cons(2, foldRight(Cons(3, Nil), Nil:List[Int])(Cons(_,_))))
    Cons(1, Cons(2, Cons(3, foldRight(Nil, Nil:List[Int])(Cons(_,_)))))
    Cons(1, Cons(2, Cons(3, Nil)))
   */

  //使用foldRight计算List的长度
  def length[A](l: List[A]): Int =
    foldRight(l, 0)((_, acc) => acc + 1)

  //foldRight(l, (0))((_: A) => acc.+(1)))

  /*
    通常的做法是使用`tailrec'注释来注释你希望被尾递归的函数。 如果函数不是尾递归的，它将产生一个编译错误，而不是静默地编译代码，
    并在运行时导致更大的堆栈空间使用。
   */
  @annotation.tailrec
  def foldLeft[A, B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil        => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f)
  }

  //用foldeLeft实现之前写的sum、product、length函数。
  def sum3(l: List[Int]): Int = foldLeft(l, 0)(_ + _)

  //def sum3(l: List[Int]): Int = return (i1: Int, i2: Int) => foldLeft(l, (0))(i1.+(i2))

  def product3(l: List[Double]): Double = foldLeft(l, 1.0)(_ * _)

  /*def product3(l: List[Double]): Double =
    return (d1: Double, d2: Double) => foldLeft(l, (1.0))(d1.*(d2))*/

  def length2[A](l: List[A]): Int = {
    foldLeft(l, 0)((acc, _) => acc + 1)
  }

  //def length2[A](l: List[A]): Int = foldLeft(l, (0))(acc => (acc).+(1))

  //写一个对原列表元素反转顺序的函数
  def reverse[A](l: List[A]): List[A] =
    foldLeft(l, List[A]())((acc, h) => Cons(h, acc))

  //  def reverse[A](l: List[A]): List[A] =
  //    foldLeft(l, List.apply()[A]())((acc: List[A]) => Cons.apply(h, acc))

  //根据foldRight来写一个foldLeft
  def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(reverse(l), z)((b, a) => f(a, b))

  //  def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: Function2[A, B, B]): B =
  //    foldLeft(reverse(l), z)((b: B) => f.apply(a, b))

  def foldRightViaFoldLeft_1[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(l, (b: B) => b)((g, a) => b => g(f(a, b)))(z)

  //  def foldRightViaFoldLeft_1[A, B](l: List[A], z: B)(f: Function2[A, B, B]): B =
  //    foldLeft(l, (b: B) => b)((g: Function1[B, B]) => (b: B) => g.apply(f.apply(a, b)))(z)

  def foldLeftViaFoldRight[A, B](l: List[A], z: B)(f: (B, A) => B): B =
    foldRight(l, (b: B) => b)((a, g) => b => g(f(b, a)))(z)

  //  def foldLeftViaFoldRight[A, B](l: List[A], z: B)(f: Function2[B, A, B]): B =
  //    foldRight(l, (b: B) => b)((a: A) => (b: B) => g.apply(f.apply(b, a)))(z)

  /*
    `append` simply replaces the `Nil` Consor of the first list with the second list, which is exactly the operation performed by `foldRight`.foldRitht实现append函数
   */
  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] =
    foldRight(l, r)(Cons(_, _))

  //  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] =
  //    (a: A, tail: List[A_]) => foldRight(l, r)(() => Cons.apply(a, tail))

  //写一个函数用来将一组列表连接成一个单个列表
  def concat[A](l: List[List[A]]): List[A] =
    foldRight(l, Nil: List[A])(append)

  //写一个函数，用来转换一个整数列表，对每个元素加1（记住这应该是一个纯函数，返回一个新列表）。
  def add1(l: List[Int]): List[Int] =
    foldRight(l, Nil: List[Int])((h, t) => Cons(h + 1, t))

  //  def add1(l: List[Int]): List[Int] =
  //    foldRight(l, Nil: List[Int])((h: Int) => Cons.apply(h.+(1), t))

  //写一个函数，将List[Double]中的每一个值转为String
  def doubleToString(l: List[Double]): List[String] =
    foldRight(l, Nil: List[String])((h, t) => Cons(h.toString, t))

  //  def doubleToString(l: List[Double]): List[String] =
  //    foldRight(l, Nil: List[String])((h: Double) => Cons.apply(h.toString, t))

  /** 写一个泛化的map函数，对列表中的每个元素进行修改，并维持列表结构。
    *
    * 我们可以使用`foldRightViaFoldLeft`来避免堆栈溢出（变体1），但更常见的是，
    * 使用我们当前的“List”实现，“map”将仅使用本地突变（变体2）来实现。
    * 再次注意到，这个突变在函数之外是不可观察的，因为我们只是为了分配我们分配的缓冲区而变异。
    */
  def map[A, B](l: List[A])(f: A => B): List[B] =
    foldRight(l, Nil: List[B])((h, t) => Cons(f(h), t))

  def map_1[A, B](l: List[A])(f: A => B): List[B] =
    foldRightViaFoldLeft(l, Nil: List[B])((h, t) => Cons(f(h), t))

  def map_2[A, B](l: List[A])(f: A => B): List[B] = {
    val buf = new collection.mutable.ListBuffer[B]

    def go(l: List[A]): Unit = l match {
      case Nil        => ()
      case Cons(h, t) => buf += f(h); go(t)
    }

    go(l)
    List(buf.toList: _*) // 从标准的Scala列表转换到我们在这里定义的列表
  }

  //关于“map”的讨论也适用于此。

  //写一个filter函数，从列表中删除所有不满足断言的元素，并用它删除一个List[Int]中的所有奇数。
  def filter[A](l: List[A])(f: A => Boolean): List[A] =
    foldRight(l, Nil: List[A])((h, t) => if (f(h)) Cons(h, t) else t)

  def filter_1[A](l: List[A])(f: A => Boolean): List[A] =
    foldRightViaFoldLeft(l, Nil: List[A])((h, t) =>
      if (f(h)) Cons(h, t) else t)

  def filter_2[A](l: List[A])(f: A => Boolean): List[A] = {
    val buf = new collection.mutable.ListBuffer[A]

    def go(l: List[A]): Unit = l match {
      case Nil        => ()
      case Cons(h, t) => if (f(h)) buf += h; go(t)
    }

    go(l)
    List(buf.toList: _*) // converting from the standard Scala list to the list we've defined here
  }

  /** 写一个flatMap函数，它跟map函数有些像，除了传入的函数f返回的是列表而非单个结果。
    * 这个f所返回的列表会被塞到flatMap最终所返回的列表。
    * 例如：flatMap(List(1, 2, 3)) (i => (i, i)) 结果是 List(1, 1, 2, 2, 3, 3)
    *
    * This could also be implemented directly using `foldRight`.
    * 这也可以直接使用`foldRight`实现。
    */
  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] =
    concat(map(l)(f))

  //用flatMap实现filter
  def filterViaFlatMap[A](l: List[A])(f: A => Boolean): List[A] =
    flatMap(l)(a => if (f(a)) List(a) else Nil)

  /** 写一个函数，接收2个列表，通过对相应元素的相加构造出一个新的列表。
    * 比如，List(1, 2, 3)和List(4, 5, 6)构造出List(5, 7, 9)
    */
  def addPairwise(a: List[Int], b: List[Int]): List[Int] = (a, b) match {
    case (Nil, _)                     => Nil
    case (_, Nil)                     => Nil
    case (Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, addPairwise(t1, t2))
  }

  /** 这个功能通常称为`zipWith`。 关于“map”解释的堆栈使用的讨论也适用于此。
    * 通过将`f`放在第二个参数列表中，Scala可以从先前的参数列表中推断出它的类型。
    */
  def zipWith[A, B, C](a: List[A], b: List[B])(f: (A, B) => C): List[C] =
    (a, b) match {
      case (Nil, _)                     => Nil
      case (_, Nil)                     => Nil
      case (Cons(h1, t1), Cons(h2, t2)) => Cons(f(h1, h2), zipWith(t1, t2)(f))
    }

  /*

   */
  @annotation.tailrec
  def startsWith[A](l: List[A], prefix: List[A]): Boolean = (l, prefix) match {
    case (_, Nil)                              => true
    case (Cons(h, t), Cons(h2, t2)) if h == h2 => startsWith(t, t2)
    case _                                     => false
  }

  /** 实现hasSubsequence方法，检测一个List子序列是否包含另一个List，
    * 比如List(1, 2, 3, 4)包含的子序列有List(1, 2)、List(2, 3)和List(4)等。
    * 注意：任意两个值x和y在scala中可以用表达式`x == y`来比较它们是否相等。
    */
  @annotation.tailrec
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
    case Nil                       => sub == Nil
    case _ if startsWith(sup, sub) => true
    case Cons(_, t)                => hasSubsequence(t, sub)
  }

}
