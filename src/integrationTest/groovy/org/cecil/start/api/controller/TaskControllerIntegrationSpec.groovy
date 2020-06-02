package org.cecil.start.api.controller

import com.fasterxml.jackson.core.type.TypeReference
import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.api.model.Task
import org.cecil.start.db.repository.TaskRepository
import org.cecil.start.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import java.nio.charset.Charset

import static org.cecil.start.util.Constants.AUTH_HEADER
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TaskControllerIntegrationSpec extends BaseWebMvcIntegrationSpec {
    @Autowired
    TaskRepository taskRepository

    def "Tasks happy path"() {
        given:
        def validToken = "${Constants.TOKEN_PREFIX}${generateValidToken()}"

        expect: "POST new task"
        def task = new Task("Call grandma")
        def postResponse = mockMvc.perform(post('/tasks')
                .header(AUTH_HEADER, validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
        def taskId = postResponse.getContentAsString(Charset.defaultCharset())
        !taskId.isEmpty()

        and: "GET tasks"
        def getResponse = mockMvc.perform(get('/tasks')
                .header(AUTH_HEADER, validToken))
                .andExpect(status().isOk())
                .andReturn().getResponse()
        def getResponseString = getResponse.getContentAsString(Charset.defaultCharset())
        List<Task> tasks = objectMapper.readValue(getResponseString, new TypeReference<List<Task>>(){})
        tasks.size() == 1
        Task firstTask = tasks[0]
        firstTask.description
        !firstTask.description.isEmpty()

        and: "PUT: Update a task"
        def expectedDescription = "Call grandpa"
        firstTask.setDescription(expectedDescription)
        def putResponse = mockMvc.perform(put("/tasks/${taskId}")
                .content(objectMapper.writeValueAsString(firstTask))
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTH_HEADER, validToken))
                .andExpect(status().isOk())
                .andReturn().getResponse()
        def putResponseString = putResponse.getContentAsString(Charset.defaultCharset())
        putResponseString == expectedDescription

        and: "DELETE task"
        mockMvc.perform(delete("/tasks/${taskId}").header(AUTH_HEADER, validToken))
                .andExpect(status().isOk())
        taskRepository.findAll().isEmpty()
    }
}
