package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Comment;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.CommentInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.AssetRepository;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AssetRepository assetRepository;

    public List<CommentInfo> getAllByAssetId(String assetId){
        List<CommentInfo> comments = new LinkedList<>();
        for (Comment comment: commentRepository.findAllByAssetId(assetId)){
            comments.add(new CommentInfo(comment.getId(), comment.getAssetId(), comment.getText(), comment.getCreatedAt().getTime()));
        }
        return comments;
    }

    public Comment addComment(Comment comment){
        if (!assetRepository.findAllById(Collections.singletonList(comment.getAssetId())).isEmpty() && !comment.getText().isBlank()){
            return commentRepository.save(comment);
        }
        return null;
    }

}
