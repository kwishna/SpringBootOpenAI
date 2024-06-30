package ai.spring.code.controller;

import ai.spring.code.services.OpenAiServices;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ai")
public class OpenAiRestController {

    private final OpenAiServices openAiServices;

    @Autowired
    public OpenAiRestController(OpenAiServices services) {
        this.openAiServices = services;
    }

    @PostMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ChatResponse explainImage(
            @RequestParam(defaultValue = "Explain what do you see on this picture?", name = "prompt") String prompt,
            @RequestPart(required = true, name = "image") MultipartFile image
    ) {
        return this.openAiServices.explainTheImage(prompt, image.getResource());
    }

    @PostMapping(path = "/query")
    public @ResponseBody ChatResponse query(
            @RequestParam(defaultValue = "Generate the names of 5 famous pirates.",
                    name = "query") String prompt
    ) {
        return this.openAiServices.answerQuery(prompt);
    }

    @PostMapping(path = "/chat")
    public @ResponseBody ResponseEntity<OpenAiApi.ChatCompletion> chatQuery(
            @RequestParam(defaultValue = "Generate the names of 5 famous pirates.", name = "query") String query
    ) {
        return this.openAiServices.chatResponse(query);
    }

}
