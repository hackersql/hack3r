1.如果没有发现任何显式的返回语句，scala方法将返回方法中最后一个计算得到的值。

2.在scala方法定义中带有大括号但没有等号的，默认返回结果类型为Unit。
scala> def f {"this String gets retrned"}
rs0:   f: Unit

带有等号会自动推断出返回类型
scala> def f = {"this String gets retrned"}
rs1:   f: String

3.类中的字段和方法可以用this关键字访问，父类中的字段和方法可以用super关键字访问。
如果一个子类中的方法需要访问其父类中的相应方法，就要用到super关键字。

4.class 类名([val|var] 参数名: <参数类型>[,...可选的更多个参数]) 
             [extends <扩展类名>(<输入参数>)] 
             [{ 字段和方法 }]

5.为函数参数指定默认值
def <方法/函数名> ([<必要参数在前>:<参数类型>], [<默认参数名在后>: <参数类型> = <参数的默认值>]) : <返回值的类型> 
def greet(name: String, prefix: String = "default") =  s"$prefix$name" 

6.定义无输入的函数 def <function-name> = <expression block> def hi = "hi" def hi:String = "hi"
7.在函数体中最后一行将成为表达式的返回值，相应地也是函数的返回值。

8.隐式的返回类型 def log(d: Double) = println(f"Got value $d%.2f")
9.显式的返回类型 def log(d: Double)：Unit = println(f"Got value $d%.2f")
10.定义时不指定返回类型，而且过程体前没有等号 def log(d: Double) { println(f"Got value $d%.2f") } 非正式地废弃这个语法

11.用空括号定义函数 def <function-name>() [: <返回值类型>] = <expression block> 

12.要标志一个参数匹配一个或多个输入实参，在函数定义中需要该参数类型后面增加一个星号（*）。
def sum(items: Int*): Int = { }    sum(10, 20, 30, ...)

13.可以把参数表分解为参数组（parameter groups），每个参数组分别用小括号分隔。
scala> def max(x: Int)(y: Int) = if(x > y) x else y
max: (x: Int)(y: Int)Int
对函数字面量使用参数组时不会产生副作用
scala> def max(x: Int, y: Int) = if(x > y) x else y
max: (x: Int, y: Int)Int

14.尾递归recursive只有最后一个语句是递归调用的函数才能由scala编译器完成尾递归优化。
如果调用函数本身的结果不作为直接返回值，而是有其他用途，这个函数不能优化。
可以利用函数注解（function annotation）来标志一个函数将完成尾递归优化@annotation.tailrec
def power(x: Int, n: Int, t: Int = 1) : Int = {
    if(n < 1) t 
    else power(x, n-1, x * t)    
}
power: (x: Int, n: Int, t: Int)Int

15.定义函数的类型参数 def <function-name>[](<paramete-name>: <type-name>): 返回值类型 = expression block
scala> def identity(s: String): String = s
identity: (s: String)String
假设希望一个函数只返回它的输入（这称为同一性函数） 在这里输入定义为一个String,但是它只能返回String类型。
没有办法对其他类型（如Int）调用这个函数，除非再定义另外一个函数：
def identity(s: Int): Int = s
identity: (s: Int)Int
为了解决这类问题我们想到一个办法，能不能使用根类型Any呢？这个Any类型适用所有类型。
scala> def identity(a: Any): Any = a
identity: (a: Any)Any
我们原想把结果赋给一个String，但是由于函数的返回类型是Any，所以导致了一个编译错误类型不匹配
scala> val s: String = identity("Hello Scala")
<console>:8: error: type mismatch;
 found   : Any
 required: String
       val s: String = identity("Hello Scala")
如何解决这个问题？不必为使用特定的类型（如String或Int）来定义函数，也不要使用一个通用的“根”类型（如Any）
可以将类型参数化，使它适用于任何类型呢。
下面是使用类型参数定义的同一性函数，这样它就可以用于你提供的任何类型了：
scala> def identity[T](a: T): T = a
identity: [T](a: T)T      
这里的类型T是一个表示通用的输入类型
scala> val s: String = identity[String]("Hello Scala")
s: String = Hello Scala

scala> val s: String = identity("Hello Scala")
s: String = Hello Scala
scala会自动推导出类型参数的类型，以及返回值的值的类型。
scala> val s = identity("Hello Scala")
s: String = Hello Scala 

16.用中缀点记法调用方法 <class instance>.<method-name>[(<paramete-name>)]  类实例名.方法名(参数值)
17.操作符记法 <objec-name> <method-name> <paramete-name> 
NOTE：和中缀点记法不同的是操作符记法是使用空格来分隔对象、操作符方法和方法的参数（要求方法只有一个参数）
每次写2+3时，scala编译器会把它识别为操作符记法，并相应地处理，就好像写为2.+(3)一样，这里调用了值为2的
一个Int加法方法，并提供参数3，最后会返回值5.

18.函数字面量 ([<参数名>: <类型>, ... ]) => 条件表达式
scala> def max(a: Int, b: Int) = if (a > b) a else b
max: (a: Int, b: Int)Int
函数赋值
scala> val maximize: (Int, Int) => Int = max
maximize: (Int, Int) => Int = <function2>
函数字面量
scala> val maximize = (a: Int, b: Int) => if (a > b) a else b
maximize: (Int, Int) => Int = <function2>

scala> def logstart() = "=" * 50 + "\nStarting NOW\n" + "=" * 50
logstart: ()String
函数赋值
scala> val start:() => String = logstart
start: () => String = <function0>
函数字面量
scala> val start = () => "=" * 50 + "\nStarting NOW\n" + "=" * 50
start: () => String = <function0>

scala> println(start())
==================================================
Starting NOW
==================================================

REPL将函数字面量称为function0，这是无输入的函数的名字。不过它不是值类型，
实际上类型推到为 ( ) => String 这是一个返回字符串的无输入函数。

scala> def safeString(s: String, f: String => String) = {
     |   if(s != null) f(s) else s
     | }
safeString: (s: String, f: String => String)String

scala> safeString(null, (s: String) => s.reverse)
res4: String = null

scala> safeString("Ready", (s: String) => s.reverse)
res5: String = ydaeR
由于已经定义了函数参数f的类型为String => String,所以可以从函数字面量中删除显式类型，
因为编译器可以推导出它的类型，如果删除显式类型意味着我们可以从函数字面量中去除小括号，
因为对于单个无类型的输入没有必要加小括号，简化其写法。
scala> safeString("Ready", s => s.reverse)
res6: String = ydaeR
占位符语法调用
scala> safeString("Ready", _.reverse)
res7: String = ydaeR

19.定义一个满足任何输入类型的函数
scala> def triple[A,B](a: A, b: A, c: A, f: (A, A, A) => B) = f(a, b, c)
triple: [A, B](a: A, b: A, c: A, f: (A, A, A) => B)B
A表示通用的输入类型 A => B，B表示返回值类型
scala> triple[Int, Int](23, 92, 14, _ * _ + _)
res8: Int = 2130

scala> triple[Int, Double](23, 92, 14, 2.0 * _ / _ / _)
res16: Double = 0.03571428571428571

scala> triple[Int, Boolean](23, 92, 14, _ < _ + _)
res14: Boolean = true

20.函数柯里化
scala> def factor(x: Int)(y: Int) = y % x == 0
factor: (x: Int)(y: Int)Boolean

scala> val isEven = factor(2) _
isEven: Int => Boolean = <function1>

scala> val z = isEven(32)
z: Boolean = true

21.部分应用函数
scala> def factor(x: Int, y: Int) = y % x == 0
factor: (x: Int, y: Int)Boolean

scala> val f = factor _
f: (Int, Int) => Boolean = <function2>
通配符赋值
scala> val x = f(4, 5)
x: Boolean = false

scala> val multiple = factor(4, _: Int)
multiple: Int => Boolean = <function1>
部分应用函数
scala> val y = multiple(55)
y: Boolean = false

22.传名参数 <参数名> => <返回值类型> x: => Int
当一个函数的参数中出现x: => Int这种形式，表明它是一个有传名参数的函数。
scala> def doubles(x: => Int) = {
     |   println("NOW Doubleing " + x)
     |   x * 2
     | }
doubles: (x: => Int)Int
传值
scala> doubles(5)
NOW Doubleing 5
res17: Int = 10

scala> def f(i: Int) = {println(s"Hello from f($i)"); i }
f: (i: Int)Int
传函数
scala> doubles(f(4))
Hello from f(4)
NOW Doubleing 4
Hello from f(4)
res18: Int = 8
由于doubles方法引用了x两次（见176行），所以Hello消息出现了两次。

23.偏函数partial function
scala> val statusHandler: Int => String = {
     |   case 200 => "Okay"
     |   case 400 => "Your Error"
     |   case 500 => "Our Error"
     | }
statusHandler: Int => String = <function1>

scala> statusHandler(200)
res20: String = Okay

scala> statusHandler(500)
res21: String = Our Error

scala> statusHandler(300)
scala.MatchError: 300 (of class java.lang.Integer)
    at $anonfun$1.apply(<console>:7)
    at $anonfun$1.apply(<console>:7)
    ...

case _   => "default "    

scala> statusHandler(300)
res23: String = "default "



24.
scala> def timer[A](f: => A): A = {
     |   def now = System.currentTimeMillis
     |   val start = now;
     |   val a = f
     |   val end = now
     |   println(s"Executed in ${end - start} ms")
     |   a
     | }
timer: [A](f: => A)A

scala> val veryRandomAmount = timer {
     |   util.Random.setSeed(System.currentTimeMillis)
     |   for (i <- 1 to 100000) util.Random.nextDouble
     |   util.Random.nextDouble
     | }
Executed in 15 ms
veryRandomAmount: Double = 0.651757645434543

25.列表List是一个不可变的单链表 List(1, 2, 3, ... )

26.def printArgs(args: Array[String]): Unit = {
     for (arg <- args)
     println(arg)
     args.foreach(println)
}







---------------------------------------fold------------------------------------------    
   
def fold[A1 >: A](z: A1)(op: (A1, A1) => A1): A1 = foldLeft(z)(op)

List中的fold方法需要输入两个参数：初始值以及一个函数。输入的函数也需要输入两个参数：累加值和当前索引元素，
即：上述代码的x为累加值，y为当前索引的元素。
在这里要注意一下，fold（）方法中传入的参数，必须和返回的最终结果类型一致。
即上述代码中：传入的””和后续方法最终返回的类型一致，都是String。

与fold相同还有两个方法foldLeft和foldRight。
其三者之间的主要区别在于fold函数操作遍历问题集合的顺序。
foldLeft是从左开始计算，然后往右遍历。
foldRight是从右开始算，然后往左遍历。
而fold遍历的顺序没有特殊的次序。

---------------------------------------foldl------------------------------------------ 
   
private def foldl[B](start: Int, end: Int, z: B, op: (B, A) => B): B =
    if (start == end) z
    else foldl(start + 1, end, op(z, this(start)), op)
        
---------------------------------------foldr------------------------------------------

private def foldr[B](start: Int, end: Int, z: B, op: (A, B) => B): B =
    if (start == end) z
    else foldr(start, end - 1, op(this(end - 1), z), op)
  
------------------------------------foldLeft---------------------------------------------

override /*TraversableLike*/
def foldLeft[B](z: B)(op: (B, A) => B): B =
    foldl(0, length, z, op)
    
def foldLeft[B](z: B)(op: (B, A) => B): B = {
    var result = z
    this foreach (x => result = op(result, x))
    result
} 

def /:[B](z: B)(op: (B, A) => B): B = foldLeft(z)(op)
    
------------------------------------foldRight---------------------------------------------

override /*IterableLike*/
def foldRight[B](z: B)(op: (A, B) => B): B =
    foldr(0, length, z, op)
    
def foldRight[B](z: B)(op: (A, B) => B): B =
    reversed.foldLeft(z)((x, y) => op(y, x))    

def :\[B](z: B)(op: (A, B) => B): B = foldRight(z)(op)  
  
------------------------------------reduceLeft---------------------------------------------   

override /*TraversableLike*/
def reduceLeft[B >: A](op: (B, A) => B): B =
    if (length > 0) foldl(1, length, this(0), op) else super.reduceLeft(op)
   
------------------------------------reduceRight---------------------------------------------

override /*IterableLike*/
def reduceRight[B >: A](op: (A, B) => B): B =
    if (length > 0) foldr(0, length - 1, this(length - 1), op) else super.reduceRight(op)
    
    
    
    
    
    
    
    
    
        