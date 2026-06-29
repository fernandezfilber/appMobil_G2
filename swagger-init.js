
window.onload = function() {
  // Build a system
  var url = window.location.search.match(/url=([^&]+)/);
  if (url && url.length > 1) {
    url = decodeURIComponent(url[1]);
  } else {
    url = window.location.origin;
  }
  var options = {
  "swaggerDoc": {
    "openapi": "3.0.0",
    "info": {
      "title": "Aula Virtual API - SISE",
      "version": "1.0.0",
      "description": "API REST completa para el sistema de Aula Virtual del SISE. Gestión de cursos, notas, tareas, asistencia y más.",
      "contact": {
        "name": "Grupo 2 - SISE",
        "email": "admin@sise.edu.pe"
      }
    },
    "servers": [
      {
        "url": "https://api.toq.life/api",
        "description": "Producción"
      },
      {
        "url": "http://localhost:3000/api",
        "description": "Servidor local"
      }
    ],
    "components": {
      "securitySchemes": {
        "bearerAuth": {
          "type": "http",
          "scheme": "bearer",
          "bearerFormat": "JWT"
        }
      }
    },
    "security": [
      {
        "bearerAuth": []
      }
    ],
    "tags": [
      {
        "name": "Auth",
        "description": "Autenticación y registro"
      },
      {
        "name": "Courses",
        "description": "Gestión de cursos"
      },
      {
        "name": "Categories",
        "description": "Categorías de cursos"
      },
      {
        "name": "Topics",
        "description": "Temas y contenido"
      },
      {
        "name": "Grades",
        "description": "Notas y calificaciones"
      },
      {
        "name": "Assignments",
        "description": "Tareas y actividades"
      },
      {
        "name": "Announcements",
        "description": "Anuncios del curso"
      },
      {
        "name": "Attendance",
        "description": "Control de asistencia"
      },
      {
        "name": "Dashboard",
        "description": "Resumen y estadísticas"
      },
      {
        "name": "Uploads",
        "description": "Gestión de archivos"
      }
    ],
    "paths": {
      "/archivos/imagen": {
        "post": {
          "tags": [
            "Uploads"
          ],
          "summary": "Subir imagen y obtener URL pública",
          "requestBody": {
            "required": true,
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "required": [
                    "image"
                  ],
                  "properties": {
                    "image": {
                      "type": "string",
                      "format": "binary",
                      "description": "Imagen JPEG, PNG, WebP o GIF (máx 10MB)"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Imagen subida exitosamente",
              "content": {
                "application/json": {
                  "schema": {
                    "type": "object",
                    "properties": {
                      "url": {
                        "type": "string"
                      },
                      "filename": {
                        "type": "string"
                      },
                      "size": {
                        "type": "integer"
                      },
                      "mimetype": {
                        "type": "string"
                      }
                    }
                  }
                }
              }
            }
          }
        }
      },
      "/archivos/archivo": {
        "post": {
          "tags": [
            "Uploads"
          ],
          "summary": "Subir archivo (PDF, ZIP o imagen)",
          "requestBody": {
            "required": true,
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "properties": {
                    "file": {
                      "type": "string",
                      "format": "binary"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Archivo subido"
            }
          }
        }
      },
      "/cursos/{courseId}/temas": {
        "get": {
          "tags": [
            "Topics"
          ],
          "summary": "Listar temas de un curso",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de temas con imágenes"
            }
          }
        },
        "post": {
          "tags": [
            "Topics"
          ],
          "summary": "Crear tema con imagen (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "requestBody": {
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "required": [
                    "title"
                  ],
                  "properties": {
                    "title": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "content": {
                      "type": "string"
                    },
                    "videoUrl": {
                      "type": "string"
                    },
                    "order": {
                      "type": "integer"
                    },
                    "type": {
                      "type": "string",
                      "enum": [
                        "LESSON",
                        "VIDEO",
                        "QUIZ",
                        "RESOURCE",
                        "ACTIVITY"
                      ]
                    },
                    "image": {
                      "type": "string",
                      "format": "binary"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Tema creado"
            }
          }
        }
      },
      "/cursos/{courseId}/temas/{id}": {
        "get": {
          "tags": [
            "Topics"
          ],
          "summary": "Obtener detalle de un tema",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Detalle del tema"
            }
          }
        },
        "put": {
          "tags": [
            "Topics"
          ],
          "summary": "Actualizar tema",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Tema actualizado"
            }
          }
        },
        "delete": {
          "tags": [
            "Topics"
          ],
          "summary": "Eliminar tema",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Tema eliminado"
            }
          }
        }
      },
      "/cursos/{courseId}/temas/{id}/progreso": {
        "patch": {
          "tags": [
            "Topics"
          ],
          "summary": "Marcar/desmarcar tema como completado (STUDENT)",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Progreso actualizado"
            }
          }
        }
      },
      "/notas/mis-notas": {
        "get": {
          "tags": [
            "Grades"
          ],
          "summary": "Obtener todas las notas del estudiante autenticado",
          "responses": {
            "200": {
              "description": "Lista de notas agrupadas por curso"
            }
          }
        }
      },
      "/notas/mis-notas/resumen": {
        "get": {
          "tags": [
            "Grades"
          ],
          "summary": "Resumen de notas del estudiante (promedio, aprobados, reprobados)",
          "responses": {
            "200": {
              "description": "Resumen de notas"
            }
          }
        }
      },
      "/notas/curso/{courseId}": {
        "get": {
          "tags": [
            "Grades"
          ],
          "summary": "Obtener notas de un curso",
          "description": "Estudiante ve sus propias notas. Profesor ve las de todos sus estudiantes.",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Notas del curso"
            }
          }
        }
      },
      "/notas/estudiante/{studentId}/curso/{courseId}": {
        "get": {
          "tags": [
            "Grades"
          ],
          "summary": "Notas de un estudiante específico en un curso (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "studentId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Notas del estudiante"
            }
          }
        }
      },
      "/notas": {
        "post": {
          "tags": [
            "Grades"
          ],
          "summary": "Registrar nota (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "value",
                    "type",
                    "studentId",
                    "courseId"
                  ],
                  "properties": {
                    "value": {
                      "type": "number",
                      "minimum": 0,
                      "maximum": 20,
                      "example": 16
                    },
                    "type": {
                      "type": "string",
                      "enum": [
                        "PARCIAL1",
                        "PARCIAL2",
                        "PARCIAL3",
                        "FINAL",
                        "TAREA",
                        "PROYECTO",
                        "LABORATORIO",
                        "PARTICIPACION"
                      ]
                    },
                    "description": {
                      "type": "string"
                    },
                    "feedback": {
                      "type": "string"
                    },
                    "studentId": {
                      "type": "string"
                    },
                    "courseId": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Nota registrada"
            }
          }
        }
      },
      "/notas/masivo": {
        "post": {
          "tags": [
            "Grades"
          ],
          "summary": "Registrar múltiples notas a la vez (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "properties": {
                    "grades": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/Grade"
                      }
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Notas registradas en lote"
            }
          }
        }
      },
      "/notas/{id}": {
        "put": {
          "tags": [
            "Grades"
          ],
          "summary": "Actualizar nota (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Nota actualizada"
            }
          }
        },
        "delete": {
          "tags": [
            "Grades"
          ],
          "summary": "Eliminar nota (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Nota eliminada"
            }
          }
        }
      },
      "/panel/estudiante": {
        "get": {
          "tags": [
            "Dashboard"
          ],
          "summary": "Dashboard del estudiante",
          "description": "Retorna un resumen completo con:\n- Cursos inscritos en el ciclo actual\n- Promedio general de notas\n- Tareas pendientes con fechas de entrega\n- Estado de asistencia\n- Últimos anuncios\n- Progreso en temas\n",
          "responses": {
            "200": {
              "description": "Dashboard del estudiante"
            }
          }
        }
      },
      "/panel/profesor": {
        "get": {
          "tags": [
            "Dashboard"
          ],
          "summary": "Dashboard del profesor",
          "description": "Retorna un resumen completo con:\n- Cursos que dicta\n- Total de estudiantes\n- Tareas pendientes de calificar\n- Entregas recientes\n",
          "responses": {
            "200": {
              "description": "Dashboard del profesor"
            }
          }
        }
      },
      "/panel/admin": {
        "get": {
          "tags": [
            "Dashboard"
          ],
          "summary": "Dashboard administrativo",
          "responses": {
            "200": {
              "description": "Estadísticas del sistema"
            }
          }
        }
      },
      "/cursos": {
        "get": {
          "tags": [
            "Courses"
          ],
          "summary": "Listar todos los cursos con búsqueda y filtros",
          "security": [],
          "parameters": [
            {
              "name": "q",
              "in": "query",
              "description": "Búsqueda por nombre/descripción",
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "categoryId",
              "in": "query",
              "description": "Filtrar por categoría",
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "period",
              "in": "query",
              "description": "Filtrar por periodo (ej: 3144-2026)",
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "status",
              "in": "query",
              "schema": {
                "type": "string",
                "enum": [
                  "ACTIVE",
                  "INACTIVE",
                  "ARCHIVED"
                ]
              }
            },
            {
              "name": "page",
              "in": "query",
              "schema": {
                "type": "integer",
                "default": 1
              }
            },
            {
              "name": "limit",
              "in": "query",
              "schema": {
                "type": "integer",
                "default": 10
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de cursos paginada"
            }
          }
        },
        "post": {
          "tags": [
            "Courses"
          ],
          "summary": "Crear curso (TEACHER o ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "required": [
                    "title",
                    "period",
                    "categoryId"
                  ],
                  "properties": {
                    "title": {
                      "type": "string"
                    },
                    "code": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "period": {
                      "type": "string",
                      "example": "3144-2026"
                    },
                    "credits": {
                      "type": "integer"
                    },
                    "schedule": {
                      "type": "string"
                    },
                    "classroom": {
                      "type": "string"
                    },
                    "categoryId": {
                      "type": "string"
                    },
                    "image": {
                      "type": "string",
                      "format": "binary"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Curso creado"
            }
          }
        }
      },
      "/cursos/buscar": {
        "get": {
          "tags": [
            "Courses"
          ],
          "summary": "Búsqueda rápida de cursos",
          "parameters": [
            {
              "name": "q",
              "in": "query",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Resultados de búsqueda"
            }
          }
        }
      },
      "/cursos/matriculados": {
        "get": {
          "tags": [
            "Courses"
          ],
          "summary": "Cursos en los que está inscrito el estudiante autenticado",
          "responses": {
            "200": {
              "description": "Lista de cursos inscritos"
            }
          }
        }
      },
      "/cursos/{id}": {
        "get": {
          "tags": [
            "Courses"
          ],
          "summary": "Obtener detalle de un curso",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Detalle del curso"
            }
          }
        },
        "put": {
          "tags": [
            "Courses"
          ],
          "summary": "Actualizar curso",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Curso actualizado"
            }
          }
        },
        "delete": {
          "tags": [
            "Courses"
          ],
          "summary": "Eliminar curso (solo ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Curso eliminado"
            }
          }
        }
      },
      "/cursos/{id}/matricular": {
        "post": {
          "tags": [
            "Courses"
          ],
          "summary": "Inscribirse en un curso",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "201": {
              "description": "Inscripción exitosa"
            }
          }
        },
        "delete": {
          "tags": [
            "Courses"
          ],
          "summary": "Desinscribirse de un curso",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Desinscripción exitosa"
            }
          }
        }
      },
      "/cursos/{id}/estudiantes": {
        "get": {
          "tags": [
            "Courses"
          ],
          "summary": "Ver estudiantes inscritos en un curso (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de estudiantes"
            }
          }
        }
      },
      "/categorias": {
        "get": {
          "tags": [
            "Categories"
          ],
          "summary": "Listar todas las categorías",
          "security": [],
          "responses": {
            "200": {
              "description": "Lista de categorías"
            }
          }
        },
        "post": {
          "tags": [
            "Categories"
          ],
          "summary": "Crear categoría (solo ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "name"
                  ],
                  "properties": {
                    "name": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "icon": {
                      "type": "string"
                    },
                    "color": {
                      "type": "string",
                      "example": "#3B82F6"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Categoría creada"
            }
          }
        }
      },
      "/categorias/{id}": {
        "get": {
          "tags": [
            "Categories"
          ],
          "summary": "Obtener categoría por ID",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Categoría encontrada"
            }
          }
        },
        "put": {
          "tags": [
            "Categories"
          ],
          "summary": "Actualizar categoría (solo ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Categoría actualizada"
            }
          }
        },
        "delete": {
          "tags": [
            "Categories"
          ],
          "summary": "Eliminar categoría (solo ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Categoría eliminada"
            }
          }
        }
      },
      "/auth/register": {
        "post": {
          "tags": [
            "Auth"
          ],
          "summary": "Registrar nuevo usuario",
          "security": [],
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "name",
                    "email",
                    "password"
                  ],
                  "properties": {
                    "name": {
                      "type": "string",
                      "example": "Filber Fernandez"
                    },
                    "email": {
                      "type": "string",
                      "example": "filber@sise.edu.pe"
                    },
                    "password": {
                      "type": "string",
                      "example": "password123"
                    },
                    "role": {
                      "type": "string",
                      "enum": [
                        "STUDENT",
                        "TEACHER"
                      ],
                      "default": "STUDENT"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Usuario creado exitosamente"
            },
            "409": {
              "description": "Email ya registrado"
            }
          }
        }
      },
      "/auth/login": {
        "post": {
          "tags": [
            "Auth"
          ],
          "summary": "Iniciar sesión",
          "security": [],
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "email",
                    "password"
                  ],
                  "properties": {
                    "email": {
                      "type": "string",
                      "example": "filber.fernandez@sise.edu.pe"
                    },
                    "password": {
                      "type": "string",
                      "example": "password123"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "200": {
              "description": "Login exitoso, retorna accessToken"
            },
            "401": {
              "description": "Credenciales inválidas"
            }
          }
        }
      },
      "/auth/logout": {
        "post": {
          "tags": [
            "Auth"
          ],
          "summary": "Cerrar sesión (invalida el token en Redis)",
          "responses": {
            "200": {
              "description": "Sesión cerrada"
            }
          }
        }
      },
      "/auth/me": {
        "get": {
          "tags": [
            "Auth"
          ],
          "summary": "Obtener perfil del usuario autenticado",
          "responses": {
            "200": {
              "description": "Datos del perfil"
            }
          }
        },
        "put": {
          "tags": [
            "Auth"
          ],
          "summary": "Actualizar perfil del usuario",
          "responses": {
            "200": {
              "description": "Perfil actualizado"
            }
          }
        }
      },
      "/auth/avatar": {
        "post": {
          "tags": [
            "Auth"
          ],
          "summary": "Subir avatar del usuario",
          "requestBody": {
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "properties": {
                    "avatar": {
                      "type": "string",
                      "format": "binary"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "200": {
              "description": "Avatar actualizado"
            }
          }
        }
      },
      "/auth/change-password": {
        "post": {
          "tags": [
            "Auth"
          ],
          "summary": "Cambiar contraseña",
          "responses": {
            "200": {
              "description": "Contraseña actualizada"
            }
          }
        }
      },
      "/asistencias/mis-asistencias": {
        "get": {
          "tags": [
            "Attendance"
          ],
          "summary": "Ver mi asistencia en todos los cursos",
          "responses": {
            "200": {
              "description": "Resumen de asistencia por curso"
            }
          }
        }
      },
      "/asistencias/curso/{courseId}": {
        "get": {
          "tags": [
            "Attendance"
          ],
          "summary": "Ver asistencia completa de un curso (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Asistencia del curso"
            }
          }
        }
      },
      "/asistencias/curso/{courseId}/estudiante/{studentId}": {
        "get": {
          "tags": [
            "Attendance"
          ],
          "summary": "Asistencia de un estudiante en un curso",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "studentId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Asistencia del estudiante"
            }
          }
        }
      },
      "/asistencias": {
        "post": {
          "tags": [
            "Attendance"
          ],
          "summary": "Registrar asistencia individual (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "studentId",
                    "courseId",
                    "date",
                    "status"
                  ],
                  "properties": {
                    "studentId": {
                      "type": "string"
                    },
                    "courseId": {
                      "type": "string"
                    },
                    "date": {
                      "type": "string",
                      "format": "date",
                      "example": "2026-06-16"
                    },
                    "status": {
                      "type": "string",
                      "enum": [
                        "PRESENT",
                        "ABSENT",
                        "LATE",
                        "EXCUSED"
                      ]
                    },
                    "notes": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Asistencia registrada"
            }
          }
        }
      },
      "/asistencias/masivo": {
        "post": {
          "tags": [
            "Attendance"
          ],
          "summary": "Registrar asistencia de todo el curso en una fecha (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "courseId",
                    "date",
                    "attendances"
                  ],
                  "properties": {
                    "courseId": {
                      "type": "string"
                    },
                    "date": {
                      "type": "string",
                      "format": "date"
                    },
                    "attendances": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "properties": {
                          "studentId": {
                            "type": "string"
                          },
                          "status": {
                            "type": "string"
                          },
                          "notes": {
                            "type": "string"
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Asistencia masiva registrada"
            }
          }
        }
      },
      "/asistencias/{id}": {
        "put": {
          "tags": [
            "Attendance"
          ],
          "summary": "Corregir registro de asistencia (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Asistencia actualizada"
            }
          }
        }
      },
      "/tareas/curso/{courseId}": {
        "get": {
          "tags": [
            "Assignments"
          ],
          "summary": "Listar tareas de un curso",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de tareas"
            }
          }
        }
      },
      "/tareas/{id}": {
        "get": {
          "tags": [
            "Assignments"
          ],
          "summary": "Detalle de una tarea",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Detalle de la tarea"
            }
          }
        },
        "put": {
          "tags": [
            "Assignments"
          ],
          "summary": "Actualizar tarea (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Tarea actualizada"
            }
          }
        },
        "delete": {
          "tags": [
            "Assignments"
          ],
          "summary": "Eliminar tarea (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Tarea eliminada"
            }
          }
        }
      },
      "/tareas": {
        "post": {
          "tags": [
            "Assignments"
          ],
          "summary": "Crear tarea (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "title",
                    "courseId",
                    "dueDate"
                  ],
                  "properties": {
                    "title": {
                      "type": "string"
                    },
                    "description": {
                      "type": "string"
                    },
                    "instructions": {
                      "type": "string"
                    },
                    "maxGrade": {
                      "type": "number",
                      "default": 20
                    },
                    "dueDate": {
                      "type": "string",
                      "format": "date-time"
                    },
                    "allowLate": {
                      "type": "boolean"
                    },
                    "courseId": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Tarea creada"
            }
          }
        }
      },
      "/tareas/{id}/entregar": {
        "post": {
          "tags": [
            "Assignments"
          ],
          "summary": "Entregar tarea (STUDENT)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "requestBody": {
            "content": {
              "multipart/form-data": {
                "schema": {
                  "type": "object",
                  "properties": {
                    "content": {
                      "type": "string"
                    },
                    "file": {
                      "type": "string",
                      "format": "binary"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Tarea entregada"
            }
          }
        }
      },
      "/tareas/{id}/entregas": {
        "get": {
          "tags": [
            "Assignments"
          ],
          "summary": "Ver todas las entregas de una tarea (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de entregas"
            }
          }
        }
      },
      "/tareas/{id}/entregas/{submissionId}/calificar": {
        "patch": {
          "tags": [
            "Assignments"
          ],
          "summary": "Calificar entrega (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            },
            {
              "name": "submissionId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "requestBody": {
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "grade"
                  ],
                  "properties": {
                    "grade": {
                      "type": "number",
                      "minimum": 0,
                      "maximum": 20
                    },
                    "feedback": {
                      "type": "string"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "200": {
              "description": "Entrega calificada"
            }
          }
        }
      },
      "/anuncios/curso/{courseId}": {
        "get": {
          "tags": [
            "Announcements"
          ],
          "summary": "Listar anuncios de un curso",
          "parameters": [
            {
              "name": "courseId",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Lista de anuncios"
            }
          }
        }
      },
      "/anuncios": {
        "post": {
          "tags": [
            "Announcements"
          ],
          "summary": "Crear anuncio (TEACHER/ADMIN)",
          "requestBody": {
            "required": true,
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "title",
                    "content",
                    "courseId"
                  ],
                  "properties": {
                    "title": {
                      "type": "string"
                    },
                    "content": {
                      "type": "string"
                    },
                    "courseId": {
                      "type": "string"
                    },
                    "isPinned": {
                      "type": "boolean"
                    }
                  }
                }
              }
            }
          },
          "responses": {
            "201": {
              "description": "Anuncio creado"
            }
          }
        }
      },
      "/anuncios/{id}": {
        "put": {
          "tags": [
            "Announcements"
          ],
          "summary": "Actualizar anuncio (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Anuncio actualizado"
            }
          }
        },
        "delete": {
          "tags": [
            "Announcements"
          ],
          "summary": "Eliminar anuncio (TEACHER/ADMIN)",
          "parameters": [
            {
              "name": "id",
              "in": "path",
              "required": true,
              "schema": {
                "type": "string"
              }
            }
          ],
          "responses": {
            "200": {
              "description": "Anuncio eliminado"
            }
          }
        }
      }
    }
  },
  "customOptions": {}
};
  url = options.swaggerUrl || url
  var urls = options.swaggerUrls
  var customOptions = options.customOptions
  var spec1 = options.swaggerDoc
  var swaggerOptions = {
    spec: spec1,
    url: url,
    urls: urls,
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  }
  for (var attrname in customOptions) {
    swaggerOptions[attrname] = customOptions[attrname];
  }
  var ui = SwaggerUIBundle(swaggerOptions)

  if (customOptions.oauth) {
    ui.initOAuth(customOptions.oauth)
  }

  if (customOptions.preauthorizeApiKey) {
    const key = customOptions.preauthorizeApiKey.authDefinitionKey;
    const value = customOptions.preauthorizeApiKey.apiKeyValue;
    if (!!key && !!value) {
      const pid = setInterval(() => {
        const authorized = ui.preauthorizeApiKey(key, value);
        if(!!authorized) clearInterval(pid);
      }, 500)

    }
  }

  if (customOptions.authAction) {
    ui.authActions.authorize(customOptions.authAction)
  }

  window.ui = ui
}
