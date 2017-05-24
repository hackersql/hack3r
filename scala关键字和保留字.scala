-----------------------------------------------------------
                      scala保留字                        ||
===========================================================
abstract    case        catch       class       def      ||
do          else        extends     false       final    ||
finally     for         forSome     if          implicit ||
import      lazy        macro       match       new      ||
null        object      override    package     private  ||
protected   return      sealed      super       this     ||
throw       trait       try         true        type     ||
val         var         while       with        yield    ||
_    :    =    =>    <-    <:    <%     >:    #    @     ||
===========================================================

Keywords/reserved symbols

关键字/保留符号
Scala中有几个特殊的符号
不能被定义或用作方法名称。其中两个被认为是适当的关键字，而其他的只是“保留”。他们是：

// 
--------------------------------------Keywords-----------------------------------------------
<-  	   // 在for循环中将生成器与其标识符分开
=>  	   // 在匹配表达式和部分函数中用来指示一个条件表达式，
		   // 在函数类型中指示一个返回类型，在函数字面量中用来定义函数体
--------------------------------------Reserved-----------------------------------------------
( )        // Delimit expressions and parameters定义表达式和参数
[ ]        // Delimit type parameters分隔类型参数
{ }        // Delimit blocks分隔块
.          // Method call and path separator方法调用和路径分隔符
// /* */   // Comments注释
#          // Used in type notations这是一个类型投影，将类型与其子类型分开
:          // Type ascription or context bounds将一个值、变量或函数与其类型分开
<: >:      // Upper and lower bounds上下界
<%         // View bounds (deprecated)视界操作符，接受可以看作是给定雷丁的所有表达式（已弃用）
" """      // Strings字符串
'          // Indicate symbols and characters指示符号和字符
@          // Annotations and variable binding on pattern matching注释和变量绑定模式匹配
`          // Denote constant or enable arbitrary identifiers表示常量或启用任意标识符
,          // Parameter separator参数分隔符
;          // Statement separator语句分隔符
_*         // vararg expansion参数扩展
_          // Many different meanings许多不同的含义,例如通配符。
=          // 赋值操作符
abstract   // 标志一个类或trait特质为抽象而且不可实例化
case       // 在匹配表达式和部分函数中定义一个匹配模式
catch      // 捕获一个异常
class      // 定义一个新类
def        // 定义一个新方法
import     // 将一个包、类或类的成员导入到当前命名空间
implicit   // 定义了一个隐含转换或参数
extends    // 为一个新类扩展基类型
final      // 标志一个类或trait不可扩展
finally    // 在一个try块后执行一个表达式
forSome    // 定义一个存在类型。
lazy       // 定义一个值为懒值惰性的值，只在第一次访问时才定义
match      // 开始一个匹配表达式
new        // 创建一个类的新实例
null       // 这个值没有实例，类型为Null
object     // 定义一个新对象
override   // 标志一个值或方法要重写基类型中同名的成员
package    // 定义当前包，一个递增的包名，或者一个包对象
private    // 标志一个类成员不能再类定义之外访问，私有成员。
protected  // 标志一个类成员不能在类定义或其子类之外访问
return     // 显式指出一个方法的返回值。默认地，方法中的最后一个表达式会用作为返回值
sealed     // 标志一个类只接受当前文件中的子类
super      // 标志要引用基类型中的成员，而不是当前类中被覆盖的那个成员。
this       // 标志要引用当前类中的成员，而不是同名的一个参数。
throw      // 产生一个错误条件，中断当前操作流，只有当这个错误在其他位置“被捕获”时才继续操作流
trait      // 定义一个新特质
try        // 标志一段捕获异常的代码
type       // 定义一个新的类型别名
val        // 定义一个新的不可变的值
var        // 定义一个新的可变的变量
with       // 为一个类定义基特质trait
yield      // 从一个for循环中的得出返回值
-------------------------------------------------------------------------------------
通配符示例：
import scala._    // Wild card -- all of Scala is imported通配符--所有scala.下的包都导入
import scala.{ Predef => _, _ } // Exclusion, everything except Predef
							    // 排除，除Predef之外的一切
def f[M[_]]       // Higher kinded type parameter
def f(m: M[_])    // Existential type
_ + _             // Anonymous function placeholder parameter匿名函数占位符参数
m _               // Eta expansion of method into method value
m(_)              // Partial function application偏函数应用程序
_ => 5            // Discarded parameter
case _ =>         // Wild card pattern -- matches anything通配符模式--匹配任何东西
f(xs: _*)         // Sequence xs is passed as multiple parameters to 
				  // 将序列xs作为多个参数传递给f(ys: T*)
case Seq(xs @ _*) // Identifier xs is bound to the whole matched sequence
				  // 标识符xs绑定到整个匹配的序列