NOTA 1:
Deberá modificar el contenido de algunos ficheros del directorio 'src\main\resources' dependiendo del 'host' que desee para albergar la base de datos:
  Fase 1 - Memoria (h2): En 'application.properties', "anotar" (carácter almohadilla) la segunda línea y activar 'spring.profiles.active=h2'
  Fase 2 (preseleccionada) - Servidor MySQL: En 'application.properties', "anotar" (carácter almohadilla) la primera línea y activar 'spring.profiles.active=mysql'. En el fichero 'application-mysql.properties' posiblemente deba modificar la dirección URL (línea 5), el nombre del usuario (línea 6) y/o su contraseña de acceso (línea 7).
    
NOTA 2:
En el directorio 'src\Postman' del proyecto encontrará la colección con todas las invocaciones http. Éstas están enlazadas por scripts y variables, y puede correr la colección al completo si, tras abrirla, pulsa 'Run'. Los 31 tests seguirán positivos incluso si la base de datos 'whitecollar' ya existe en el servidor y la aplicación se dedica a duplicar entradas; por claridad, quizás quiera borrar la base de datos antes de volver a correr la colección Postman: "drop database whitecollar;" (en MySQL)