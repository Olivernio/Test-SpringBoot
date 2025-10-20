SQL (scirpt):
-- ================================================
-- ACURED - BASE DE DATOS FINAL 2025
-- ================================================
-- Motor: PostgreSQL 13+
-- Autor: Equipo de Desarrollo ACURED
-- Versión: vFinal 2025-10
-- Descripción: Script limpio, normalizado y optimizado.

SET client_min_messages = WARNING;
SET search_path = public;

-- ================================================
-- BLOQUE 1: LIMPIEZA COMPLETA
-- ================================================
DROP TABLE IF EXISTS 
    foro_post,
    foro_tema,
    soporte_ticket,
    notificacion,
    curriculum_terapeuta,
    servicio_centro,
    interaccion_ia,
    transaccion_pago,
    suscripcion_usuario,
    plan_suscripcion,
    factura,
    pago,
    detalle_cita_tratamiento,
    cita,
    sesion_terapeutica,
    historial_medico,
    respuesta_formulario,
    campo_formulario,
    formulario,
    tratamiento,
    centro_medico,
    paciente,
    usuario_preferencia,
    usuario_credenciales,
    usuario,
    rol_usuario,
    metodo_pago,
    especialidad,
    certificacion,
    pais,
    auditoria
CASCADE;

-- ================================================
-- BLOQUE 2: CATÁLOGOS BASE
-- ================================================
CREATE TABLE rol_usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE metodo_pago (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE especialidad (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE certificacion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    institucion VARCHAR(200),
    descripcion TEXT
);

CREATE TABLE pais (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    codigo_iso CHAR(3) NOT NULL UNIQUE
);
CREATE INDEX idx_pais_nombre ON pais(nombre);
CREATE INDEX idx_pais_codigo_iso ON pais(codigo_iso);

-- ================================================
-- BLOQUE 3: USUARIOS Y AUTENTICACIÓN
-- ================================================
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    apellido VARCHAR(150) NOT NULL,
    rut VARCHAR(20),
    email VARCHAR(200) UNIQUE NOT NULL,
    telefono VARCHAR(30),
    direccion VARCHAR(300),
    rol_id INT REFERENCES rol_usuario(id) ON DELETE SET NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_rol ON usuario(rol_id);

CREATE TABLE usuario_credenciales (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    hash_password VARCHAR(400) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);
CREATE INDEX idx_usuario_credenciales_usuario_id ON usuario_credenciales(usuario_id);

CREATE TABLE usuario_preferencia (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    idioma VARCHAR(10),
    tema VARCHAR(50),
    notificaciones BOOLEAN DEFAULT TRUE
);

-- ================================================
-- BLOQUE 4: MÓDULO CLÍNICO Y OPERATIVO
-- ================================================
CREATE TABLE paciente (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    observaciones TEXT
);
CREATE INDEX idx_paciente_usuario ON paciente(usuario_id);

CREATE TABLE centro_medico (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    direccion VARCHAR(300),
    telefono VARCHAR(50),
    email VARCHAR(150),
    pais_id INT REFERENCES pais(id) ON DELETE SET NULL,
    sitio_web VARCHAR(250)
);
CREATE INDEX idx_centro_nombre ON centro_medico(nombre);

CREATE TABLE tratamiento (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    duracion_min INT,
    precio NUMERIC(12,2),
    especialidad_id INT REFERENCES especialidad(id) ON DELETE SET NULL
);
CREATE INDEX idx_tratamiento_nombre ON tratamiento(nombre);

CREATE TABLE cita (
    id SERIAL PRIMARY KEY,
    paciente_id INT REFERENCES paciente(id) ON DELETE CASCADE,
    terapeuta_id INT REFERENCES usuario(id) ON DELETE SET NULL,
    centro_id INT REFERENCES centro_medico(id) ON DELETE SET NULL,
    fecha TIMESTAMP NOT NULL,
    estado VARCHAR(50) DEFAULT 'pendiente' CHECK (estado IN ('pendiente','confirmada','cancelada','completada')),
    motivo TEXT
);
CREATE INDEX idx_cita_paciente ON cita(paciente_id);
CREATE INDEX idx_cita_fecha ON cita(fecha);

CREATE TABLE detalle_cita_tratamiento (
    id SERIAL PRIMARY KEY,
    cita_id INT REFERENCES cita(id) ON DELETE CASCADE,
    tratamiento_id INT REFERENCES tratamiento(id) ON DELETE SET NULL,
    observacion TEXT
);

CREATE TABLE sesion_terapeutica (
    id SERIAL PRIMARY KEY,
    cita_id INT REFERENCES cita(id) ON DELETE CASCADE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notas TEXT
);

CREATE TABLE historial_medico (
    id SERIAL PRIMARY KEY,
    paciente_id INT REFERENCES paciente(id) ON DELETE CASCADE,
    descripcion TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    url_archivo TEXT
);
CREATE INDEX idx_historial_paciente ON historial_medico(paciente_id);

CREATE TABLE formulario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    tipo VARCHAR(100)
);

CREATE TABLE campo_formulario (
    id SERIAL PRIMARY KEY,
    formulario_id INT REFERENCES formulario(id) ON DELETE CASCADE,
    pregunta TEXT NOT NULL,
    tipo_campo VARCHAR(50),
    alternativas TEXT
);

CREATE TABLE respuesta_formulario (
    id SERIAL PRIMARY KEY,
    formulario_id INT REFERENCES formulario(id) ON DELETE CASCADE,
    paciente_id INT REFERENCES paciente(id) ON DELETE CASCADE,
    respuestas JSONB,
    fecha_respuesta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_respuesta_paciente ON respuesta_formulario(paciente_id);

-- ================================================
-- BLOQUE 5: PAGOS, PLANES Y SUSCRIPCIONES
-- ================================================
CREATE TABLE pago (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE SET NULL,
    monto NUMERIC(12,2) NOT NULL,
    metodo_id INT REFERENCES metodo_pago(id) ON DELETE SET NULL,
    estado VARCHAR(50) DEFAULT 'pendiente' CHECK (estado IN ('pendiente','pagado','fallido','reembolsado')),
    comprobante_transferencia TEXT,
    metodo_detalle VARCHAR(100),
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_pago_usuario ON pago(usuario_id);

CREATE TABLE factura (
    id SERIAL PRIMARY KEY,
    pago_id INT REFERENCES pago(id) ON DELETE CASCADE,
    numero VARCHAR(50) UNIQUE,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    monto_total NUMERIC(12,2)
);
CREATE INDEX idx_factura_fecha ON factura(fecha_emision);

CREATE TABLE plan_suscripcion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(12,2) NOT NULL,
    duracion_dias INT NOT NULL
);

CREATE TABLE suscripcion_usuario (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    plan_id INT REFERENCES plan_suscripcion(id) ON DELETE CASCADE,
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP,
    estado VARCHAR(50) DEFAULT 'activa' CHECK (estado IN ('activa','vencida','cancelada'))
);
CREATE INDEX idx_suscripcion_estado ON suscripcion_usuario(estado);

CREATE TABLE transaccion_pago (
    id SERIAL PRIMARY KEY,
    pago_id INT REFERENCES pago(id) ON DELETE CASCADE,
    referencia VARCHAR(150),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50)
);

-- ================================================
-- BLOQUE 6: EXTENSIONES FUNCIONALES
-- ================================================
CREATE TABLE servicio_centro (
    id SERIAL PRIMARY KEY,
    centro_id INT REFERENCES centro_medico(id) ON DELETE CASCADE,
    tratamiento_id INT REFERENCES tratamiento(id) ON DELETE CASCADE,
    disponible BOOLEAN DEFAULT TRUE,
    precio NUMERIC(12,2),
    duracion_min INT
);

CREATE TABLE curriculum_terapeuta (
    id SERIAL PRIMARY KEY,
    terapeuta_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    institucion VARCHAR(200),
    cargo VARCHAR(150),
    descripcion TEXT,
    fecha_inicio DATE,
    fecha_fin DATE
);

CREATE TABLE notificacion (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    tipo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    leido BOOLEAN DEFAULT FALSE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_notificacion_usuario ON notificacion(usuario_id);

CREATE TABLE soporte_ticket (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE SET NULL,
    asunto VARCHAR(200),
    mensaje TEXT NOT NULL,
    estado VARCHAR(50) DEFAULT 'pendiente',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta TIMESTAMP,
    respuesta TEXT
);

CREATE TABLE foro_tema (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    usuario_id INT REFERENCES usuario(id),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_foro_tema_usuario ON foro_tema(usuario_id);

CREATE TABLE foro_post (
    id SERIAL PRIMARY KEY,
    tema_id INT REFERENCES foro_tema(id) ON DELETE CASCADE,
    usuario_id INT REFERENCES usuario(id),
    contenido TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_foro_post_tema ON foro_post(tema_id);

CREATE TABLE interaccion_ia (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    pregunta TEXT,
    respuesta TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- BLOQUE 7: AUDITORÍA
-- ================================================
CREATE TABLE auditoria (
    id SERIAL PRIMARY KEY,
    tabla VARCHAR(100),
    operacion VARCHAR(50),
    usuario_id INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- Catálogos base
INSERT INTO rol_usuario (nombre) VALUES ('Administrador'), ('Paciente'), ('Terapeuta');
INSERT INTO metodo_pago (nombre) VALUES ('Transferencia'), ('Tarjeta'), ('Efectivo');
INSERT INTO especialidad (nombre) VALUES ('Medicina general'), ('Psicología'), ('Traumatología');
INSERT INTO certificacion (nombre, institucion, descripcion)
VALUES ('Medicina U. Chile', 'Universidad de Chile', 'Especialidad médica'),
       ('Psiquiatría UC', 'Pontificia Universidad Católica', 'Título de psiquiatría');
INSERT INTO pais (nombre, codigo_iso) VALUES ('Chile', 'CHL'), ('Argentina', 'ARG');

-- Usuarios
INSERT INTO usuario (nombre, apellido, rut, email, telefono, direccion, rol_id)
VALUES
('Ana', 'Gómez', '12345678-9', 'ana@acured.com', '+56911111111', 'Calle 1', 2),
('Luis', 'Torres', '98765432-1', 'luis@acured.com', '+56922222222', 'Calle 2', 3),
('Tomas', 'Pérez', '11111333-4', 'tomas@acured.com', '+56946346346', 'Calle 3', 1);

INSERT INTO usuario_credenciales (usuario_id, hash_password) VALUES (1, 'hash_ana'), (2, 'hash_luis'), (3, 'hash_tomas');
INSERT INTO usuario_preferencia (usuario_id, idioma, tema, notificaciones)
VALUES (1, 'es', 'oscuro', TRUE), (2, 'es', 'claro', TRUE), (3, 'es', 'oscuro', FALSE);

-- Pacientes
INSERT INTO paciente (usuario_id, fecha_nacimiento, genero, observaciones)
VALUES (1, '1990-06-10', 'Femenino', 'Sin antecedentes'), (2, '1981-05-15', 'Masculino', 'Hipertensión');

-- Centro médico y tratamientos
INSERT INTO centro_medico (nombre, direccion, telefono, email, pais_id, sitio_web)
VALUES ('Centro ACURED', 'Calle Salud 100', '+56212345678', 'contacto@acured.com', 1, 'acured.com');

INSERT INTO tratamiento (nombre, descripcion, duracion_min, precio, especialidad_id)
VALUES ('Terapia General', 'Consulta médica clásica', 30, 25000, 1),
       ('Terapia Psicología', 'Sesión psicológica', 60, 35000, 2);

-- Formularios (ejemplo ficha clínica y síntomas)
INSERT INTO formulario (nombre, tipo) VALUES 
('Ficha clínica inicial', 'Clínico'),
('Cuestionario síntomas', 'Síntomas');

INSERT INTO campo_formulario (formulario_id, pregunta, tipo_campo, alternativas)
VALUES 
(1, '¿Cuál es su género?', 'selección única', 'Femenino,Masculino,No binario'),
(1, '¿Está en tratamiento?', 'boolean', 'Sí,No'),
(2, 'Síntomas presentes', 'selección múltiple', 'Fatiga,Estrés,Ansiedad,Pérdida peso');

-- Respuestas formulario
INSERT INTO respuesta_formulario (formulario_id, paciente_id, respuestas)
VALUES
(1, 1, '{"¿Cuál es su género?":"Femenino","¿Está en tratamiento?":"Sí"}'),
(2, 2, '{"Síntomas presentes":["Fatiga","Ansiedad"]}');

-- Citas, detalles y sesiones
INSERT INTO cita (paciente_id, terapeuta_id, centro_id, fecha, estado, motivo)
VALUES (1, 2, 1, '2025-10-20 09:00', 'pendiente', 'Evaluación general'),
       (2, 2, 1, '2025-10-21 10:00', 'confirmada', 'Seguimiento tratamiento');

INSERT INTO detalle_cita_tratamiento (cita_id, tratamiento_id, observacion)
VALUES (1, 1, 'Primera vez'), (2, 2, 'Sesión de seguimiento');

INSERT INTO sesion_terapeutica (cita_id, fecha, notas)
VALUES (1, '2025-10-20 10:00', 'Paciente respondió bien'), (2, '2025-10-21 11:00', 'Sin complicaciones');

-- Historial médico
INSERT INTO historial_medico (paciente_id, descripcion, fecha_registro, url_archivo)
VALUES (1, 'Analítica normal', '2025-09-01', NULL), (2, 'Radiografía lumbar', '2025-08-15', 'img1.pdf');

-- Pagos, facturas, planes, suscripciones, transacciones
INSERT INTO pago (usuario_id, monto, metodo_id, estado, comprobante_transferencia, metodo_detalle)
VALUES (1, 35000, 1, 'pagado', 'tb-112', 'Banco Estado'),
       (2, 25000, 2, 'pagado', 'tb-113', 'Visa');

INSERT INTO factura (pago_id, numero, monto_total)
VALUES (1, 'FAC-1001', 35000), (2, 'FAC-1002', 25000);

INSERT INTO plan_suscripcion (nombre, descripcion, precio, duracion_dias)
VALUES ('Mensual', 'Acceso básico', 12000, 30), ('Premium', 'Consultas ilimitadas', 40000, 30);

INSERT INTO suscripcion_usuario (usuario_id, plan_id, estado)
VALUES (1, 1, 'activa'), (2, 2, 'activa');

INSERT INTO transaccion_pago (pago_id, referencia, estado)
VALUES (1, 'TRX001', 'completado'), (2, 'TRX002', 'completado');

-- Servicios y extensiones
INSERT INTO servicio_centro (centro_id, tratamiento_id, disponible, precio, duracion_min)
VALUES (1, 1, TRUE, 25000, 30), (1, 2, TRUE, 35000, 60);

INSERT INTO curriculum_terapeuta (terapeuta_id, institucion, cargo, descripcion, fecha_inicio, fecha_fin)
VALUES (2, 'U. del Desarrollo', 'Psic. Clínico', 'Especialista en adultos', '2018-01-01', NULL);

INSERT INTO notificacion (usuario_id, tipo, mensaje)
VALUES (1, 'bienvenida', 'Bienvenido Ana'), (2, 'alerta', 'Tienes cita mañana');

INSERT INTO soporte_ticket (usuario_id, asunto, mensaje)
VALUES (1, 'Problema acceso', 'No puedo entrar al sitio');

-- Foro y post
INSERT INTO foro_tema (titulo, descripcion, usuario_id)
VALUES ('Zona de preguntas', 'Para dudas generales', 2);

INSERT INTO foro_post (tema_id, usuario_id, contenido)
VALUES (1, 1, '¿Cuál es la experiencia con la terapia X?');

-- IA
INSERT INTO interaccion_ia (usuario_id, pregunta, respuesta)
VALUES (1, '¿Cuál es el horario de atención?', 'Lunes a viernes 09:00-18:00');

-- Auditoría
INSERT INTO auditoria (tabla, operacion, usuario_id)
VALUES ('cita', 'INSERT', 1), ('pago', 'UPDATE', 2);


select * from paciente