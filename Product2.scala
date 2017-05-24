/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */
// GENERATED CODE: DO NOT EDIT. See scala.Function0 for timestamp.

package scala

object Product2 {
  def unapply[T1, T2](x: Product2[T1, T2]): Option[Product2[T1, T2]] =
    Some(x)
}

/** Product2 is a Cartesian product of 2 components.
 *  Product2是2个组件的笛卡尔乘积。
 *  @since 2.3
 */
trait Product2[@specialized(Int, Long, Double) +T1, @specialized(Int, Long, Double) +T2] extends Any with Product {
  /** The arity of this product.
   *  @return 2
   */
  override def productArity = 2

  
  /** Returns the n-th projection of this product if 0 <= n < productArity,
   *  otherwise throws an `IndexOutOfBoundsException`.
   *
   *  @param n number of the projection to be returned
   *  @return  same as `._(n+1)`, for example `productElement(0)` is the same as `._1`.
   *  @throws  IndexOutOfBoundsException
   */

  @throws(classOf[IndexOutOfBoundsException])
  override def productElement(n: Int) = n match { 
    case 0 => _1
    case 1 => _2
    case _ => throw new IndexOutOfBoundsException(n.toString())
 }

  /** A projection of element 1 of this Product.
   *  @return   A projection of element 1.
   */
  def _1: T1
  /** A projection of element 2 of this Product.
   *  @return   A projection of element 2.
   */
  def _2: T2


}
/** @specialized（类型专门化）编译器为每一个基本类型或者用户指定的类型生成对应的版本。
 *  Type Specialization的语法很简单，就是在泛型类型前加上注解@specialized。
 *  一旦加上@specialized后，编译器除了生成普通的版本外，还会为每一个基本类型生成一个对应的版本。
 *  Scala的基本类型有Unit, Boolean, Byte, Short, Char, Int, Long, Float, Double九种，
 *  那么编译器就会生成九种不同的版本。当然，你还可以指定生成对应的版本。
 */