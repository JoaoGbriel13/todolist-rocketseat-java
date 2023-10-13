package br.com.joaog.todolist.Controller;

import br.com.joaog.todolist.Model.TaskModel;
import br.com.joaog.todolist.Repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;
    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel task, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        task.setUserID((UUID) idUser);
        taskRepository.save(task);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStartedAt()) || currentDate.isAfter(task.getEndAt()) || task.getStartedAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data fora dos padrões");
        }
        return ResponseEntity.status(201).body("Tarefa criada!\n" + task);
    }
    @GetMapping
    public ResponseEntity getByUser(HttpServletRequest request){
        var userId = request.getAttribute("idUser");
        List<TaskModel> taskList = taskRepository.findByUserID((UUID) userId);
        return ResponseEntity.ok(taskList);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID id){
        var idUser = (UUID) request.getAttribute("idUser");
        var taskOriginal = taskRepository.findById(id);
        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não pode enviar uma tarefa sem corpo.");
        }
        if(!taskOriginal.get().getUserID().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não pode editar a tarefa de outro usuario");
        }
        task.setID(id);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Atualizado\n" + task);
    }
}
