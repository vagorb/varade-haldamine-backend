package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Comment;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.CommentInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addComment(@RequestBody Comment comment){
        if (commentService.addComment(comment) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
