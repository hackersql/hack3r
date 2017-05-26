/** scala -Xprint:typer test.scala
Sublime 配置文件内容
{  
"shell_cmd": "scala -Xprint:typer \"$file\"",  
"selector": "source.scala"  
}  
*/
def main(argv: Array[String]): Unit = {
	val l = List(1,2,3)
	//val l: List[Int] = immutable.this.List.apply[Int](1, 2, 3);

	for(i <- l) {println(i)}
	//l.foreach[Unit](((i: Int) => scala.this.Predef.println(i)))

	( for(i <- 1 to 10) println(i) )
	/** scala.this.Predef.intWrapper(1).to(10).foreach[Unit](((i: Int) => 
		scala.this.Predef.println(i)))
	 */
def time[T](code : => T) =  {
    val t0 = System.nanoTime : Double
    val res = code
    val t1 = System.nanoTime : Double
    println("Elapsed time " + (t1 - t0) / 1000000.0 + " msecs")
    res
}

/**
def time[T >: Nothing <: Any](code: => T): T = {
	val t0: Double = (java.this.lang.System.nanoTime().toDouble: Double);
	val res: T = code;
	val t1: Double = (java.this.lang.System.nanoTime().toDouble: Double);
	scala.this.Predef.println("Elapsed time ".+(t1.-(t0)./(1000000.0)).+(" msecs"));
	res
}
*/

}