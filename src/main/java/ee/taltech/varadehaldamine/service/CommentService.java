package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidCommentException;
import ee.taltech.varadehaldamine.model.Comment;
import ee.taltech.varadehaldamine.modelDTO.CommentInfo;
import ee.taltech.varadehaldamine.repository.AssetRepository;
import ee.taltech.varadehaldamine.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AssetRepository assetRepository;

    public List<CommentInfo> getAllByAssetId(String assetId) {
        List<CommentInfo> comments = new LinkedList<>();
        for (Comment comment : commentRepository.findAllByAssetId(assetId)) {
            CommentInfo commentInfo = new CommentInfo(comment.getAssetId(), comment.getText(), comment.getCreatedAt().getTime());
            comments.add(commentInfo);
        }
        return comments;
    }

    public List<CommentInfo> addComment(CommentInfo commentInfo) {
        try {
            if (!assetRepository.findAllById(Collections.singletonList(commentInfo.getAssetId())).isEmpty()
                    && !commentInfo.getText().isBlank()) {
                Comment comment = new Comment(commentInfo.getAssetId(), commentInfo.getText(), new Timestamp(System.currentTimeMillis()));
                Comment newComment = commentRepository.save(comment);
                return getAllByAssetId(newComment.getAssetId());
            } else {
                throw new InvalidCommentException("Error when saving Comment");
            }
        } catch (InvalidCommentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
