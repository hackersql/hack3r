E:\ELK\Git_Project>scalac -Xshow-phases
phase name                   description
----------                   -----------
parser                       parse source into ASTs, perform simple desugaring
namer                        resolve names, attach symbols to named trees
packageobjects               load package objects
typer                        the meat and potatoes: type the trees
patmat                       translate match expressions
superaccessors               add super accessors in traits and nested classes
extmethods                   add extension methods for inline classes
pickler                      serialize symbol tables
refchecks                    reference/override checking, translate nested objects
selectiveanf  
selectivecps  
uncurry                      uncurry, translate function values to anonymous classes
tailcalls                    replace tail calls by jumps
specialize                   @specialized-driven class and method specialization
explicitouter                this refs to outer pointers, translate patterns
erasure                      erase types, add interfaces for traits
posterasure                  clean up erased inline classes
lazyvals                     allocate bitmaps, translate lazy vals into lazified defs
lambdalift                   move nested functions to top level
constructors                 move field definitions into constructors
flatten                      eliminate inner classes
mixin                        mixin composition
cleanup                      platform-specific cleanups, generate reflective calls
icode                        generate portable intermediate code
inliner                      optimization: do inlining
inlineExceptionHandlers      optimization: inline exception handlers
closelim                     optimization: eliminate uncalled closures
dce                          optimization: eliminate dead code
jvm                          generate JVM bytecode
terminal                     The last phase in the compiler chain