1.如果没有发现任何显式的返回语句，scala方法将返回方法中最后一个计算得到的值。

2.在scala方法定义中带有大括号但没有等号的，默认返回结果类型为Unit。
scala> def f {"this String gets retrned"}
rs0:   f: Unit

带有等号会自动推断出返回类型
scala> def f = {"this String gets retrned"}
rs1:   f: String
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
    
    
    
    
    
    
    
    
    
        