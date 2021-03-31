package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.CommentInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.CommentService;
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
