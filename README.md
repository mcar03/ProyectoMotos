# MotosProyecto

## Descripción

MotosProyecto es una aplicación Android desarrollada en Kotlin que permite gestionar una lista de motocicletas a través de una arquitectura robusta basada en MVVM y Clean Architecture. La aplicación cuenta con autenticación mediante Firebase y almacenamiento en Firestore.

## Estructura MVVM (Clean Architecture)
![imagen](https://github.com/user-attachments/assets/129b08f9-5b6c-444a-956b-f83e771e72b6) 

La clase Controller tiene todo el código comentado, este existe debido a que antes la aplicación era MVC (Módelo Vista Controlador). Es decir, Que el Controller tenía el contexto de la Ui, mientras que con la nueva arquitectura MVVM es el ViewModels (la propia ui la que (se actualiza devido a un "observer"))

## Contenido Data
El contenido que debe estar en Data como su propio nombre indica, son todo lo que tenga que ver con datos... Dentro de el encontramos 

![imagen](https://github.com/user-attachments/assets/3a6e50d8-635e-4071-a7e5-a9fd7a9d1f1b)

Aquí es donde vamos a tener nuestra clase POJO "Motos" y la creación de una lista en MEMORIA sobre motos que luego previamente se cargaran en un RecyclerView, cada item en un cardview como el siguiente:

![imagen](https://github.com/user-attachments/assets/e86d1bfa-415a-4121-bd95-54de3df29e58)

## Contenido Dominio
El contenido que debe estar en Domain es todo lo que tenga que ver con la lógica de negocio, en nuetro caso será los casos de uso (GET,POST,PUSH,DELETE)

- GET para devolver toda la lista de Motos en memoria en cada item.
- POST para añadir una nueva moto a una lista (no se guarda en memoria).
- PUSH para editar la moto selecionada cogiendo la posición de dicha moto en la lista y cambiando sus parámetros (no se guarda en memoria).
- DELETE para borrar una moto de la lista (no se guarda en memoria).


![imagen](https://github.com/user-attachments/assets/908eb5a3-916d-44fd-bb68-f7b50c1c929a)

### AddMotoUseCase.kt
```java
class MutableListMotos {
    companion object {
        var motos:MutableList<Moto> = emptyList<Moto>().toMutableList()
    }
}
```

### DeleteMotoUseCase.kt
```java
class DeleteMotoUseCase {
    operator fun invoke(pos: Int): List<Moto> {
        return DaoMoto.myDao.removeMoto(pos)
    }
}
```

### GetMotoUseCase.kt
```java
class GetMotoUseCase {
    operator fun invoke(): List<Moto> {
        return DaoMoto.myDao.getMotos()
    }
}
```

### UpdateMotoUseCase.kt
```java
class UpdateMotoUseCase {
    operator fun invoke(pos: Int, moto: Moto): List<Moto> {
        return DaoMoto.myDao.updateMoto(pos,moto)
    }
}
```

## Contenido UI
Todo el contenido de que tendrá la UI es todo lo que tenga que ver con la Interfaz de Usuario como su propio nombre indica, esto quiere decir que dentro estarán los Activitys y Fragmentos de nuestra aplicación.

![imagen](https://github.com/user-attachments/assets/e73c4f9e-df49-4bee-8126-dbd5ad52a935)




## Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM con Clean Architecture
- **Aunteticación**: Firebase 
- **UI**: XML con Jetpack Components
- **Patrones de Diseño**: Repository Pattern, Use Cases

### Dependencias Clave

- Firebase Authentication
- RecyclerView
- ViewModel
- LiveData
- Navigation Component

## Estructura del Proyecto

Clean Architecture (mvvm)

## Funcionalidades Principales

### Autenticación

- Inicio de sesión con Firebase Authentication
- Registro de nuevos usuarios
- Restablecimiento de contraseña

### Gestión de Motocicletas (CRUD)

- Agregar una nueva motocicleta
- Editar información de una motocicleta
- Eliminar una motocicleta
- Listado dinámico con RecyclerView

### Persistencia de Datos

- Firestore como base de datos en la nube
- Uso de LiveData y ViewModel para la gestión del estado
- Implementación de Coroutines para operaciones asíncronas

## Instalación y Configuración

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/tu-repo/motosproyecto.git
