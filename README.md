# AREP PARCIAL TERCIO 2

El proyecto implica la creación de dos componentes clave: una aplicación web para explorar los algoritmos de búsqueda lineal y binaria, y un sistema de microservicios destinado al ordenamiento de funciones, acompañado de un proxy que dirigirá las solicitudes entre las distintas instancias del servicio. Además, se diseñará un cliente web básico para la interacción con el proxy y los servicios de ordenamiento. Todo el desarrollo se llevará a cabo utilizando las tecnologías mencionadas, y la implementación se desplegará en instancias de máquinas virtuales EC2 en AWS.

## Primeros Pasos

### PREREQUISITOS

* [Java (desde la 15 para delante)](https://www.oracle.com/co/java/technologies/downloads/) 
* [Maven](https://maven.apache.org/download.cgi) 
* [Git](https://git-scm.com/downloads)

### ARQUITECTURA

1. **Aplicación Web de Exploración de Algoritmos de Búsqueda:**
   - Esta parte involucra la creación de una aplicación web que permita investigar dos algoritmos de búsqueda clave en ciencias de la computación: la búsqueda lineal y la búsqueda binaria.
   - Tecnologías utilizadas: Maven para gestión de proyectos, Git y GitHub para control de versiones y colaboración, SparkJava como framework para el backend, HTML5 y JavaScript para el frontend.
   - La aplicación estará desplegada en tres máquinas virtuales EC2 de Amazon Web Services (AWS).

2. **Sistema de Microservicios para Funciones de Ordenamiento con Service Proxy:**
   - Este aspecto del proyecto se enfoca en el diseño de un sistema de microservicios, con un servicio denominado "Math Services" para realizar funciones de ordenamiento.
   - El servicio de ordenamiento estará desplegado en al menos dos instancias virtuales EC2, lo que permite escalabilidad y redundancia.
   - Se implementará un service proxy que recibirá las solicitudes de llamadas desde los clientes y las distribuirá entre las dos instancias del servicio de ordenamiento utilizando un algoritmo de round-robin.
   - El proxy se desplegará en otra máquina EC2 de AWS para una gestión independiente.
   - Configuración flexible: Se podrán ajustar las direcciones y puertos de las instancias del servicio en el proxy mediante el uso de variables de entorno del sistema operativo.
   
3. **Cliente Web Mínimo para Interacción con el Proxy y Servicios de Ordenamiento:**
   - Se construirá un cliente web mínimo utilizando HTML y JavaScript.
   - Este cliente web incluirá formularios para interactuar con el proxy y los servicios de ordenamiento, permitiendo al usuario ingresar valores.
   - Las solicitudes del cliente hacia el proxy serán asíncronas, invocando los servicios correspondientes para obtener los resultados de las funciones de ordenamiento.
   - El cliente web se comunicará con el proxy para dirigir las solicitudes a las instancias adecuadas del servicio de ordenamiento.

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/02978b72-93a2-4fdc-a062-a938439677ec)


## INSTALACIÓN Y EJECUCIÓN DE MANERA LOCAL

**NOTA:** En este proyecto se hace uso de la variable de entorno SEARCH_SERVICES, cuyo valor para correrlo en una sola máquina local es http://localhost:35000/,http://localhost:35000/. En caso que se tengan otras máquinas en las que se puedan hacer peticiones HTTP, simplemente intercambiar localhost por el dominio o dirección ip. 

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

**NOTA:** En la instancia que ejecutará el servicio proxy, abrir el puerto 38000 y en las otras dos instancias que ejecutarán el servicio mathservices abrir el puerto 35000. Además, todas las instancias deben tener java, git y maven.

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

7. Para ejecutar el servicio de mathservices, ir a las instancias que lo ejecutarán y poner el siguiente comando:
```
java -cp target/AREP_PT2-1.0-SNAPSHOT.jar org.example.MathServices
```
