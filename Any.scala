/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala

/** Class `Any` is the root of the Scala class hierarchy. 类“Any”是Scala类层次结构的根 
 *  Every class in a Scala execution environment inherits directly or indirectly from this class. 
 *  Scala执行环境中的每个类直接或间接继承自该类。
 * Starting with Scala 2.10 it is possible to directly extend `Any` using ''universal traits''.
 * 从Scala 2.10开始，可以使用“通用特征”直接扩展`Any`。
 * A ''universal trait'' is a trait that extends `Any`, only has `def`s as members, and does no initialization.
 * “通用特征”是一个扩展`Any`的特征，只有`def`s作为成员，并且没有初始化。
 * The main use case for universal traits is to allow basic inheritance of methods for [[scala.AnyVal value classes]].
 * 通用特征的主要用例是允许[[scala.AnyVal value classes]]的方法的基本继承。  
 * For example,例如
 *
 * {{{
 *     trait Printable extends Any {
 *       def print(): Unit = println(this)
 *     }
 *     class Wrapper(val underlying: Int) extends AnyVal with Printable
 *
 *     val w = new Wrapper(3)
 *     w.print()
 * }}}
 *
 * See the [[http://docs.scala-lang.org/overviews/core/value-classes.html Value Classes and Universal Traits]] for more有关更多信息，请参阅
 * details on the interplay of universal traits and value classes.
 */
abstract class Any {
  /** Compares the receiver object (`this`) with the argument object (`that`) for equivalence.
   *  将接收器对象（`this`）与参数对象（`that`）进行比较看是否相等
   *  Any implementation of this method should be an [[http://en.wikipedia.org/wiki/Equivalence_relation equivalence relation]]:
   *
   *  - It is reflexive: for any instance `x` of type `Any`, `x.equals(x)` should return `true`.
   *  它是自反的：对于任何类型为“Any”的实例，`x.equals（x）`应该返回`true`。
   *  - It is symmetric: for any instances `x` and `y` of type `Any`, 
   *  `x.equals(y)` should return `true` if and only if `y.equals(x)` returns `true`.
   *  它是对称的：对于任何类型为“Any”的实例x和y，`x.equals(y)` 应该返回 `true`
   *  当且仅当`y.equals（x）'返回`true`
   *  - It is transitive: for any instances `x`, `y`, and `z` of type `Any` 
   *  if `x.equals(y)` returns `true` and `y.equals(z)` returns `true`,
   *  then `x.equals(z)` should return `true`.  
   *  它是可传递的：对于'Any'类型的任何实例'x'，'y'和'z'，
   *  如果'x.equals（y）'返回'true'和'y.equals（z）'返回'true'，
   *  那么'x.equals（z）'应该返回'true'。
   *  If you override this method, you should verify that your implementation remains an equivalence relation.
   *  如果您重写此方法，则应验证您的实现是否保持等价关系。
   *  Additionally, when overriding this method it is usually necessary to override `hashCode` to ensure that
   *  另外，当覆盖这个方法时，通常需要重写`hashCode'以确保这一点
   *  objects which are "equal" (`o1.equals(o2)` returns `true`) hash to the same [[scala.Int]].
   *  “equal”对象（`o1.equals（o2）`返回`true`）哈希到相同的[[scala.Int]]。
   *  (`o1.hashCode.equals(o2.hashCode)`).
   *
   *  @param  that    the object to compare against this object for equality.
   *                  将对象与此对象进行比较以相等
   *  @return         `true` if the receiver object is equivalent to the argument; 
   *                  `false` otherwise.如果接收对象等同于参数则为true，否则为false
   */
  def equals(that: Any): Boolean

  /** Calculate a hash code value for the object.
   *  计算对象的哈希码值。
   *  The default hashing algorithm is platform dependent.
   *  默认散列算法依赖于平台
   *  Note that it is allowed for two objects to have identical hash codes (`o1.hashCode.equals(o2.hashCode)`) yet
   *  请注意，允许两个对象具有相同的哈希码
   *  但是(`o1.hashCode.equals(o2.hashCode)`)不等于(`o1.equals(o2)` returns `false`)
   *  not be equal (`o1.equals(o2)` returns `false`).  A degenerate implementation could always return `0`.
   *  However, it is required that if two objects are equal (`o1.equals(o2)` returns `true`) that they have identical hash codes (`o1.hashCode.equals(o2.hashCode)`).  Therefore, when overriding this method, be sure to verify that the behavior is consistent with the `equals` method.
   但是，如果两个对象相等（`o1.equals（o2）`返回`true`），
   则它们必须具有相同的哈希码（`o1.hashCode.equals（o2.hashCode）`））。
   因此，当覆盖此方法时，请确保验证该行为是否与“equals”方法一致。
   *
   *  @return   the hash code value for this object.返回此对象的哈希码值
   */
  def hashCode(): Int

  /** Returns a string representation of the object.
   *  返回对象的字符串表示形式。
   *  The default representation is platform dependent.
   *  默认表示依赖于平台
   *  @return a string representation of the object.
   *  返回对象的字符串表示形式
   */
  def toString(): String

  /** Returns the runtime class representation of the object.
   *  返回对象的运行时类表示
   *  @return a class object corresponding to the runtime type of the receiver.
   *  返回与接收器的运行时类型相对应的类对象
   */
  final def getClass(): Class[_] = sys.error("getClass")

  /** Test two objects for equality.测试两个对象是否相等
   *  The expression `x == that` is equivalent to `if (x eq null) that eq null else x.equals(that)`.
   *  表达式“x == that”等效于`if（x eq null）eq null else x.equals（that）`。
   *  @param  that  the object to compare against this object for equality.
   *  @return       `true` if the receiver object is equivalent to the argument; `false` otherwise.如果接收对象等同于参数则为true，否则为false
   */
  final def ==(that: Any): Boolean = this equals that

  /** Test two objects for inequality.
   *  测试两个对象不相等
   *  @param  that  the object to compare against this object for equality.
   *  @return       `true` if !(this == that), false otherwise.
   *  if !(this == that) 为`true`，否则为false
   */
  final def != (that: Any): Boolean = !(this == that)

  /** Equivalent to `x.hashCode` except for boxed numeric types and `null`.
   *  相当于`x.hashCode`，除了boxed数字类型和`null`。
   *  For numerics, it returns a hash value which is consistent
   *  with value equality:对于数字，它返回与值相等一致的哈希值：   
   *  if two value type instances compare as true,
   *  如果两个值类型实例比较为真，
   *  then ## will produce the same hash value for each of them.
   *  则##将为它们中的每一个生成相同的哈希值。
   *  For `null` returns a hashcode where `null.hashCode` throws a `NullPointerException`.
   *  对于`null`返回一个哈希码，其中`null.hashCode`抛出一个`NullPointerException`。
   *
   *  @return   a hash value consistent with ==      返回与==一致的哈希值
   */
  final def ##(): Int = sys.error("##")

  /** Test whether the dynamic type of the receiver object is `T0`.
   *  测试接收器对象的动态类型是否为T0。
   *  Note that the result of the test is modulo Scala's erasure semantics.
   *  请注意，测试结果是Scala形式的擦除语义。
   *  Therefore the expression `1.isInstanceOf[String]` will return `false`, 
   *  因此，表达式“1.isInstanceOf [String]”将返回“false”，
   *  while the expression `List(1).isInstanceOf[List[String]]` will return `true`.
   *  而表达式`List(1).isInstanceOf[List[String]]`将返回“true”。
   *  In the latter example, because the type argument is erased as part of compilation it is
   *  not possible to check whether the contents of the list are of the specified type.
   *  在后一个示例中，因为类型参数作为编译的一部分被擦除，
   *  所以不可能检查列表的内容是否是指定的类型。
   *  @return `true` if the receiver object is an instance of erasure of type `T0`; `false` otherwise.
   *  如果接收器对象是“T0”类型的擦除实例，则返回“true”; 否则返回“假”。
   */
  final def isInstanceOf[T0]: Boolean = sys.error("isInstanceOf")

  /** Cast the receiver object to be of type `T0`.将接收器对象转换为“T0”类型
   *
   *  Note that the success of a cast at runtime is modulo Scala's erasure semantics.
   *  请注意，运行时的转换成功是Scala形式的擦除语义。
   *  Therefore the expression `1.asInstanceOf[String]` will throw a `ClassCastException` at
   *  runtime, 因此，表达式“1.asInstanceOf [String]”会在运行时抛出一个“ClassCastException”
   *  while the expression `List(1).asInstanceOf[List[String]]` will not.
   *  而表达式“List（1）.asInstanceOf [List [String]]`不会。
   *  In the latter example, because the type argument is erased as part of compilation it is
   *  not possible to check whether the contents of the list are of the requested type.
   *  在后一个示例中,由于类型参数作为编译的一部分被擦除，因此无法检查列表的内容是否为请求的类型
   *  @throws ClassCastException if the receiver object is not an instance of the erasure of type `T0`.
   *  如果接收者对象不是“T0”类型的擦除实例,则抛出一个ClassCastException异常。
   *  @return the receiver object.接收器对象
   */
  final def asInstanceOf[T0]: T0 = sys.error("asInstanceOf")
}

  /** 总而言之，我们把
   *  classOf[T]看成Java里的T.class
   *  obj.isInstanceOf[T]看成 obj instanceof T
   *  obj.asInstanceOf[T]看成(T)obj就对了
   *  scala为我们提供了语法糖，但也免不了类型擦除问题的影响。
   */