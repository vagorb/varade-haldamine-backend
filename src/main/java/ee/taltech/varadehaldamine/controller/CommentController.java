package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.modelDTO.CommentInfo;
import ee.taltech.varadehaldamine.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comment")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public List<CommentInfo> getAllByAssetId(@PathVariable String id) {
        return commentService.getAllByAssetId(id);
    }

    @PostMapping
    public List<CommentInfo> addComment(@RequestBody CommentInfo commentInfo) {
        return commentService.addComment(commentInfo);
    }
}
