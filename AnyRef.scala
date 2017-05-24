/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala

/** Class `AnyRef` is the root class of all ''reference types''.
 *  类AnyRef是所有“引用类型”的根类。
 *  All types except the value types descend from this class.
 *  除类型之外的所有类型都来自此类
 *  @template
 */
trait AnyRef extends Any {

  /** The equality method for reference types.  Default implementation delegates to `eq`.
   *  对于引用类型相等的方法，默认实现自`eq`
   *  See also `equals` in [[scala.Any]].另请参见[[scala.Any]]中的`equals`。
   *  
   *  @param  that    the object to compare against this object for equality.
   *  @return         `true` if the receiver object is equivalent to the argument; `false` otherwise.
   */
  def equals(that: Any): Boolean = this eq that

  /** The hashCode method for reference types.  See hashCode in [[scala.Any]].
   *  对于引用类型hashCode方法。 请参阅[[scala.Any]]中的hashCode。
   *  @return   the hash code value for this object.返回该对象的哈希码值。
   */
  def hashCode: Int = sys.error("hashCode")

  /** Creates a String representation of this object.  The default
   *  representation is platform dependent.  On the java platform it
   *  is the concatenation of the class name, "@", and the object's
   *  hashcode in hexadecimal.
   *
   *  @return     a String representation of the object.
   *              返回一个对象的字符串表示形式。
   */
  def toString: String = sys.error("toString")

  /** Executes the code in `body` with an exclusive lock on `this`.
   *
   *  @param    body    the code to execute要执行的代码
   *  @return           the result of `body`返回“body”的结果
   */
  def synchronized[T](body: => T): T

  /** Tests whether the argument (`that`) is a reference to the receiver object (`this`).
   *  测试参数（`that`）是否是对接收器对象的引用（`this`）。
   *  The `eq` method implements an [[http://en.wikipedia.org/wiki/Equivalence_relation equivalence relation]] on
   *  non-null instances of `AnyRef`, and has three additional properties:
   *  “AnyRef”的非空实例，并具有三个附加属性：
   *
   *   - It is consistent: for any non-null instances `x` and `y` of type `AnyRef`, multiple invocations of
   *     `x.eq(y)` consistently returns `true` or consistently returns `false`.
   *  它是一致的：对于任何类型为“AnyRef”的非空实例“x”和“y”，“x.
   *  eq（y）”的多次调用始终返回“true”或始终返回“false”。
   *   - For any non-null instance `x` of type `AnyRef`, `x.eq(null)` and `null.eq(x)` returns `false`.
   *  - 对于任何类型为“AnyRef”的非空实例“x”，“x.eq（null）”和“null.eq（x）”返回`false`。
   *   - `null.eq(null)` returns `true`.- `null.eq（null）`返回`true`。
   *
   *  When overriding the `equals` or `hashCode` methods, it is important to ensure that their behavior is
   *  consistent with reference equality.  Therefore, if two objects are references to each other (`o1 eq o2`), they
   *  should be equal to each other (`o1 == o2`) and they should hash to the same value (`o1.hashCode == o2.hashCode`).
   *  当覆盖`equals`或`hashCode`方法时，确保它们的行为与引用相等性是一致的。 
   *  因此，如果两个对象是彼此的引用（`o1 eq o2`），则它们应该彼此相等（`o1 == o2`），
   *  并且它们应该哈希到相同的值（`o1.hashCode == o2.hashCode`）。

   *  @param  that    the object to compare against this object for reference equality.
   *  @return         `true` if the argument is a reference to the receiver object; `false` otherwise.
   */
  final def eq(that: AnyRef): Boolean = sys.error("eq")

  /** Equivalent to `!(this eq that)`.相当于`!(this eq that)`
   *
   *  @param  that    the object to compare against this object for reference equality.
   *  @return         `true` if the argument is not a reference to the receiver object; `false` otherwise.
   */
  final def ne(that: AnyRef): Boolean = !(this eq that)

  /** The expression `x == that` is equivalent to `if (x eq null) that eq null else x.equals(that)`.
   *  表达式`x == that`相当于`if (x eq null) that eq null else x.equals(that)`
   *  @param    that  the object to compare against this object for equality.
   *  @return         `true` if the receiver object is equivalent to the argument; `false` otherwise.
   */
  final def ==(that: Any): Boolean =
    if (this eq null) that.asInstanceOf[AnyRef] eq null
    else this equals that

  /** Create a copy of the receiver object.
   *  创建接收器对象的副本。
   *  The default implementation of the `clone` method is platform dependent.
   *  clone方法的默认实现是平台依赖的。
   *  @note   not specified by SLS as a member of AnyRef
   *          不被SLS指定为AnyRef的成员，SLS是个什么鬼
   *  @return a copy of the receiver object.返回接收器对象的副本
   */
  protected def clone(): AnyRef

  /** Called by the garbage collector on the receiver object when there
   *  are no more references to the object.
   *
   *  The details of when and if the `finalize` method is invoked, as
   *  well as the interaction between `finalize` and non-local returns
   *  and exceptions, are all platform dependent.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  protected def finalize(): Unit

  /** Wakes up a single thread that is waiting on the receiver object's monitor.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def notify(): Unit

  /** Wakes up all threads that are waiting on the receiver object's monitor.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def notifyAll(): Unit

  /** Causes the current Thread to wait until another Thread invokes
   *  the notify() or notifyAll() methods.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def wait (): Unit
  final def wait (timeout: Long, nanos: Int): Unit
  final def wait (timeout: Long): Unit
}
