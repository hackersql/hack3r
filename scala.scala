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

scala> val s: String = identity[String]("Hello Scala")
s: String = Hello Scala

scala> val s: String = identity("Hello Scala")
s: String = Hello Scala
scala会自动推导出类型参数的类型，以及返回值的值的类型。
scala> val s = identity("Hello Scala")
s: String = Hello Scala 

16.用中缀点记法调用方法 <class instance>.<method-name>[(<paramete-name>)]  类实例名.方法名(参数值)








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
    
    
    
    
    
    
    
    
    
        