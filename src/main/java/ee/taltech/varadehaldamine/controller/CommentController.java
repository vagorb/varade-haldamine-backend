package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.Comment;
import ee.taltech.varadehaldamine.modelDTO.CommentInfo;
import ee.taltech.varadehaldamine.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comment")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * Method to get all comments of asset by asset id
     *
     * @param id asset id
     * @return asset comments list
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/{id}")
    public List<CommentInfo> getAllByAssetId(@PathVariable String id) {
        return commentService.getAllByAssetId(id);
    }

    /**
     * Method to add new comment to the asset.
     *
     * @param commentInfo comment information
     * @return asset comments list
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @PostMapping
    public List<CommentInfo> addComment(@RequestBody CommentInfo commentInfo) {
        return commentService.addComment(commentInfo);
    }

    /**
     * Method to update comment text.
     *
     * @param comment new comment text holder
     * @param id      comment id to replace text
     * @return renewed comment
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @PutMapping("/{id}")
    public Comment updateComment(@RequestBody Comment comment, @PathVariable Long id) {
        return commentService.update(comment, id);
    }
}
