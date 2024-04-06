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

## CASOS DE PRUEBA

Estas pruebas fueron realizadas de manera remota: 
* Servidor proxy: ec2-54-80-193-19.compute-1.amazonaws.com:38000
* Servidor mathservice: ec2-52-90-130-227.compute-1.amazonaws.com:35000
* Servidor mathservice: ec2-54-80-142-64.compute-1.amazonaws.com:35000

### Funcionamiento de Búsqueda Líneal

Se hará uso de los siguientes parámetros:
- Lista: 5,1,4,2,3
- Valor a buscar: 4

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/897eeab0-7543-483a-aa29-33c82dfa71f7)

Cuerpo del JSON de respuesta:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/f775be06-7fa3-4604-a516-5cc4a92eae40)

Terminal de la instancia proxy:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/97a928ef-013a-49bc-998d-5d33027b49b0)

Terminal de la instancia a la que el proxy le dirigió la petición:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/c4a82208-74d7-47a6-8e24-5d61e398b351)

## Prueba de No Funcionamiento de la Búsqueda Lineal

Se hará uso de los siguientes parámetros:
- Lista: 5,1,4,2,3
- Valor a buscar: 45

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/f38e8c31-8cd8-4154-89ef-7a73e85d27fa)

Cuerpo del JSON de respuesta: 

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/1e95f1d7-5a20-440e-9dfb-23b66bb78836)

Terminal de la instancia proxy:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/5ac3106d-6335-4b57-931d-3ac78be93612)

Terminal de la instancia a la que el proxy le dirigió la petición:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/c68a4d65-f736-4cf7-a61c-10f4b57386e8)

### Funcionamiento de Búsqueda Binaria

Se hará uso de los siguientes parámetros:
- Lista: 1,2,3,4,5
- Valor a buscar: 3

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/8ec009a8-086b-4eef-9d1f-fc29aa632a47)

Cuerpo del JSON de respuesta:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/b2df4f01-a27d-4fd1-8be2-dba4cd7d0710)

Terminal de la instancia proxy:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/b09a0fee-2c48-40b6-b7df-d268c41b845f)

Terminal de la instancia a la que el proxy le dirigió la petición:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/e887b61f-8d10-468c-9f0f-579d95f0c0e0)

## Prueba de No Funcionamiento de la Búsqueda Binaria

Se hará uso de los siguientes parámetros:
- Lista: 1,2,3,4,5
- Valor a buscar: 34

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/6c364272-9e9c-4f23-826a-9d9ba77928cd)

Cuerpo del JSON de respuesta: 

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/f0197bbb-6d15-44ee-a6b4-bf250fe308c2)

Terminal de la instancia proxy:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/fd1ba140-2f65-444d-a0bb-c626ff35df06)

Terminal de la instancia a la que el proxy le dirigió la petición:

![image](https://github.com/FDanielMC/AREP_PARCIALT2/assets/123689924/70b4beff-80d4-40e3-bf38-7ee7f09d6f6b)

### Vídeo Desplegando el Programa



## Authors

* **Daniel Fernando Moreno Cerón** - [FDanielMC](https://github.com/FDanielMC)

### Licencia

This project is licensed under the MIT License - see the LICENSE.md file for details

### Agradecimientos

Escuela Colombiana de Ingeniería
