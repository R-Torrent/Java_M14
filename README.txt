NOTA 1:
Deber� modificar el contenido de algunos ficheros del directorio 'src\main\resources' dependiendo del 'host' que desee para albergar la base de datos:
  Fase 1 - Memoria (h2): En 'application.properties', "anotar" (car�cter almohadilla) la segunda l�nea y activar 'spring.profiles.active=h2'
  Fase 2 (preseleccionada) - Servidor MySQL: En 'application.properties', "anotar" (car�cter almohadilla) la primera l�nea y activar 'spring.profiles.active=mysql'. En el fichero 'application-mysql.properties' posiblemente deba modificar la direcci�n URL (l�nea 5), el nombre del usuario (l�nea 6) y/o su contrase�a de acceso (l�nea 7).
    
NOTA 2:
En el directorio 'src\Postman' del proyecto encontrar� la colecci�n con todas las invocaciones http. �stas est�n enlazadas por scripts y variables, y puede correr la colecci�n al completo si, tras abrirla, pulsa 'Run'. Los 31 tests seguir�n positivos incluso si la base de datos 'whitecollar' ya existe en el servidor y la aplicaci�n se dedica a duplicar entradas; por claridad, quiz�s quiera borrar la base de datos antes de volver a correr la colecci�n Postman: "drop database whitecollar;" (en MySQL)