# AREP PARCIAL TERCIO 2

Aplicación web para encontrar el índice de un valor dado de una lista de enteros. El programa está desplegado en tres máquinas virtuales de EC2 de AWS. Las tecnologías usadas en la solución son maven, git, github, sparkjava, html5, y js.

## Primeros Pasos

### PREREQUISITOS

* [Java (desde la 15 para delante)](https://www.oracle.com/co/java/technologies/downloads/) 
* [Maven](https://maven.apache.org/download.cgi) 
* [Git](https://git-scm.com/downloads)

## INSTALACIÓN Y EJECUCIÓN DE MANERA LOCAL

**NOTA:** En este proyecto se hace uso de la variable de entorno SEARCH_SERVICES, cuyo valor para correrlo en una sola máquina loca es http://localhost:35000/,http://localhost:35000/. En caso que se tengan otras máquinas en las que se puedan hacer peticiones HTTP, simplemente intercambiar localhost por el dominio o dirección ip. 

1. Clona el repositorio:
```
git clone https://github.com/FDanielMC/AREP_PARCIALT2.git
```

2. Ve a la carpeta del repositorio clonado:
```
cd AREP_PARCIALT2
```

3. Usa el siguiente comando para construir el proyecto:
```
mvn clean install
```

4. Para ejecutar la aplicación se debe correr los servicios SecureLogin (38000) y UserManager(35000):
```
java -cp target/AREP_PT2-1.0-SNAPSHOT.jar org.example.ServiceProxy
```

```
java -cp target/AREP_PT2-1.0-SNAPSHOT.jar org.example.MathServices
```

5. En un navegador ve al siguiente enlace para probar el programa: https://localhost:38000

## INSTALACIÓN Y EJECUCIÓN REMOTA (AWS EC2)

**NOTA:** En la instancia que ejecutará el servicio proxy, abrir el puerto 38000 y en las otras dos instancias que ejecutarán el servicio mathservices abrir el puerto 35000.

1. Para realizar la ejecución remota, primero es conectarse a 3 instancias de ec2.

2. Luego de estar en las instancias de EC2, clonamos el repositorio en cada una de las instancias:
```
git clone https://github.com/FDanielMC/AREP_PARCIALT2.git
```

3. Ve a la carpeta del repositorio clonado:
```
cd AREP_PARCIALT2
```

4. Crear la variable de entornor en la instancia de EC2 que ejecutará el servicio proxy, para hacerlo usar el siguiente comando:
```
export SEARCH_SERVICES=<servicio-uno>,<servicio-dos>
```

En mi caso el comando es el siguiente:
```
export SEARCH_SERVICES=http://ec2-54-210-4-5.compute-1.amazonaws.com:35000/,http://ec2-52-70-12-54.compute-1.amazonaws.com:35000/
```

5. Usa el siguiente comando para construir el proyecto:
```
mvn clean install
```

6. Para ejecutar el servicio proxy, ir a la instancia que lo ejecutará y poner el siguiente comando:
```
java -cp target/AREP_PT2-1.0-SNAPSHOT.jar org.example.ServiceProxy
```
