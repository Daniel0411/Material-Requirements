# Material-Requirements
The second final assignment of the programming class 1.

A little program to manage different assemblies and the components of these assemblies. For example can an assembly A consist of the parts B and C. Part C can also be an assembly which consists of the Parts B and D and so on. Components which exist without an assigned assembly will be removed automatically.

The following commands are part of the programm:

### pointed brackets are not part of the command ###

addAssembly <nameAssembly>=<amount1>:<name1>;<amount2>:<name2>;...;<amountn>:<namen>
  Adds an assembly to the system.
  nameAssembly: the name of the new assembly. Characters must be between a-z and A-Z.
  name:         name of the parts the assembly consists of.
  amount:       amount of the parts the assembly consists of (must be between 1 and 1000).

removeAssembly <nameAssembly>
  Removes an assembly. If the assembly you'd like to remove is part of another assembly the assembly will remain in the system but is no longer an assembly but an component.
  nameAssembly: assembly you'd like to remove from the system.
  
printAssembly <nameAssembly>
  nameAssembly: assembly you'd like to print.
  
getAssemblies <nameAssembly>
  Gets all assemblies an assembly consists of. Gets no components (also indirect assemblies).
  nameAssembly: name of the assembly you'd like to get the assemblies of.
  
getComponents <nameAssembly>
  Same command as getAssemblies but with components.
  
addPart <nameAssembly>+<amount>:<name>
  Adds a part to an existing assembly.
  nameAssembly: assembly you'd like to add a part.
  amount:       amount of the part you'd like to add.
  name:         name of the part you'd like to add.
  
removePart <nameAssembly>-<amount>:<name>
  Removes a part of an existing assembly.
  nameAssembly: assembly you'd like to remove a part.
  amount:       amount of the part you'd like to remove.
  name:         name of the part you'd like to remove.
  
quit
  Terminates the program and deletes all existing assemblies and components.
  
  
  
  
