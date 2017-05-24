/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */
// GENERATED CODE: DO NOT EDIT. See scala.Function0 for timestamp.

package scala


/** A tuple of 2 elements; the canonical representation of a [[scala.Product2]].
 *
 *  @constructor  Create a new tuple with 2 elements. Note that it is more idiomatic to create a Tuple2 via `(t1, t2)`
 *	创建一个包含2个元素的新元组。 注意，通过（t1，t2）创建一个Tuple2是比较惯用的，
 *  @param  _1   Element 1 of this Tuple2这个Tuple2的元素1
 *  @param  _2   Element 2 of this Tuple2这个Tuple2的元素2
 */
final case class Tuple2[
	@specialized(Int, Long, Double, Char, Boolean/*, AnyRef*/) +T1, 
	@specialized(Int, Long, Double, Char, Boolean/*, AnyRef*/) +T2] (_1: T1, _2: T2)
  	extends Product2[T1, T2]
{
  override def toString() = "(" + _1 + "," + _2 + ")"
  
  /** Swaps the elements of this `Tuple`.交换这个“元组”的元素。
   * @return a new Tuple where the first element is the second element of this Tuple and the
   * second element is the first element of this Tuple.
   * 返回一个新元组，其中第一个元素是该元组的第二个元素，第二个元素是这个元组的第一个元素。
   */

  def swap: Tuple2[T2,T1] = Tuple2(_2, _1)

}
/** @specialized（类型专门化）编译器为每一个基本类型或者用户指定的类型生成对应的版本。
 *  Type Specialization的语法很简单，就是在泛型类型前加上注解@specialized。
 *  一旦加上@specialized后，编译器除了生成普通的版本外，还会为每一个基本类型生成一个对应的版本。
 *  Scala的基本类型有Unit, Boolean, Byte, Short, Char, Int, Long, Float, Double九种，
 *  那么编译器就会生成九种不同的版本。当然，你还可以指定生成对应的版本。
 */