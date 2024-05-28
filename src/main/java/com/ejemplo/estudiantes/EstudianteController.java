package com.ejemplo.estudiantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Crear un nuevo estudiante
    @PostMapping
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Obtener la lista de todos los estudiantes
    @GetMapping
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }

    // Obtener un estudiante por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorId(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(estudiante -> ResponseEntity.ok().body(estudiante))
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un estudiante existente
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante detallesEstudiante) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudiante.setNombre(detallesEstudiante.getNombre());
                    estudiante.setEdad(detallesEstudiante.getEdad());
                    Estudiante estudianteActualizado = estudianteRepository.save(estudiante);
                    return ResponseEntity.ok().body(estudianteActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable Long id) {
        return estudianteRepository.findById(id)
                .map(estudiante -> {
                    estudianteRepository.delete(estudiante);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
