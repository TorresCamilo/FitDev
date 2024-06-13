package com.torres.fitdev;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecutan los comandos SQL para crear las tablas
        db.execSQL("CREATE TABLE Usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, email TEXT NOT NULL UNIQUE, password TEXT NOT NULL, tipoUsuario TEXT NOT NULL);");
        db.execSQL("CREATE TABLE Cliente (id INTEGER PRIMARY KEY AUTOINCREMENT, fechaNacimiento DATE, genero TEXT, altura REAL, peso REAL, objetivo TEXT, musculoObjetivo TEXT, experiencia TEXT, diasEntrenar TEXT, horaEntreno TIME, gym TEXT, email TEXT, FOREIGN KEY (email) REFERENCES Usuario(email));");
        db.execSQL("CREATE TABLE Administrador (id INTEGER PRIMARY KEY, permisos INTEGER, email TEXT, FOREIGN KEY (email) REFERENCES Usuario(email));");
        db.execSQL("CREATE TABLE Ejercicio (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, descripcion TEXT, series INTEGER, repeticiones INTEGER, duracion INTEGER, imagenReferencia TEXT NOT NULL UNIQUE);");
        db.execSQL("CREATE TABLE PlanEntrenamiento (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, descripcion TEXT, ejercicios TEXT, clienteEmail TEXT, retroalimentacion TEXT, etiquetas TEXT, diasObjetivos REAL, diasCompletados REAL, FOREIGN KEY (clienteEmail) REFERENCES Cliente(email));");
        db.execSQL("CREATE TABLE Progreso (id INTEGER PRIMARY KEY AUTOINCREMENT, clienteEmail TEXT NOT NULL, planEntrenamiento INTEGER NOT NULL, fecha DATE, ejerciciosCompletados TEXT, observaciones TEXT, FOREIGN KEY (clienteEmail) REFERENCES Cliente(email), FOREIGN KEY (planEntrenamiento) REFERENCES PlanEntrenamiento(id));");
        db.execSQL("CREATE TABLE Notificacion (id INTEGER PRIMARY KEY AUTOINCREMENT, clienteEmail TEXT NOT NULL, mensaje TEXT, fechaNotificacion DATE, FOREIGN KEY (clienteEmail) REFERENCES Cliente(email));");

        insertDefaultExercises(db);
        insertDefaultPlanes(db);
        insertDefaultUsers(db);
        insertDefaultClientes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas existentes
        db.execSQL("DROP TABLE IF EXISTS Usuario;");
        db.execSQL("DROP TABLE IF EXISTS Cliente;");
        db.execSQL("DROP TABLE IF EXISTS Administrador;");
        db.execSQL("DROP TABLE IF EXISTS Ejercicio;");
        db.execSQL("DROP TABLE IF EXISTS PlanEntrenamiento;");
        db.execSQL("DROP TABLE IF EXISTS Progreso;");
        db.execSQL("DROP TABLE IF EXISTS Notificacion;");

        // Crear las tablas de nuevo
        onCreate(db);
    }
    private void insertDefaultExercises(SQLiteDatabase db) {
        // Lista de ejercicios por defecto
        String[] ejercicios = {
                "('Saltar la cuerda', 'Sujeta una cuerda de saltar con ambas manos y salta sobre ella mientras la haces girar por debajo de tus pies.', 3, 15, 60, 'ej1')",
                "('Estiramiento de cuerpo completo', 'Realiza movimientos suaves para estirar todos los grupos musculares, incluyendo brazos, piernas, espalda y cuello.', 3, 10, 60, 'ej2')",
                "('Sentadilla aérea', 'Baja las caderas hacia atrás y hacia abajo como si te sentaras en una silla invisible, luego levántate de nuevo.', 3, 12, 60, 'ej3')",
                "('Sentadilla con barra', ' Coloca una barra sobre tus hombros y realiza una sentadilla como se describió anteriormente.', 3, 10, 60, 'ej4')",
                "('Sentadilla búlgara con barra', 'Coloca un pie detrás de ti sobre un banco o plataforma, sujeta la barra en tus hombros y realiza una sentadilla con la pierna delantera', 3, 12, 60, 'ej5')",
                "('Empuje de cadera alto con barra', 'Coloca la barra sobre tus caderas y empuja hacia arriba mientras mantienes la espalda recta.', 3, 15, 60, 'ej6')",
                "('Columpio con kettlebell', 'Sujeta una kettlebell con ambas manos y balancea entre las piernas y hacia adelante, usando la fuerza de las caderas.', 3, 12, 60, 'ej7')",
                "('Extensión de piernas sentado', 'Siéntate en una máquina de extensión de piernas y extiende las piernas hacia adelante para trabajar los músculos cuádriceps.', 3, 10, 60, 'ej8')",
                "('Máquina de elevación de pantorrillas sentado', 'Siéntate en una máquina de pantorrillas y levanta los talones hacia arriba para trabajar los músculos de la pantorrilla.', 3, 15, 60, 'ej9')",
                "('Máquina de elevación de pantorrillas de pie', 'Párate en una máquina de pantorrillas y levanta los talones hacia arriba mientras mantienes las piernas rectas.', 3, 12, 60, 'ej10')",
                "('Picar leña alto bajo con polea', 'Este ejercicio simula el movimiento de picar leña. Utiliza una polea alta y baja para imitar el gesto de cortar madera. Mantén una postura estable y controlada mientras realizas el movimiento.', 3, 10, 60, 'ej11')",
                "('Abdominales de rodillas con barra EZ', 'Siéntate en una máquina de abdominales con las rodillas dobladas. Sujeta una barra EZ detrás de la cabeza y realiza abdominales flexionando el torso hacia adelante.', 3, 15, 60, 'ej12')",
                "('Entrenador de cross', 'Este equipo combina movimientos de remo, ciclismo y esquí de fondo. Trabaja varios grupos musculares al mismo tiempo.', 3, 12, 60, 'ej13')",
                "('Estiramiento de cuerpo completo', 'Realiza movimientos suaves para estirar todos los grupos musculares, incluyendo brazos, piernas, espalda y cuello.', 3, 10, 60, 'ej14')",
                "('Press de banca con barra', 'Acuéstate en un banco plano y levanta una barra con pesas desde el pecho hasta la extensión completa de los brazos.', 3, 12, 60, 'ej15')",
                "('Press de banca inclinado con mancuernas', 'Realiza el mismo movimiento que el press de banca, pero en un banco inclinado. Utiliza mancuernas en lugar de una barra.', 3, 10, 60, 'ej16')",
                "('Press de hombros con barra', 'Párate con los pies separados al ancho de los hombros. Levanta una barra desde los hombros hasta la extensión completa de los brazos sobre la cabeza.', 3, 12, 60, 'ej17')",
                "('Remo de pie con barra', 'Sujeta una barra con las manos y flexiona las caderas hacia adelante. Luego, estira las caderas y tira de la barra hacia el abdomen.', 3, 10, 60, 'ej18')",
                "('Dominadas con agarre ancho', 'Utiliza una barra fija para hacer dominadas, manteniendo las manos separadas a la distancia de los hombros.', 3, 15, 60, 'ej19')",
                "('Inmersiones en barras paralelas', 'Colócate entre dos barras paralelas y baja el cuerpo flexionando los brazos. Luego, estira los brazos para elevar el cuerpo.', 3, 12, 60, 'ej20')",
                "('Máquina de remo (esfuerzo ligero)', 'Utiliza una máquina de remo para simular el movimiento de remar. Ajusta la resistencia para un esfuerzo ligero.', 3, 15, 60, 'ej21')",
                "('Estiramiento de cuerpo completo', 'Realiza movimientos suaves para estirar todos los grupos musculares, incluyendo brazos, piernas, espalda y cuello.', 3, 10, 60, 'ej22')",
                "('Peso muerto con barra', 'Coloca una barra en frente de tus pies y levántala desde el suelo hasta la posición de pie. Mantén la espalda recta y las piernas ligeramente flexionadas.', 3, 12, 60, 'ej23')",
                "('Sentadilla frontal con barra', 'Sujeta la barra en frente de tus hombros y realiza una sentadilla como se describió anteriormente.', 3, 10, 60, 'ej24')",
                "('Estocada con barra', 'Da un paso hacia adelante y baja las caderas en una posición de estocada. Sujeta la barra en los hombros para agregar resistencia.', 3, 12, 60, 'ej25')",
                "('Buenos días con barra', 'Coloca la barra en la parte posterior de tus hombros y flexiona las caderas hacia adelante.', 3, 15, 60, 'ej26')",
                "('Ruleta rusa', 'Este ejercicio combina giros y movimientos de cadera. Sujeta una barra o pesa y gira el torso de un lado a otro.', 3, 12, 60, 'ej27')",
                "('Curvas laterales con mancuerna', 'Sujeta una mancuerna en una mano y flexiona el torso hacia un lado, luego hacia el otro.', 3, 15, 60, 'ej28')",
                "('Crunch inverso acostado', 'Acuéstate boca arriba y levanta las piernas hacia arriba. Luego, levanta la parte inferior de la espalda del suelo para trabajar los abdominales.', 3, 12, 60, 'ej29')",
                "('Bicicleta elíptica (esfuerzo ligero)', 'Utiliza una bicicleta elíptica con resistencia ligera para trabajar tanto las piernas como los brazos.', 3, 15, 60, 'ej30')",
                "('Estiramiento de cuerpo completo', 'Realiza movimientos suaves para estirar todos los grupos musculares, incluyendo brazos, piernas, espalda y cuello.', 3, 10, 60, 'ej31')",
                "('Dominadas con agarre ancho', 'Utiliza una barra fija para hacer dominadas, manteniendo las manos separadas a la distancia de los hombros.', 3, 12, 60, 'ej32')",
                "('Pull-ups con anillos', 'Realiza pull-ups utilizando anillos de gimnasia. Estira los brazos y luego flexiónalos para elevar el cuerpo.', 3, 10, 60, 'ej33')",
                "('Remo sentado con polea', 'Siéntate en una máquina de remo y tira de la polea hacia tu abdomen, trabajando los músculos de la espalda.', 3, 15, 60, 'ej34')",
                "('Press de banca con barra', 'Acuéstate en un banco plano y levanta una barra con pesas desde el pecho hasta la extensión completa de los brazos.', 3, 12, 60, 'ej35')",
                "('Press de banca inclinado con agarre ancho', 'Realiza el mismo movimiento que el press de banca, pero en un banco inclinado y con las manos separadas.', 3, 10, 60, 'ej36')",
                "('Press Arnold con mancuernas', 'Sujeta mancuernas en los hombros y presiona hacia arriba, girando las muñecas durante el movimiento.', 3, 12, 60, 'ej37')",
                "('Despliegue de abdominales sobre rodillas', 'Siéntate en una máquina de abdominales y extiende el torso hacia adelante desde una posición arrodillada.', 3, 15, 60, 'ej38')",
                "('Giro ruso con mancuerna', 'Siéntate en el suelo con las piernas flexionadas y sujeta una mancuerna. Gira el torso de un lado a otro.', 3, 12, 60, 'ej39')"
        };

        // Verificar e insertar cada ejercicio
        for (String ejercicio : ejercicios) {
            String referenciaEjercicio = ejercicio.split(",")[5].replace("'", "").trim();
            Cursor cursor = db.rawQuery("SELECT id FROM Ejercicio WHERE imagenReferencia = ?", new String[]{referenciaEjercicio});

            if (cursor.getCount() == 0) {
                // Insertar el ejercicio por defecto
                db.execSQL("INSERT INTO Ejercicio (nombre, descripcion, series, repeticiones, duracion, imagenReferencia) VALUES " + ejercicio + ";");
            }
            cursor.close();
        }
    }
    private void insertDefaultPlanes(SQLiteDatabase db) {
        /*
        Lista de planes por defecto
        Balanceado
        Pecho
        Espalda
        Brazos
        Piernas
        Abdomen
        Gluteos
        */
        String[] planes = {
                //Nombre, descripcion, ejercicios, clienteEmail, retroalimentacion, etiquetas, diasObjetivos, diasCompletados
                "('Plan Balanceado', 'Entrena todos los grupos musculares de manera equilibrada para mejorar la fuerza y la forma corporal.', '', '', '', 'Balanceado', '15','0')",
                "('Plan Pecho', 'Ejercicios enfocados en fortalecer los músculos pectorales para un pecho más definido.', 'Press de banca con barra, Press de banca inclinado con mancuernas, Inmersiones en barras paralelas, Press de banca con barra, Press de banca inclinado con agarre', 'kaleth@hotmail.com, asd3@', '', 'Pecho', '15','0')",
                "('Plan Espalda', 'Trabaja los músculos de la espalda para mejorar la postura y la fuerza de la parte superior del cuerpo.', 'Dominadas con agarre ancho, Remo de pie con barra, Pull-ups con anillos, Press Arnold con mancuernas', 'asd@', '', 'Espalda', '15','0')",
                "('Plan Brazos', 'Se centra en el desarrollo de los músculos de los brazos, bíceps y tríceps, para unos brazos más fuertes.', 'Press de hombros con barra, Inmersiones en barras paralelas, Ruleta rusa, Curvas laterales, Máquina de remo', 'asd2@', '', 'Brazos', '15','0')",
                "('Plan Piernas', 'Ejercicios para fortalecer los músculos de las piernas, como cuádriceps, isquiotibiales y glúteos, para mejorar la fuerza y la estabilidad.', 'Sentadilla aérea, Sentadilla con barra, Sentadilla búlgara con barra, Empuje de cadera alto, Extensión de piernas sentado', '', '', 'Piernas', '15','0')",
                "('Plan Abdomen', 'Trabaja los músculos abdominales para un core más fuerte y tonificado.', 'Abdominales de rodillas con barra EZ, Curvas laterales con mancuerna, Crunch inverso acostado, Despliegue de abdominales sobre rodillas', '', '', 'Abdomen', '15','0')",
                "('Plan Gluteos', 'En   focado en desarrollar y tonificar los músculos glúteos para una parte inferior más firme y esculpida.', 'Empuje de cadera alto con barra, Estocada con barra, Sentadilla frontal con barra', '', '', 'Gluteos', '15','0')"
        };

        // Verificar e insertar cada ejercicio
        for (String plan : planes) {
            db.execSQL("INSERT INTO PlanEntrenamiento (nombre, descripcion, ejercicios, clienteEmail, retroalimentacion, etiquetas, diasObjetivos, diasCompletados) VALUES " + plan + ";");
        }
    }
    private void insertDefaultClientes(SQLiteDatabase db) {
        String[] clientes = {
                /*
                fechaNacimiento, genero, altura, peso, objetivo, musculoObjetivo, experiencia, diasEntrenar, horaEntreno, gym, email
                 */
                "('10/09/2000', 'Masculino', '170.0', '60.0', 'Hipertrofia', 'Espalda', 'Principiante', 'Lunes, Martes, Miercoles, Jueves, Viernes, Sábado', '05:00', 'Ferchales','asd@')",
                "('23/05/1995', 'Masculino', '160.0', '65.0', 'Hipertrofia', 'Pecho', 'Principiante', 'Lunes, Miercoles, Viernes, Sábado', '06:00', 'Ferchales','kaleth@hotmail.com')",
                "('10/09/2000', 'Femenino', '180.0', '60.0', 'Hipertrofia', 'Brazos', 'Principiante', 'Lunes, Viernes, Sábado', '07:00', 'Ferchales','asd2@')",
        };
        //insertar cada ejercicio
        for (String cliente : clientes) {
            db.execSQL("INSERT INTO Cliente (fechaNacimiento, genero, altura, peso, objetivo, musculoObjetivo, experiencia, diasEntrenar, horaEntreno, gym, email) VALUES " + cliente + ";");
        }
    }
    private void insertDefaultUsers(SQLiteDatabase db) {
        String[] users = {
                /*
                nombre, email, password, tipoUsuario
                 */
                "('Camilo Torres', 'asd@', 'asd', 'Cliente')",
                "('Kaleth Morales', 'kaleth@hotmail.com', 'kaleth', 'Cliente')",
                "('Juancho Contreras', 'asd2@', 'asd2', 'Cliente')",
                "('Administrador', 'admin', 'admin', 'Administrador')",
        };
        //insertar cada ejercicio
        for (String user : users) {
            db.execSQL("INSERT INTO Usuario (nombre, email, password, tipoUsuario) VALUES " + user + ";");
        }
    }
}
