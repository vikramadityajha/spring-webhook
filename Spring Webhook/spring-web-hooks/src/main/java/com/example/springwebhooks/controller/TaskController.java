package com.example.springwebhooks.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebhooks.entity.Task;
import com.example.springwebhooks.entity.Webhook;
import com.example.springwebhooks.repository.TaskRepository;
import com.example.springwebhooks.services.TriggerEventService;

@RestController
@RequestMapping("/task")
public class TaskController {

	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

	private TaskRepository taskRepository;
	
	@Autowired
	private TriggerEventService triggerEventService;
	
	@Autowired
	public TaskController(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task createTask(@RequestBody Task task) {
		Task createdTask = taskRepository.save(task);
		triggerEventService.triggerEventToSubcriber("create", task.getName());
		return createdTask;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/ids/{id}", produces = MediaType.TEXT_MARKDOWN_VALUE)
	public ResponseEntity<String> removeWebHookById(@PathVariable Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (task != null) {
			taskRepository.deleteById(task.get().getId());
			return new ResponseEntity<>("WebHook was successfully deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Webhook doesn't exist.", HttpStatus.OK);
	}
}
